package br.ucs.horus.ManagedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.ucs.horus.bean.QuestionBean;
import br.ucs.horus.models.Question;

@Named
@ViewScoped //session
public class QuestionMB implements Serializable {
	private static final long serialVersionUID = -1907939408935134046L;

	private Question question;
	private String questionImage, regressiveCounter;
	private Integer secondsRemaining;
	private int lastIndex = 0;

	@EJB
	private QuestionBean questionBean;

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

	public Integer getSecondsRemaining() {
		return secondsRemaining;
	}

	public void setSecondsRemaining(Integer secondsRemaining) {
		this.secondsRemaining = secondsRemaining;
	}

	public void decrementCounter() {
		if (secondsRemaining > 0) {
			secondsRemaining--;
			int minutes = secondsRemaining / 60;
			int seconds = secondsRemaining % 60;
			this.regressiveCounter = String.format("%02d:%02d", minutes, seconds);	
		} else {
			// acabou o tempo
		}
	}
	
	public void nextQuestion() {
		//pulou
		System.out.println("Pulou");
	}

	@PostConstruct
	public void init() {
		question = questionBean.nextQuestion(lastIndex++);
		questionImage = getImagePath(question);
		secondsRemaining = question.getMaxTime();
	}

	private String getImagePath(Question question) {
		final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		int id = question.getId();
		if (externalContext.getResourceAsStream("/resources/images/question/" + id + ".png") != null) {
			return "question/" + id + ".png";
		} else if (externalContext.getResourceAsStream("/resources/images/question/" + id + ".jpg") != null) {
			return "question/" + id + ".jpg";
		} else if (externalContext.getResourceAsStream("/resources/images/question/" + id + ".jpeg") != null) {
			return "question/" + id + ".jpeg";
		}

		return null;
	}
}
