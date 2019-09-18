package br.ucs.horus.ManagedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ucs.horus.models.FileMedia;
import br.ucs.horus.models.ImageMedia;
import br.ucs.horus.models.Media;
import br.ucs.horus.models.VideoMedia;
import br.ucs.horus.utils.Sessao;
import br.ucs.horus.utils.Utils;

@Named
@ViewScoped
public class MediaDialogMB implements Serializable {
	private static final long serialVersionUID = -6151508621999059079L;
	private Media media;
	private String content;
	
	@Inject
	private Sessao sessao;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isFile() {
		return media instanceof FileMedia;
	}
	
	public boolean isVideo() {
		return media instanceof VideoMedia;
	}
	
	public boolean isImage() {
		return media instanceof ImageMedia;
	}
	
	@PostConstruct
	public void init() {
		String uuidPk = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("br.ucs.horus.media");
		if (!Utils.isEmpty(uuidPk)) {
			media = (Media) this.sessao.getMapa().remove(uuidPk);
			
			if (isFile()) {
				content = "/resources/content/question/reco/" + ((FileMedia)media).getPath();
			} else if (isImage()) {
				content = "/question/reco/" + ((ImageMedia)media).getPath();
			} else if (isVideo()) {
				content = "http://www.youtube.com/v/" + ((VideoMedia)media).getYoutubeLink();
			}
		}
	}

}
