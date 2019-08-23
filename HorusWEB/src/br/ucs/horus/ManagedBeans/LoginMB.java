package br.ucs.horus.ManagedBeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.ucs.horus.Utils.Sessao;
import br.ucs.horus.bean.UserBean;
import br.ucs.horus.models.User;

@Named
@SessionScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 2645917878819223639L;
	private String user, password;
	private boolean rememberMe;
	
	@EJB
	private UserBean userBean;
	
	@Inject
	private Sessao sessao;

	public LoginMB() {

	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String login() {
		if (user == null || user.length() == 0 || password == null || password.length() == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					criarMsg("Login inválido", "Por favor, preencha os campos corretamente", FacesMessage.SEVERITY_ERROR));
			return null;
		}
		
		final User currentUser = userBean.login(user,  password);
		if (currentUser == null) { 
			FacesContext.getCurrentInstance().addMessage(null, criarMsg("Erro", "Usuário ou senha inválidos", FacesMessage.SEVERITY_ERROR));
			return null;
		} else {
			sessao.setCurrentUser(currentUser);
			return "/private/question.jsf?faces-redirect=true";
		}
	}

	protected FacesMessage criarMsg(String title, String msg, Severity categoria) {
		FacesMessage fm = new FacesMessage(title, msg);
		fm.setSeverity(categoria);
		return fm;
	}
}
