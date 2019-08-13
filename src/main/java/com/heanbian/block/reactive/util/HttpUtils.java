package com.heanbian.block.reactive.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public final class HttpUtils {

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doGet(String url, Map<String, String> header) {
		Objects.requireNonNull(url, "url must not be empty");
		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(30));
		if (header != null && !header.isEmpty()) {
			for (Entry<String, String> entry : header.entrySet()) {
				builder.header(entry.getKey(), entry.getValue());
			}
		}
		HttpRequest request = builder.GET().build();
		HttpClient client = HttpClient.newBuilder().build();
		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException e) {// Ignore
		} catch (InterruptedException e) {// Ignore
		}
		return null;
	}

	public static String doPost(String url) {
		return doPost(url, null, null);
	}

	public static String doPost(String url, String json) {
		Map<String, String> header = new HashMap<>(1);
		header.put("Content-Type", "application/json;charset=UTF-8");
		return doPost(url, json, null);
	}

	public static String doPost(String url, String args, Map<String, String> header) {
		Objects.requireNonNull(url, "url must not be empty");
		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(30));
		if (header != null && !header.isEmpty()) {
			for (Entry<String, String> entry : header.entrySet()) {
				builder.header(entry.getKey(), entry.getValue());
			}
		}
		BodyPublisher body = args != null ? BodyPublishers.ofString(args) : BodyPublishers.noBody();
		HttpRequest request = builder.POST(body).build();
		HttpClient client = HttpClient.newBuilder().build();
		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException e) {// Ignore
		} catch (InterruptedException e) {// Ignore
		}
		return null;
	}

}
