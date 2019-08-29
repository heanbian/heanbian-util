package com.heanbian.block.reactive.util;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public final class HttpUtils {

	public static String doGet(String url) {
		return doGet0(url, null, null, null);
	}

	public static String doGet(String url, String jsonBody) {
		return doGet0(url, jsonBody, null, null);
	}

	public static String doGet(String url, Map<String, String> params) {
		return doGet0(url, null, params, null);
	}

	public static String doGet(String url, Map<String, String> params, Map<String, String> header) {
		return doGet0(url, null, params, header);
	}

	public static String doPost(String url) {
		return doPost0(url, null, null, null);
	}

	public static String doPost(String url, String jsonBody) {
		return doPost0(url, jsonBody, null, null);
	}

	public static String doPost(String url, Map<String, String> params) {
		return doPost0(url, null, params, null);
	}

	public static String doPost(String url, Map<String, String> params, Map<String, String> header) {
		return doPost0(url, null, params, header);
	}

	private static String doGet0(String url, String jsonBody, Map<String, String> params, Map<String, String> header) {
		Objects.requireNonNull(url, "url must not be empty");

		if (params != null && !params.isEmpty()) {
			StringBuilder s = new StringBuilder();
			for (Entry<String, String> entry : params.entrySet()) {
				s.append("&").append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), Charset.defaultCharset()));
			}
			url += "?" + s.substring(1);
		}

		HttpRequest.Builder builder = HttpRequest.newBuilder();
		if (jsonBody != null) {
			url += "?" + jsonBody;
			builder.header("Content-Type", "application/json;charset=UTF-8");
		}

		if (header != null && !header.isEmpty()) {
			for (Entry<String, String> entry : header.entrySet()) {
				builder.header(entry.getKey(), entry.getValue());
			}
		}

		HttpRequest request = builder.uri(URI.create(url)).timeout(Duration.ofSeconds(30)).GET().build();
		HttpClient client = HttpClient.newBuilder().build();

		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException e) {// Ignore
		} catch (InterruptedException e) {// Ignore
		}
		return null;
	}

	private static String doPost0(String url, String jsonBody, Map<String, String> params, Map<String, String> header) {
		Objects.requireNonNull(url, "url must not be empty");

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(30));
		if (header != null && !header.isEmpty()) {
			for (Entry<String, String> entry : header.entrySet()) {
				builder.header(entry.getKey(), entry.getValue());
			}
		}

		BodyPublisher body = BodyPublishers.noBody();

		if (params != null && !params.isEmpty()) {
			StringBuilder s = new StringBuilder();
			for (Entry<String, String> entry : params.entrySet()) {
				s.append("&").append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), Charset.defaultCharset()));
			}
			body = BodyPublishers.ofString(s.substring(1));
		}

		if (jsonBody != null) {
			body = BodyPublishers.ofString(jsonBody);
			builder.header("Content-Type", "application/json;charset=UTF-8");
		}

		HttpRequest request = builder.POST(body).build();
		HttpClient client = HttpClient.newBuilder().build();

		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException e) {// Ignore
		} catch (InterruptedException e) {// Ignore
		}
		return null;
	}

	public static byte[] doPost(String url, Map<String, String> header, byte[] buf) {
		try {
			return doPost0(url, header, buf);
		} catch (IOException e) {// Ignore
		} catch (InterruptedException e) {// Ignore
		}
		return null;
	}

	private static byte[] doPost0(String url, Map<String, String> header, byte[] buf)
			throws IOException, InterruptedException {
		Objects.requireNonNull(url, "url must not be empty");

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(30));
		if (header != null && !header.isEmpty()) {
			for (Entry<String, String> entry : header.entrySet()) {
				builder.header(entry.getKey(), entry.getValue());
			}
		}

		BodyPublisher body = BodyPublishers.ofByteArray(buf);

		HttpRequest request = builder.POST(body).build();
		HttpClient client = HttpClient.newBuilder().build();

		return client.send(request, BodyHandlers.ofByteArray()).body();
	}

}
