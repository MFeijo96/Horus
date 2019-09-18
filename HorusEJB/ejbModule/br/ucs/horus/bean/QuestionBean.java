package br.ucs.horus.bean;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ucs.horus.dao.QuestionDAO;
import br.ucs.horus.dao.UserDAO;
import br.ucs.horus.models.Answer;
import br.ucs.horus.models.Historic;
import br.ucs.horus.models.Question;
import br.ucs.horus.models.Question.Reason;
import br.ucs.horus.models.User;
import br.ucs.horus.utils.Sessao;
import br.ucs.horus.utils.Utils;

import static br.ucs.horus.models.Question.Reason.*;

import java.util.Date;

@Stateless
@LocalBean
public class QuestionBean {
	@EJB
	private QuestionDAO questionDAO;
	
	@EJB
	private UserDAO	userDAO;
	
	@Inject
	private Sessao sessao;

	public Question nextQuestion(int lastId) {
		Question question = questionDAO.findQuestion(lastId);

		return question;
	}

	public void adjustScore(Question question, Answer answer, Reason reason, int time) {
		User currentUser = sessao.getCurrentUser();
		final Historic historic = questionDAO.getLastAnswer(question, currentUser);
		int userScore = currentUser.getScore();
		int partialScore = 0;
		
		if (historic != null) {
			userScore -= historic.getPartialScore();
		}
		
		if (reason == ACERTO) {
			if (question.getMaxTime() != null && (historic == null || historic.getReason() != ACERTO.value) && ((float)(question.getMaxTime() - time) / question.getMaxTime()) > Utils.FATOR_CHUTE) {
				reason = CHUTE;
				partialScore =  wrongFlow(reason);
			}
			else partialScore = correctFlow(question, time);
		} else {
			partialScore = wrongFlow(reason);
		}
		
		userScore += partialScore;
		
		final Historic newHistoric = new Historic();
		if (answer != null) newHistoric.setAnswer_id(answer.getId());
		newHistoric.setQuestion_id(question.getId());
		newHistoric.setUser_id(currentUser.getId());
		newHistoric.setDate(new Date());
		newHistoric.setReason(reason.value);
		newHistoric.setTime(time);
		newHistoric.setPartialScore(partialScore);
		
		Utils.printLog("Usuario:" + currentUser.getId() + " Questão:" + question.getId() + " PontuaçãoAnt:" + currentUser.getScore() + " PontuaçãoParcial:" + partialScore + " Tempo:" + time + " PontuaçãoFinal: " + userScore);
		
		currentUser.setScore(userScore);
		userDAO.update(currentUser);
		questionDAO.saveHistoric(newHistoric);
	}
	
	private int wrongFlow(Reason reason) {
		float questionValue = getQuestionValue();
		float FP = questionValue * Reason.getFator(reason);
		return normalize(FP, questionValue);
	}
	
	private int correctFlow(Question question, int time) {
		float questionValue = getQuestionValue();
		float max;
		if (question.getMaxTime() != null) {
			time = question.getMaxTime() - time;
			max = question.getMaxTime() * Utils.FATOR_CHUTE;
		} else {
			max = time;
		}
		
		float FT = (time * questionValue)/max; 
		return normalize(FT, questionValue);
	}
	
	private int normalize(float total, float questionValue) {
		return (int) (total * 100 / questionValue);
	}
	
	private float getQuestionValue() {
		return 5f/questionDAO.getTotalAvailableQuestions();
	}
}
