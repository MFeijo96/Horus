package br.ucs.horus.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Media")
public class Media {
	@Id
	@GeneratedValue
	private int id;

	private int type;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	public enum Type {
	    IMAGE(1),VIDEO(2),LINK(3),FILE(4);
	 
	    public int value;
	    Type(int valor) {
	        value = valor;
	    }
	}
}
