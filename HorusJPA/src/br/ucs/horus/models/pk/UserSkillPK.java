package br.ucs.horus.models.pk;

import java.io.Serializable;

public class UserSkillPK implements Serializable {
	private static final long serialVersionUID = -7652132261205711481L;
	
	protected int user_id;
	protected int skill_id;
	
	public UserSkillPK() {}
	
	public UserSkillPK(int user_id, int skill_id) {
		this.user_id = user_id;
		this.skill_id = skill_id;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getSkill_id() {
		return skill_id;
	}

	public void setSkill_id(int skill_id) {
		this.skill_id = skill_id;
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
		
		final UserSkillPK other = (UserSkillPK) obj;
		
		return other.getUser_id() == this.getUser_id() && other.getSkill_id() == this.getSkill_id();
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + this.getUser_id();
		hash = hash * 31 + this.getSkill_id();
		return hash;
	}
}
