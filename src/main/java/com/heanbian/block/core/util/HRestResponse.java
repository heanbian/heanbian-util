package com.heanbian.block.core.util;

public class HRestResponse<T> {

	public static final int SUCCESS = 0;
	public static final String SUCCESS_STRING = "success";

	public static final int FAIL = 1;
	public static final String FAIL_STRING = "fail";

	private int code;
	private String message;
	private T data;

	public static <T> HRestResponse<T> block(int code, String message, T data) {
		return new HRestResponse<>(code, message, data);
	}

	public static <T> HRestResponse<T> block(int code, String message) {
		return block(code, message, null);
	}

	public static <T> HRestResponse<T> success(String message) {
		return block(SUCCESS, message);
	}

	public static <T> HRestResponse<T> success() {
		return block(SUCCESS, SUCCESS_STRING);
	}

	public static <T> HRestResponse<T> success(String message, T data) {
		return block(SUCCESS, message, data);
	}

	public static <T> HRestResponse<T> fail(String message) {
		return block(FAIL, message);
	}

	public static <T> HRestResponse<T> fail() {
		return block(FAIL, FAIL_STRING);
	}

	public static <T> HRestResponse<T> fail(String message, T data) {
		return block(FAIL, message, data);
	}

	protected HRestResponse() {
	}

	protected HRestResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public HRestResponse<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public HRestResponse<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public HRestResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

}