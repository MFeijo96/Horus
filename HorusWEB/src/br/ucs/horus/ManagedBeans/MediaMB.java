package br.ucs.horus.ManagedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ucs.horus.Utils.Sessao;
import br.ucs.horus.models.Question;

@Named
@SessionScoped
public class MediaMB implements Serializable {
	private static final long serialVersionUID = 2511999536002075941L;

	private String teste;

	public String getTeste() {
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}
	
	public void carregarPagina(boolean isCorrect, Question question) {
		teste = "Está correto? " + isCorrect + "\nQual a questão:" + question.getQuestion();
	}
}
