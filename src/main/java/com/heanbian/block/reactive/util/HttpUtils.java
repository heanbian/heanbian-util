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
			response = Http.doGet(urlPart);
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.SS,
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.FF, e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static RestResponse<String> doPost(String url, String json_params) {
		return doPost(url, json_params, null, null);
	}

	public static RestResponse<String> doPost(String url, Map<String, String> params) {
		return doPost(url, null, params, null);
	}

	public static RestResponse<String> doPost(String url, Map<String, String> params, Map<String, String> headers) {
		return doPost(url, null, params, headers);
	}

	public static RestResponse<String> doPost(String url, String json_params, Map<String, String> params,
			Map<String, String> headers) {
		Objects.requireNonNull(url, "url must not be null");
		CloseableHttpResponse response = null;
		try {
			response = Http.doPost(url, json_params, params, headers);
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.SS,
					EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return RestResponse.block(response.getStatusLine().getStatusCode(), RestResponse.FF, e.getMessage());
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
