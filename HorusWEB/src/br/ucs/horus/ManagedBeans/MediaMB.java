package br.ucs.horus.ManagedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.ucs.horus.bean.MediaBean;
import br.ucs.horus.models.FileMedia;
import br.ucs.horus.models.ImageMedia;
import br.ucs.horus.models.Media;
import br.ucs.horus.models.Media.Type;
import br.ucs.horus.utils.Sessao;
import br.ucs.horus.utils.Utils;
import br.ucs.horus.models.OnlineMedia;
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

	@Inject
	private Sessao sessao;

	public Sessao getSessao() {
		return sessao;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public List<? super Media> getRecommendedMedia() {
		return recommendedMedia;
	}

	public void carregarPagina(boolean isCorrect, Question question) {
		this.isCorrect = isCorrect;
		recommendedMedia = !isCorrect ? mediaBean.getRecommendedMedia(question) : null;
	}
	
	public boolean hasRecommendedMedia() {
		return !Utils.isEmpty(recommendedMedia);
	}

	public String getMediaIcon(int type) {
		return Type.getIcon(type);
	}

	public void onMediaSelected(Media media) {
		if (media instanceof FileMedia || media instanceof ImageMedia || media instanceof VideoMedia) {

			Map<String, Object> options = new HashMap<String, Object>();
			options.put("modal", true);
			options.put("headerElement", "dialogTitulo");
			options.put("contentHeight", "100%");
			options.put("contentWidth", "100%");

			options.put("width", 960);
			options.put("height", 540);

			options.put("closeOnEscape", true);
			options.put("fitViewport", true);
			options.put("responsive", true);

			Map<String, List<String>> params = new HashMap<>();
			String uuid = UUID.randomUUID().toString();
			params.put("br.ucs.horus.media", Arrays.asList(uuid));

			sessao.getMapa().put(uuid, media);

			PrimeFaces.current().dialog().openDynamic("/private/mediaDialog.xhtml", options, params);
		} else if (media instanceof OnlineMedia) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    try {
				externalContext.redirect(((OnlineMedia) media).getLink());
			} catch (IOException e) {
				Utils.showError("Link não pôde ser aberto");
				Utils.printLog(e.getMessage());
			}
		}
	}
	
	public String nextQuestion() {
		return "/private/question.jsf?faces-redirect=true";
	}
}
