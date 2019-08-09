package com.heanbian.block.reactive.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * @author Heanbian
 * @see Http
 */
public final class HttpUtils {

	public static RestResponse<String> doGet(String urlPart) {
		Objects.requireNonNull(urlPart, "urlPart must not be null");
		CloseableHttpResponse response = null;
		try {
			response = urlPart.startsWith("https://") ? Http.doHttpsGet(urlPart) : Http.doHttpGet(urlPart);
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.SS,
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return RestResponse.block(response.getStatusLine().getStatusCode(), e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static RestResponse<String> doPost(String url, String part) {
		Objects.requireNonNull(url, "url must not be null");
		CloseableHttpResponse response = null;
		try {
			response = url.startsWith("https://") ? Http.doHttpsPost(url, part) : Http.doHttpPost(url, part);
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.SS,
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return RestResponse.block(response.getStatusLine().getStatusCode(), e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static RestResponse<String> doPost(String url, Map<String, String> part) {
		Objects.requireNonNull(url, "url must not be null");
		CloseableHttpResponse response = null;
		try {
			response = url.startsWith("https://") ? Http.doHttpsPost(url, part) : Http.doHttpPost(url, part);
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.SS,
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return RestResponse.block(response.getStatusLine().getStatusCode(), e.getMessage());
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
