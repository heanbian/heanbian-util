package com.heanbian.block.reactive.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Heanbian
 * @see Http
 */
public final class HttpUtils {

	public static String doGet(String url, Map<String, String> header) {
		try {
			return Http.doGet(url, header);
		} catch (Exception e) {// Ignore
		}
		return null;
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}

	public static String doPost(String url, String json) {
		Map<String, String> header = new HashMap<>(1);
		header.put("Content-Type", "application/json;charset=UTF-8");
		return doPost(url, json, header);
	}

	public static String doPost(String url, String args, Map<String, String> header) {
		try {
			return Http.doPost(url, args, header);
		} catch (Exception e) {// Ignore
		}
		return null;
	}

}
