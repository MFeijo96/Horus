package br.ucs.horus.utils;

import java.util.List;

public class Utils {
	public static boolean isEmpty(List<?> objects) {
		return objects == null || objects.size() == 0;
	}
	
	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
}
