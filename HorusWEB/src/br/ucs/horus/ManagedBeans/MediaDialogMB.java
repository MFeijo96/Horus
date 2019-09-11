package br.ucs.horus.ManagedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ucs.horus.Utils.Sessao;
import br.ucs.horus.utils.Utils;

@Named
@ViewScoped
public class MediaDialogMB implements Serializable {
	private static final long serialVersionUID = -6151508621999059079L;
	
	private String teste = "Finge que tem algo aqui";
		
	public String getTeste() {
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}

	@Inject
	private Sessao sessao;
	
	@PostConstruct
	public void init() {
		String uuidPk = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("br.ucs.horus.media");
		if (!Utils.isEmpty(uuidPk)) {
			String teste = (String) this.sessao.getMapa().remove(uuidPk);
			System.out.println(teste);
		}
	}

}
