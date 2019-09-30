package br.ucs.horus.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Utils {
	public static final float FATOR_CHUTE = 0.9f;
	public static final Random random = new Random();
	private static final String LOG_INFO = "Horus_Log_Info";
	private static final String LOG_DEBUG = "Horus_Log_Debug";

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
	
	public static float round(float d, int decimalPlace) {
	    return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	public static void printLogInfo(String message) {
		System.out.println(LOG_INFO + " - " + message);
	}
	
	public static void printLogDebug(String message) {
		System.out.println(LOG_DEBUG + " - " + message);
	}
	
	public static int getNextRandom(int max) {
		return random.nextInt(max);
	}
	
	public static void redirect(String page) throws Exception {
		if (!Utils.isEmpty(page)) {
			String uri = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			page = page.replaceAll("\\?faces-redirect=true", "");
			FacesContext.getCurrentInstance().getExternalContext().redirect(uri + page);
		}
	}
}
