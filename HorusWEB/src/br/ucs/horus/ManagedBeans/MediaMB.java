package br.ucs.horus.ManagedBeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import br.ucs.horus.bean.MediaBean;
import br.ucs.horus.models.ImageMedia;
import br.ucs.horus.models.Media;
import br.ucs.horus.models.Media.Type;
import br.ucs.horus.models.Question;
import br.ucs.horus.models.VideoMedia;

@Named
@SessionScoped
public class MediaMB implements Serializable {
	private static final long serialVersionUID = 2511999536002075941L;

	private boolean isCorrect;
	private List<? super Media> recommendedMedia;
	
	@EJB
	private MediaBean mediaBean;
	
	public boolean isCorrect() {
		return isCorrect;
	}

	public List<? super Media> getRecommendedMedia() {
		return recommendedMedia;
	}

	public void carregarPagina(boolean isCorrect, Question question) {
		this.isCorrect = isCorrect;
		recommendedMedia = mediaBean.getRecommendedMedia(question);
	}
	
	public String getMediaIcon(int type) {
		return Type.getIcon(type);
	}
	
	public void onMediaSelected(Media media){
		System.out.println("mauricio batata" + media.getTitle()); 
	}
}
