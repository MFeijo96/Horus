package br.ucs.horus.ManagedBeans;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ucs.horus.bean.MediaBean;
import br.ucs.horus.bean.QuestionBean;
import br.ucs.horus.models.Answer;
import br.ucs.horus.models.Question;
import br.ucs.horus.utils.Sessao;

import static br.ucs.horus.models.Question.Reason.*;

@Named
@ViewScoped // session
public class QuestionMB implements Serializable {
	private static final long serialVersionUID = -1907939408935134046L;

	private Question question;
	private String questionImage, regressiveCounter;
	private int time;
	private List<Answer> answers;
	private boolean alreadyCall;

	@EJB
	private QuestionBean questionBean;

	@EJB
	private MediaBean mediaBean;

	@Inject
	private Sessao sessao;

	public String onSelectAnswer(int index) {
		Answer answer = answers.get(index);

		if (!alreadyCall) {
			alreadyCall = true;
			questionBean.update(question, answer, answer.isCorrect() ? ACERTO : ERRO, time);
		}

		return "/private/media.jsf?faces-redirect=true";
	}

	public Sessao getSessao() {
		return sessao;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getQuestionImage() {
		return questionImage;
	}

	public void setQuestionImage(String questionImage) {
		this.questionImage = questionImage;
	}

	public String getRegressiveCounter() {
		return regressiveCounter;
	}

	public void setRegressiveCounter(String regressiveCounter) {
		this.regressiveCounter = regressiveCounter;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void decrementCounter() {
		if (!alreadyCall) {
			time++;
			if (question.getMaxTime() != null && question.getMaxTime() > 0) {
				int secondsRemaining = question.getMaxTime() - time;
				if (secondsRemaining > 0) {
					int minutes = secondsRemaining / 60;
					int seconds = secondsRemaining % 60;
					this.regressiveCounter = String.format("%02d:%02d", minutes, seconds);
				} else if (secondsRemaining == 0) {
					alreadyCall = true;
					questionBean.update(question, null, TEMPO, time);
				}
			}
		}
	}

	public void nextQuestion() {
		if (!alreadyCall) {
			alreadyCall = true;
			questionBean.update(question, null, PULO, time);
		}
	}

	@PostConstruct
	public void init() {
		question = questionBean.nextQuestion(sessao.getNextQuestionId());
		answers = question.getAnswers();
		Collections.shuffle(answers);
		questionImage = mediaBean.getAuxiliaryMediaPath(question);
		time = 0;
		alreadyCall = false;

		// final ExternalContext externalContext =
		// FacesContext.getCurrentInstance().getExternalContext();
		// externalContext.getResourceAsStream("/resources/images/question/" + id +
		// ".png") != null
	}
}
