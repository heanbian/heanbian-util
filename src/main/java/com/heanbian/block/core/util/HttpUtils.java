package com.heanbian.block.core.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public static HResponse doGet(String urlPart) {
		Objects.requireNonNull(urlPart, "urlPart must not be null");
		CloseableHttpResponse response = null;
		try {
			response = urlPart.startsWith("https://") ? HCloseableHttpClient.doHttpsGet(urlPart)
					: HCloseableHttpClient.doHttpGet(urlPart);
			return HResponse.block(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return HResponse.fail(e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static HResponse doPost(String url, String part) {
		Objects.requireNonNull(url, "url must not be null");
		CloseableHttpResponse response = null;
		try {
			response = url.startsWith("https://") ? HCloseableHttpClient.doHttpsPost(url, part)
					: HCloseableHttpClient.doHttpPost(url, part);
			return HResponse.block(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return HResponse.fail(e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static HResponse doPost(String url, Map<String, String> part) {
		Objects.requireNonNull(url, "url must not be null");
		CloseableHttpResponse response = null;
		try {
			response = url.startsWith("https://") ? HCloseableHttpClient.doHttpsPost(url, part)
					: HCloseableHttpClient.doHttpPost(url, part);
			return HResponse.block(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return HResponse.fail(e.getMessage());
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
