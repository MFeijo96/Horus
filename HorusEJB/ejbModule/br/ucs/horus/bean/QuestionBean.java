package br.ucs.horus.bean;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ucs.horus.dao.QuestionDAO;
import br.ucs.horus.dao.SkillDAO;
import br.ucs.horus.dao.UserDAO;
import br.ucs.horus.models.Answer;
import br.ucs.horus.models.Historic;
import br.ucs.horus.models.Question;
import br.ucs.horus.models.Question.Reason;
import br.ucs.horus.models.QuestionSkill;
import br.ucs.horus.models.Skill;
import br.ucs.horus.models.User;
import br.ucs.horus.models.UserSkill;
import br.ucs.horus.utils.Sessao;
import br.ucs.horus.utils.Utils;

import static br.ucs.horus.models.Question.Reason.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
public class QuestionBean {
	@EJB
	private QuestionDAO questionDAO;

	@EJB
	private UserDAO userDAO;
	
	@EJB
	private SkillDAO skillDAO;

	@Inject
	private Sessao sessao;

	public Question nextQuestion(int lastId) {
		Question question = questionDAO.findQuestion(lastId);

		return question;
	}

	public void update(Question question, Answer answer, Reason reason, int time) {
		Utils.printLogInfo("Pergunta respondida - Usuário [" + sessao.getCurrentUser().getId() + "]"
				+ " - Pergunta [" + question.getId() + "]"
				+ " - Resposta [" + (answer == null ? "null" : answer.getId()) + "]"
				+ " - Motivo [" + reason.value + "]");
		adjustScore(question, answer, reason, time);
		adjustRecomendation(question, answer, reason, time);
	}

	private void adjustScore(Question question, Answer answer, Reason reason, int time) {
		User currentUser = sessao.getCurrentUser();
		final Historic historic = questionDAO.getLastAnswer(question, currentUser);
		int userScore = currentUser.getScore();
		int partialScore = 0;

		if (historic != null) {
			userScore -= historic.getPartialScore();
		}

		if (reason == ACERTO) {
			if (question.getMaxTime() != null && (historic == null || historic.getReason() != ACERTO.value)
					&& ((float) (question.getMaxTime() - time) / question.getMaxTime()) > Utils.FATOR_CHUTE) {
				reason = CHUTE;
				partialScore = wrongFlowPoints(reason);
			} else
				partialScore = correctFlowPoints(question, time);
		} else {
			partialScore = wrongFlowPoints(reason);
		}

		userScore += partialScore;

		final Historic newHistoric = new Historic();
		if (answer != null)
			newHistoric.setAnswer_id(answer.getId());
		newHistoric.setQuestion_id(question.getId());
		newHistoric.setUser_id(currentUser.getId());
		newHistoric.setDate(new Date());
		newHistoric.setReason(reason.value);
		newHistoric.setTime(time);
		newHistoric.setPartialScore(partialScore);

		Utils.printLogInfo("Pontuação gerada - Usuário [" + currentUser.getId() + "]"
				+ " - PontuaçãoAnt [" + currentUser.getScore() + "]"
				+ " - PontuaçãoParcial [" + partialScore + "]"
				+ " - Tempo [" + time + "]"
				+ " - PontuaçãoFinal [" + userScore + "]");

		currentUser.setScore(userScore);
		userDAO.updateUser(currentUser);
		questionDAO.saveHistoric(newHistoric);
	}

	private int wrongFlowPoints(Reason reason) {
		float questionValue = getQuestionValue();
		float FP = questionValue * Reason.getFator(reason);
		return normalize(FP, questionValue);
	}

	private int correctFlowPoints(Question question, int time) {
		float questionValue = getQuestionValue();
		return (int) getFT(question, time, questionValue);
	}

	private float getFT(Question question, int time, float questionValue) {
		return getFT(question, time, questionValue, true);
	}

	private float getFT(Question question, int time, float questionValue, boolean normalize) {
		float max;
		if (question.getMaxTime() != null) {
			time = question.getMaxTime() - time;
			max = question.getMaxTime() * Utils.FATOR_CHUTE;
		} else {
			max = time;
		}

		float FT = (time * questionValue) / max;
		return normalize ? normalize(FT, questionValue) : FT;
	}

	private void adjustRecomendation(Question question, Answer answer, Reason reason, int time) {
		User currentUser = sessao.getCurrentUser();
		final Historic historic = questionDAO.getLastAnswer(question, currentUser);

		if (reason == ACERTO) {
			if (question.getMaxTime() != null && (historic == null || historic.getReason() != ACERTO.value)
					&& ((float) (question.getMaxTime() - time) / question.getMaxTime()) > Utils.FATOR_CHUTE) {
				reason = CHUTE;
				wrongFlowRequirement(question, currentUser, reason);
			} else
				correctFlowRequirement(question, currentUser, time);
		} else {
			wrongFlowRequirement(question, currentUser, reason);
		}
	}

	private void correctFlowRequirement(Question question, User user, int time) {
		final List<QuestionSkill> questionSkills = skillDAO.getPositiveSkills(question);

		if (!Utils.isEmpty(questionSkills)) {
			final List<UserSkill> userSkills = userDAO.getUserSkills(user);

			for (QuestionSkill questionSkill : questionSkills) {
				boolean found = false;
				if (!Utils.isEmpty(userSkills)) {
					for (UserSkill userSkill : userSkills) {
						if (userSkill.getSkill_id() == questionSkill.getSkill_id()) {
							if (questionSkill.getLevel() > userSkill.getLevel()) {
								float IR = questionSkill.getLevel() - userSkill.getLevel();
								float FT = getFT(question, time, IR, false);
								userSkill.setLevel(Utils.round(userSkill.getLevel() + FT, 2));
								userDAO.updateSkill(userSkill, true);
							} else {
								Utils.printLogDebug("Encontrou Skil porém de menor valor. Question[" + questionSkill.getLevel() + "]"
										+ " User[" + userSkill.getLevel() + "]");
							}
							found = true;
							break;
						}
					}
				}

				if (!found) {
					Utils.printLogInfo("Nova Skill para o Usuário[" + user.getId() + "]"
							+ " SkillId[" + questionSkill.getSkill_id() + "]");
					UserSkill userSkill = new UserSkill();
					userSkill.setSkill_id(questionSkill.getSkill_id());
					userSkill.setUser_id(user.getId());
					userSkill.setLevel(Utils.round(getFT(question, time, questionSkill.getLevel(), false), 2));
					userDAO.updateSkill(userSkill, false);
				}
			}
		}
	}

	private void wrongFlowRequirement(Question question, User user, Reason reason) {
		final List<QuestionSkill> questionSkills = skillDAO.getPositiveSkills(question);

		if (!Utils.isEmpty(questionSkills)) {
			final List<UserSkill> userSkills = userDAO.getUserSkills(user);

			if (!Utils.isEmpty(userSkills)) {
				for (QuestionSkill questionSkill : questionSkills) {
					for (UserSkill userSkill : userSkills) {
						if (userSkill.getSkill_id() == questionSkill.getSkill_id()) {
							if (questionSkill.getLevel() < userSkill.getLevel()) {
								float DR = 1 - (questionSkill.getLevel() / userSkill.getLevel());
								float total = DR * Reason.getFator(reason);
								userSkill.setLevel(Utils.round(userSkill.getLevel() + total, 2));
								userDAO.updateSkill(userSkill, true);
							}
						}
					}
				}
			}
		}
	}

	private int normalize(float total, float questionValue) {
		return (int) (total * 100 / questionValue);
	}

	private float getQuestionValue() {
		return 5f / questionDAO.getTotalAvailableQuestions();
	}
	
	public Question getNextQuestion() {
		final User user = sessao.getCurrentUser();
		final List<UserSkill> userSkills = userDAO.getUserSkills(user);
		final List<Skill> otherSkills = skillDAO.getSkillsExcept(getIds(userSkills));
		final LinkedHashMap<Integer, Float> userCapabilities = new LinkedHashMap<>();
		
		if (!Utils.isEmpty(otherSkills)) {
			for (Skill skill : otherSkills) {
				userCapabilities.put(skill.getId(), 0f);
			}
		}
		
		String capabilitiesDebug = "";
		if (!Utils.isEmpty(userSkills)) {
			for (UserSkill skill : userSkills) {
				userCapabilities.put(skill.getSkill_id(), skill.getLevel());
				capabilitiesDebug += skill.getSkill_id() + "=" + skill.getLevel() + ";";
			}
		}
		
		Utils.printLogDebug("Buscando nova pergunta - Usuário [" + user.getId() + "]"
				+ " - Capacidades Usuário [" + capabilitiesDebug + "]"
						+ " - Capacidades completas: [" + userCapabilities + "]");
		
		for (Map.Entry<Integer, Float> capacity : userCapabilities.entrySet()) {
		    final List<Question> possibleQuestions = new ArrayList<Question>();
		    float userCapacity = capacity.getValue(); 
		    do {
		    	final List<Question> questions = questionDAO.getQuestionsForMinorRequirement(capacity.getKey(), userCapacity);
		    	userCapacity += 1;
		    	for (Question question: questions) {
		    		boolean canContinue = true;
		    		
		    		List<QuestionSkill> questionSkills = skillDAO.getSkills(question);
		    		for (QuestionSkill questionSkill: questionSkills) {
		    			if (questionSkill.getLevel() <= (userCapabilities.get(questionSkill.getSkill_id()) + 2.5f)) { //&& questionSkill.getLevel() > (userCapabilities.get(questionSkill.getQuestion_id()) - 1)
		    				canContinue = true;
		    			} else {
		    				canContinue = false;
		    				break;
		    			}
		    		}
		    		
		    		if (canContinue) {
		    			possibleQuestions.add(question);
		    			if (possibleQuestions.size() > 2) break;
		    		}
		    	}
		    //} while (possibleQuestions.size() < 10 && userCapacity < 6);
		    } while (possibleQuestions.size() < 3 && userCapacity < 6);
		    
		    Utils.printLogDebug("Buscando nova pergunta - Usuário [" + user.getId() + "]"
					+ " - Possíveis questões [" + possibleQuestions + "]");
		    	
		    if (possibleQuestions.size() > 0) {
		    	final List<Question> questionNotAnswered = questionDAO.getQuestionsNotAnswered(possibleQuestions, user);
		    	if (!Utils.isEmpty(questionNotAnswered)) {
			    	for (Question question: questionNotAnswered) {
			    		possibleQuestions.add(question);
			    	}
		    	}
		    	
		    	Utils.printLogDebug("Buscando nova pergunta - Usuário [" + user.getId() + "]"
		    			+ " - Questões finais [" + possibleQuestions + "]"
		    			+ " - Skill [" + capacity.getKey() + "]");
		    	return possibleQuestions.get(Utils.getNextRandom(possibleQuestions.size()));
		    }
		}
		
		return null;
	}
	
	private List<Integer> getIds(List<UserSkill> userSkills) {
		final List<Integer> result = new ArrayList<>();
		if (!Utils.isEmpty(userSkills)) {
			for (UserSkill userSkill: userSkills) {
				result.add(userSkill.getSkill_id());
			}
		}
		
		return result;
	}
}
