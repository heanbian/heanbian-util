package com.heanbian.block.reactive.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public final class ReactiveHttpUtils {

	public static RestResponse<?> doGet(String urlPart) {
		Objects.requireNonNull(urlPart, "urlPart must not be null");
		CloseableHttpResponse response = null;
		try {
			response = urlPart.startsWith("https://") ? ReactiveCloseableHttpClient.doHttpsGet(urlPart)
					: ReactiveCloseableHttpClient.doHttpGet(urlPart);
			return RestResponse.block(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return RestResponse.fail(e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static RestResponse<?> doPost(String url, String part) {
		Objects.requireNonNull(url, "url must not be null");
		CloseableHttpResponse response = null;
		try {
			response = url.startsWith("https://") ? ReactiveCloseableHttpClient.doHttpsPost(url, part)
					: ReactiveCloseableHttpClient.doHttpPost(url, part);
			return RestResponse.block(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return RestResponse.fail(e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static RestResponse<?> doPost(String url, Map<String, String> part) {
		Objects.requireNonNull(url, "url must not be null");
		CloseableHttpResponse response = null;
		try {
			response = url.startsWith("https://") ? ReactiveCloseableHttpClient.doHttpsPost(url, part)
					: ReactiveCloseableHttpClient.doHttpPost(url, part);
			return RestResponse.block(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return RestResponse.fail(e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
