package br.ucs.horus.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Media")
public class Media {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int type;
	private String title;
	
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public enum Type {
	    IMAGE(1),VIDEO(2),LINK(3),FILE(4);
	 
	    public int value;
	    Type(int valor) {
	        value = valor;
	    }
	    
	    public static String getTableName(int type) {
	    	String tableName = null;
	    	if (type == IMAGE.value) tableName = "ImageMedia";
			else if (type == VIDEO.value) tableName = "VideoMedia";
			else if (type == LINK.value) tableName = "OnlineMedia";
			else if (type == FILE.value) tableName = "FileMedia";
			else throw new RuntimeException("Type não existente. Valor " + type);
	    	
	    	return tableName;
	    }
	    
	    public static String getIcon(int type) {
	    	String iconName = null;
	    	if (type == IMAGE.value) iconName = "fas fa-image fa-9x";
			else if (type == VIDEO.value) iconName = "fab fa-youtube  fa-9x";
			else if (type == LINK.value) iconName = "fas fa-link  fa-7x";
			else if (type == FILE.value) iconName = "fas fa-file-download fa-7x";
			else throw new RuntimeException("Type não existente. Valor " + type);
	    	
	    	return iconName;
	    }
	}
}
