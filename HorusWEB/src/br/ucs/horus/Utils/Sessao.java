package br.ucs.horus.Utils;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ucs.horus.models.User;

@Named
@SessionScoped
public class Sessao implements Serializable {
	private static final long serialVersionUID = -7119162199478968302L;

	private User currentUser;

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
