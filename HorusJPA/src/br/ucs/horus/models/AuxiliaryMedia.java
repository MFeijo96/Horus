package br.ucs.horus.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AuxiliaryMedia")
public class AuxiliaryMedia implements Serializable {
	private static final long serialVersionUID = 533770744396384582L;
	
	@Id
	private int id;
	
	private int question_id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedAt;
	
	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
}
