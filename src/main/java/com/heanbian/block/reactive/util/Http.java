package com.heanbian.block.reactive.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

public final class Http {

	protected static String doGet(String url) throws IOException, InterruptedException {
		return doGet(url, null);
	}

	protected static String doGet(String url, Map<String, String> header) throws IOException, InterruptedException {
		Objects.requireNonNull(url, "url must not be empty");
		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(30));
		if (header != null && !header.isEmpty()) {
			for (Entry<String, String> entry : header.entrySet()) {
				builder.header(entry.getKey(), entry.getValue());
			}
		}
		HttpRequest request = builder.GET().build();
		HttpClient client = HttpClient.newBuilder().build();
		return client.send(request, BodyHandlers.ofString()).body();
	}

	protected static String doPost(String url) throws IOException, InterruptedException {
		return doPost(url, null, null);
	}

	protected static String doPost(String url, String args) throws IOException, InterruptedException {
		return doPost(url, args, null);
	}

	protected static String doPost(String url, String args, Map<String, String> header)
			throws IOException, InterruptedException {
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
		return client.send(request, BodyHandlers.ofString()).body();
	}

}
