package com.heanbian.block.core.util;

public class HRestResponse<T> extends HResponse {

	private T data;

	public static <T> HRestResponse<T> block(int code, String message, T data) {
		return new HRestResponse<>(code, message, data);
	}

	public static <T> HRestResponse<T> success(String message, T data) {
		return block(SUCCESS, message, data);
	}

	public static <T> HRestResponse<T> fail(String message, T data) {
		return block(FAIL, message, data);
	}

	protected HRestResponse() {
	}

	protected HRestResponse(int code, String message, T data) {
		super(code, message);
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public HRestResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

}