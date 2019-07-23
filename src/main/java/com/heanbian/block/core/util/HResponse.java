package com.heanbian.block.core.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HResponse<T> {

	public static final int SUCCESS = 0;
	public static final String SUCCESS_STRING = "success";

	public static final int FAIL = 1;
	public static final String FAIL_STRING = "fail";

	private int code;
	private String message;
	private T data;

	public static <T> HResponse<T> block(int code, String message, T data) {
		return new HResponse<>(code, message, data);
	}

	public static <T> HResponse<T> block(int code, String message) {
		return block(code, message, null);
	}

	public static <T> HResponse<T> success(String message, T data) {
		return block(SUCCESS, message, data);
	}

	public static <T> HResponse<T> success(String message) {
		return block(SUCCESS, message);
	}

	public static <T> HResponse<T> success() {
		return block(SUCCESS, SUCCESS_STRING);
	}

	public static <T> HResponse<T> fail(String message, T data) {
		return block(FAIL, message, data);
	}

	public static <T> HResponse<T> fail(String message) {
		return block(FAIL, message);
	}

	public static <T> HResponse<T> fail() {
		return block(FAIL, FAIL_STRING);
	}

	public HResponse() {}

	public HResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public HResponse<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public HResponse<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public HResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

}