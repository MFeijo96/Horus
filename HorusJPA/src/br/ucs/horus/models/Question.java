package br.ucs.horus.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;

@Entity
@Table(name="Questions")
public class Question {
	@Id
	@GeneratedValue
	private int id;

	private String question;
	
	private Integer contentMedia_id;
	private Integer maxTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedAt;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id", referencedColumnName = "id", insertable = false, updatable = false)
	private List<Answer> answers;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getContentMedia_id() {
		return contentMedia_id;
	}

	public void setContentMedia_id(Integer contentMedia_id) {
		this.contentMedia_id = contentMedia_id;
	}

	public Integer getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public enum Reason {
	    ACERTO(1),ERRO(2),TEMPO(3),PULO(4),CHUTE(5);
	 
	    public int value;
	    Reason(int valor) {
	        value = valor;
	    }
	    
	    public static float getFator(Reason reason) {
	    	if (reason == ERRO) {
	    		return -1;
	    	} else if (reason == TEMPO) {
	    		return -0.25f;
	    	} else if (reason == PULO || reason == CHUTE) {
	    		return -0.5f;
	    	} else {
	    		throw new RuntimeException("Nenhum fator estabelecido para este motivo");
	    	}
	    }
	}
}
