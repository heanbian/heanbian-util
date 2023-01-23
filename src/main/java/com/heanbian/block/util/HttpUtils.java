package com.heanbian.block.util;

import static java.util.Objects.requireNonNull;

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

public final class HttpUtils {

	public static String doGet(String url, Map<String, String> params, Map<String, String> header, Duration timeout) {
		return internalGet(url, params, header, timeout);
	}

	public static String doPost(String url, Map<String, String> params, Map<String, String> header, Duration timeout) {
		return internalPost(url, params, header, timeout);
	}

	public static String doPost(String url, Duration timeout) {
		return internalPost(url, null, BodyPublishers.noBody(), timeout);
	}

	public static String doPost(String url, Map<String, String> header, String body, Duration timeout) {
		BodyPublisher bodyPublisher = (body == null) ? BodyPublishers.noBody() : BodyPublishers.ofString(body);
		return internalPost(url, header, bodyPublisher, timeout);
	}

	private static String internalGet(String url, Map<String, String> params, Map<String, String> header,
			Duration timeout) {
		requireNonNull(url, "url must not be empty");

		if (timeout == null) {
			timeout = Duration.ofSeconds(30);
		}

		if (params != null && !params.isEmpty()) {
			StringBuilder s = new StringBuilder();
			params.forEach((k, v) -> {
				s.append("&").append(k).append("=").append(URLEncoder.encode(v, Charset.defaultCharset()));
			});
			url += "?" + s.substring(1);
		}

		HttpRequest.Builder builder = HttpRequest.newBuilder();
		if (header != null && !header.isEmpty()) {
			header.forEach(builder::header);
		}

		HttpRequest request = builder.uri(URI.create(url)).timeout(timeout).GET().build();
		HttpClient client = HttpClient.newBuilder().build();

		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
		}
		return null;
	}

	private static String internalPost(String url, Map<String, String> params, Map<String, String> header,
			Duration timeout) {
		BodyPublisher body = BodyPublishers.noBody();

		if (params != null && !params.isEmpty()) {
			StringBuilder s = new StringBuilder();
			params.forEach((k, v) -> {
				s.append("&").append(k).append("=").append(URLEncoder.encode(v, Charset.defaultCharset()));
			});
			body = BodyPublishers.ofString(s.substring(1));
		}
		return internalPost(url, header, body, timeout);
	}

	private static String internalPost(String url, Map<String, String> header, BodyPublisher body, Duration timeout) {
		requireNonNull(url, "url must not be empty");

		if (timeout == null) {
			timeout = Duration.ofSeconds(30);
		}

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(timeout);
		if (header != null && !header.isEmpty()) {
			header.forEach(builder::header);
		}

		HttpRequest request = builder.POST(body).build();
		HttpClient client = HttpClient.newBuilder().build();

		try {
			return client.send(request, BodyHandlers.ofString()).body();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
		}
		return null;
	}

}
