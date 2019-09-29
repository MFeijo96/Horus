package br.ucs.horus.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ucs.horus.models.pk.UserSkillPK;

@Entity
@Table(name="Skills")
public class Skill {
	@Id
	@GeneratedValue
	private int id;

	private String name;
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
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
		
		final Skill other = (Skill) obj;
		
		return other.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + this.getId();
		return hash;
	}
}
