package br.ucs.horus.ManagedBeans;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import br.ucs.horus.bean.MediaBean;
import br.ucs.horus.bean.QuestionBean;
import br.ucs.horus.models.Answer;
import br.ucs.horus.models.Question;

@Named
@ViewScoped //session
public class QuestionMB implements Serializable {
	private static final long serialVersionUID = -1907939408935134046L;

	private Question question;
	private String questionImage, regressiveCounter;
	private Integer secondsRemaining;
	private int lastIndex = 1;
	private List<Answer> answers;
	
	@EJB
	private QuestionBean questionBean;

	@EJB
	private MediaBean mediaBean;
	
	public String onSelectAnswer(int index) {
		Answer answer = answers.get(index);
		
		//Fazer processamento
		
		return "/private/media.jsf?faces-redirect=true";
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

	public Integer getSecondsRemaining() {
		return secondsRemaining;
	}

	public void setSecondsRemaining(Integer secondsRemaining) {
		this.secondsRemaining = secondsRemaining;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
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
		answers = question.getAnswers();
		Collections.shuffle(answers);
		questionImage = mediaBean.getAuxiliaryMediaPath(question);
		secondsRemaining = question.getMaxTime();
		
		//final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				//externalContext.getResourceAsStream("/resources/images/question/" + id + ".png") != null
	}
}
