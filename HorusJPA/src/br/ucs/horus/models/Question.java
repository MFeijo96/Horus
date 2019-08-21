package br.ucs.horus.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Entity
@Table(name="Questions")
public class Question {
	@Id
	@GeneratedValue
	private int id;

	private String question;
	
	private Integer contentMedia_id;
	private int maxTime;

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

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
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
}
