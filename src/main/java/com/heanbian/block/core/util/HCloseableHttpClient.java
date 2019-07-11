package com.heanbian.block.core.util;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

public class HCloseableHttpClient {
	private static SSLConnectionSocketFactory socketFactory;

	public static CloseableHttpResponse doHttpGet(String urlPart) {
		Objects.requireNonNull(urlPart, "urlPart must not be null");
		try {
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
			CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build();
			URL url = new URL(urlPart);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			HttpGet get = new HttpGet(uri);
			return client.execute(get);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CloseableHttpResponse doHttpsGet(String urlPart) {
		Objects.requireNonNull(urlPart, "urlPart must not be null");
		try {
			enableSSL();
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
					.setExpectContinueEnabled(true)
					.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
					.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(config).build();
			URL url = new URL(urlPart);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			HttpGet get = new HttpGet(uri);
			return client.execute(get);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CloseableHttpResponse doHttpPost(String url, String part) {
		Objects.requireNonNull(url, "url must not be null");
		Objects.requireNonNull(part, "part must not be null");
		try {
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
			CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build();
			HttpPost post = new HttpPost(url);
			if (part != null) {
				StringEntity entity = new StringEntity(part, StandardCharsets.UTF_8);
				post.setEntity(entity);
			}
			return client.execute(post);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CloseableHttpResponse doHttpsPost(String url, String part) {
		Objects.requireNonNull(url, "url must not be null");
		Objects.requireNonNull(part, "part must not be null");
		try {
			enableSSL();
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
					.setExpectContinueEnabled(true)
					.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
					.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(config).build();
			HttpPost post = new HttpPost(url);
			if (part != null) {
				StringEntity entity = new StringEntity(part, StandardCharsets.UTF_8);
				post.setEntity(entity);
			}
			return client.execute(post);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CloseableHttpResponse doHttpPost(String url, Map<String, String> part) {
		Objects.requireNonNull(url, "url must not be null");
		Objects.requireNonNull(part, "part must not be null");
		try {
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
			CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<>();
			if (part != null && !part.isEmpty()) {
				for (Map.Entry<String, String> entry : part.entrySet()) {
					parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8);
				post.setEntity(entity);
			}
			return client.execute(post);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CloseableHttpResponse doHttpsPost(String url, Map<String, String> part) {
		Objects.requireNonNull(url, "url must not be null");
		Objects.requireNonNull(part, "part must not be null");
		try {
			enableSSL();
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
					.setExpectContinueEnabled(true)
					.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
					.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(config).build();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<>();
			if (part != null && !part.isEmpty()) {
				for (Map.Entry<String, String> entry : part.entrySet()) {
					parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8);
				post.setEntity(entity);
			}
			return client.execute(post);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static TrustManager manager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] certificate, String s) {
		}

		public void checkServerTrusted(X509Certificate[] certificate, String s) {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	private static void enableSSL() {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { manager }, null);
			socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
