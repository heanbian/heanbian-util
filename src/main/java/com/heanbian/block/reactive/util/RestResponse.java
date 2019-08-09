package com.heanbian.block.reactive.util;

public class RestResponse<T> {

	private static final int S = 0;
	private static final int F = 1;
	public static final String SS = "success";
	public static final String FF = "fail";

	private int code;
	private String message;
	private T data;

	public static final RestResponse<String> SUCCESS = RestResponse.success();
	public static final RestResponse<String> FAIL = RestResponse.fail();

	public static <T> RestResponse<T> block(int code, String message, T data) {
		return new RestResponse<>(code, message, data);
	}

	public static <T> RestResponse<T> block(int code, String message) {
		return block(code, message, null);
	}

	public static <T> RestResponse<T> success(String message, T data) {
		return block(S, message, data);
	}

	public static <T> RestResponse<T> success(String message) {
		return block(S, message);
	}

	public static <T> RestResponse<T> success() {
		return block(S, SS);
	}

	public static <T> RestResponse<T> fail(String message, T data) {
		return block(F, message, data);
	}

	public static <T> RestResponse<T> fail(String message) {
		return block(F, message);
	}

	public static <T> RestResponse<T> fail() {
		return block(F, FF);
	}

	public RestResponse() {}

	public RestResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public RestResponse<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public RestResponse<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public RestResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

}