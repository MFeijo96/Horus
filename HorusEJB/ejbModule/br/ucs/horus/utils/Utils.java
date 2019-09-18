package br.ucs.horus.utils;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Utils {
	public static final float FATOR_CHUTE = 0.9f;
	private static final String LOG = "Horus_Log";

	public static boolean isEmpty(List<?> objects) {
		return objects == null || objects.size() == 0;
	}

	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	public static void showError(String message) {
		FacesContext.getCurrentInstance().addMessage(null, criarMsg("Erro", message, FacesMessage.SEVERITY_ERROR));
	}

	private static FacesMessage criarMsg(String title, String msg, Severity categoria) {
		FacesMessage fm = new FacesMessage(title, msg);
		fm.setSeverity(categoria);
		return fm;
	}

	public static void printLog(String message) {
		System.out.println(LOG + " - " + message);
	}
}
