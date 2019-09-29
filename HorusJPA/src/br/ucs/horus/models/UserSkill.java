package br.ucs.horus.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ucs.horus.models.pk.UserSkillPK;

@Entity
@Table(name="User_Skill")
@IdClass(UserSkillPK.class)
public class UserSkill {
	@Id
	private int user_id;
	
	@Id
	private int skill_id;
	
	private float level;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedAt;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getSkill_id() {
		return this.skill_id;
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
}
