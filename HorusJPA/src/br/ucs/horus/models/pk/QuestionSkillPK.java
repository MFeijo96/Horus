package br.ucs.horus.models.pk;

import java.io.Serializable;

public class QuestionSkillPK implements Serializable {
	private static final long serialVersionUID = -7652132261205711481L;
	
	protected int skill_id;
	protected int question_id;
	
	public QuestionSkillPK() {}
	
	public QuestionSkillPK(int question_id, int skill_id) {
		this.skill_id = skill_id;
		this.question_id = question_id;
	}
	
	public int getSkill_id() {
		return skill_id;
	}

	public void setSkill_id(int skill_id) {
		this.skill_id = skill_id;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		
		final QuestionSkillPK other = (QuestionSkillPK) obj;
		
		return other.getQuestion_id() == this.getQuestion_id() && other.getSkill_id() == this.getSkill_id();
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + this.getSkill_id();
		hash = hash * 31 + this.getQuestion_id();
		return hash;
	}
}
