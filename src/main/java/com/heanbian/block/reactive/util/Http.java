package com.heanbian.block.reactive.util;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

public final class Http {

	private static SSLConnectionSocketFactory socketFactory;
	static {
		enableSSL();
	}

	protected static CloseableHttpResponse doGet(String urlPart) {
		Objects.requireNonNull(urlPart, "urlPart must not be empty");
		try {
			CloseableHttpClient client = client(urlPart);
			URL url = new URL(urlPart);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			HttpGet httpget = new HttpGet(uri);
			return client.execute(httpget);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static CloseableHttpResponse doPost(String url, String json_params, Map<String, String> params,
			Map<String, String> headers) {
		Objects.requireNonNull(url, "url must not be empty");
		try {
			CloseableHttpClient client = client(url);
			HttpPost httppost = new HttpPost(url);
			if (json_params != null) {
				StringEntity json = new StringEntity(json_params, "UTF-8");
				json.setContentEncoding("UTF-8");
				httppost.setEntity(json);
				httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
			}
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<>();
				for (Entry<String, String> entry : params.entrySet()) {
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8);
				httppost.setEntity(entity);
			}
			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, String> entry : headers.entrySet()) {
					httppost.addHeader(entry.getKey(), entry.getValue());
				}
			}
			return client.execute(httppost);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void enableSSL() {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] c, String s) {}
				public void checkServerTrusted(X509Certificate[] c, String s) {}
				public X509Certificate[] getAcceptedIssuers() {return null;}} }, null);
			socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static CloseableHttpClient client(String url) {
		Objects.requireNonNull(url, "url msut not be empty");
		CloseableHttpClient client;
		if (url.startsWith("https://")) {
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
					.setExpectContinueEnabled(true)
					.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
					.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			client = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(config)
					.build();
		} else {
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
			client = HttpClients.custom().setDefaultRequestConfig(config).build();
		}
		return client;
	}

}
