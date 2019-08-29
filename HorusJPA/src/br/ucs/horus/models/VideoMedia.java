package br.ucs.horus.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VideoMedia")
public class VideoMedia {
	@Id
	private int id;
	
	private String youtubeLink;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}
}
