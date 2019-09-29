package br.ucs.horus.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ucs.horus.models.pk.QuestionSkillPK;

@Entity
@Table(name="Question_Skill")
@IdClass(QuestionSkillPK.class)
public class QuestionSkill {
	@Id
	private int question_id;
	
	@Id
	private int skill_id;
	
	private float level;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedAt;

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public int getSkill_id() {
		return skill_id;
	}

	public void setSkill_id(int skill_id) {
		this.skill_id = skill_id;
	}

	public float getLevel() {
		return level;
	}

	public void setLevel(float level) {
		this.level = level;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
}
