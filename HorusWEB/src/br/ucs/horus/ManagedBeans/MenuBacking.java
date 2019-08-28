package br.ucs.horus.ManagedBeans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ucs.horus.Utils.Sessao;
import br.ucs.horus.models.User;

@Named
@SessionScoped
public class MenuBacking implements Serializable {
	
	private static final long serialVersionUID = -1047067591991030638L;
	
	@Inject
	private Sessao sessao;
	
	public String getUsername() {
		final User user = sessao.getCurrentUser();
		return "Olá " + (user == null || user.getFirstName() == null ? "Desconhecido" : user.getFirstName());
	}
}
