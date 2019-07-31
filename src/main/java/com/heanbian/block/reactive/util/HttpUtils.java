package com.heanbian.block.reactive.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Heanbian
 * @see Http
 */
public final class HttpUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static RestResponse<String> doGet(String urlPart) {
		return doGet(urlPart, String.class);
	}

	public static <T> RestResponse<T> doGet(String urlPart, Class<T> valueType) {
		Objects.requireNonNull(urlPart, "urlPart must not be null");
		Objects.requireNonNull(valueType, "valueType must not be null");
		CloseableHttpResponse response = null;
		try {
			response = urlPart.startsWith("https://") ? Http.doHttpsGet(urlPart) : Http.doHttpGet(urlPart);
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.SS, objectMapper
					.readValue(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), valueType));
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

	public static RestResponse<String> doPost(String url, String part) {
		return doPost(url, part, String.class);
	}

	public static <T> RestResponse<T> doPost(String url, String part, Class<T> valueType) {
		Objects.requireNonNull(url, "url must not be null");
		Objects.requireNonNull(valueType, "valueType must not be null");
		CloseableHttpResponse response = null;
		try {
			response = url.startsWith("https://") ? Http.doHttpsPost(url, part) : Http.doHttpPost(url, part);
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.SS, objectMapper
					.readValue(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), valueType));
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

	public static RestResponse<String> doPost(String url, Map<String, String> part) {
		return doPost(url, part, String.class);
	}

	public static <T> RestResponse<T> doPost(String url, Map<String, String> part, Class<T> valueType) {
		Objects.requireNonNull(url, "url must not be null");
		Objects.requireNonNull(valueType, "valueType must not be null");
		CloseableHttpResponse response = null;
		try {
			response = url.startsWith("https://") ? Http.doHttpsPost(url, part) : Http.doHttpPost(url, part);
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.SS, objectMapper
					.readValue(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), valueType));
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
