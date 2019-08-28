package br.ucs.horus.Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ucs.horus.models.User;

@Named
@SessionScoped
public class Sessao implements Serializable {
	private static final long serialVersionUID = -7119162199478968302L;

	private User currentUser;
	
	private Map<String, Object> mapa = new HashMap<>();

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public Map<String, Object> getMapa() {
		return this.mapa;
	}
}
