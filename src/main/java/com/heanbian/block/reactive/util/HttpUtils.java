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
		return doGet0(url, null, null, null, Duration.ofSeconds(30));
	}

	public static String doGet(String url, Duration timeout) {
		return doGet0(url, null, null, null, timeout);
	}

	public static String doGet(String url, String jsonBody) {
		return doGet0(url, jsonBody, null, null, Duration.ofSeconds(30));
	}

	public static String doGet(String url, String jsonBody, Duration timeout) {
		return doGet0(url, jsonBody, null, null, timeout);
	}

	public static String doGet(String url, Map<String, String> params, Duration timeout) {
		return doGet0(url, null, params, null, timeout);
	}

	public static String doGet(String url, Map<String, String> params) {
		return doGet0(url, null, params, null, Duration.ofSeconds(30));
	}

	public static String doGet(String url, Map<String, String> params, Map<String, String> header) {
		return doGet0(url, null, params, header, Duration.ofSeconds(30));
	}

	public static String doGet(String url, Map<String, String> params, Map<String, String> header, Duration timeout) {
		return doGet0(url, null, params, header, timeout);
	}

	public static String doPost(String url) {
		return doPost0(url, null, null, null, Duration.ofSeconds(30));
	}

	public static String doPost(String url, Duration timeout) {
		return doPost0(url, null, null, null, timeout);
	}

	public static String doPost(String url, String jsonBody) {
		return doPost0(url, jsonBody, null, null, Duration.ofSeconds(30));
	}

	public static String doPost(String url, String jsonBody, Duration timeout) {
		return doPost0(url, jsonBody, null, null, timeout);
	}

	public static String doPost(String url, Map<String, String> params) {
		return doPost0(url, null, params, null, Duration.ofSeconds(30));
	}

	public static String doPost(String url, Map<String, String> params, Duration timeout) {
		return doPost0(url, null, params, null, timeout);
	}

	public static String doPost(String url, Map<String, String> params, Map<String, String> header) {
		return doPost0(url, null, params, header, Duration.ofSeconds(30));
	}

	public static String doPost(String url, Map<String, String> params, Map<String, String> header, Duration timeout) {
		return doPost0(url, null, params, header, timeout);
	}

	private static String doGet0(String url, String jsonBody, Map<String, String> params, Map<String, String> header,
			Duration timeout) {
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

		if (timeout == null) {
			timeout = Duration.ofSeconds(30);
		}

		HttpRequest request = builder.uri(URI.create(url)).timeout(timeout).GET().build();
		HttpClient client = HttpClient.newBuilder().build();

		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException e) {// Ignore
		} catch (InterruptedException e) {// Ignore
		}
		return null;
	}

	private static String doPost0(String url, String jsonBody, Map<String, String> params, Map<String, String> header,
			Duration timeout) {
		Objects.requireNonNull(url, "url must not be empty");

		if (timeout == null) {
			timeout = Duration.ofSeconds(30);
		}

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(timeout);
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
		return doPost(url, header, buf, Duration.ofSeconds(30));
	}

	public static byte[] doPost(String url, Map<String, String> header, byte[] buf, Duration timeout) {
		try {
			return doPost0(url, header, buf, timeout);
		} catch (IOException e) {// Ignore
		} catch (InterruptedException e) {// Ignore
		}
		return null;
	}

	private static byte[] doPost0(String url, Map<String, String> header, byte[] buf, Duration timeout)
			throws IOException, InterruptedException {
		Objects.requireNonNull(url, "url must not be empty");

		if (timeout == null) {
			timeout = Duration.ofSeconds(30);
		}

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(timeout);
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
