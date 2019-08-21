package br.ucs.horus.ManagedBeans;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.ucs.horus.bean.QuestionBean;
import br.ucs.horus.bean.UserBean;
import br.ucs.horus.models.Answer;
import br.ucs.horus.models.Question;

@Named
@SessionScoped
public class QuestionMB implements Serializable {
	private static final long serialVersionUID = -1907939408935134046L;
	
	private Question question;
	private String questionImage;
	private int lastIndex = 3;
	
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
	@PostConstruct
    public void init(){
        question = questionBean.nextQuestion(lastIndex++);
        questionImage = getImagePath(question);
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
