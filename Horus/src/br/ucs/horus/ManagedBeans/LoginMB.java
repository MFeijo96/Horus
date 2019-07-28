package br.ucs.horus.ManagedBeans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 2645917878819223639L;
	private String teste = "batata";
	
	public LoginMB() {
		
	}

	public String getTeste() {
		return teste;
	}
}
