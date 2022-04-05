package com.heanbian.block.util;

public final class Rest<T> {

	private int code;
	private String message;
	private T data;

	public static <T> Rest<T> fail() {
		return new Rest<>(1, "fail");
	}

	public static <T> Rest<T> success() {
		return new Rest<>(0, "success");
	}

	public static <T> Rest<T> success(T data) {
		return new Rest<>(0, "success", data);
	}

	public static <T> Rest<T> of(int code) {
		return new Rest<>(code);
	}

	public static <T> Rest<T> of(int code, String message) {
		return new Rest<>(code, message);
	}

	public static <T> Rest<T> of(int code, String message, T data) {
		return new Rest<>(code, message, data);
	}

	public Rest() {
	}

	public Rest(int code) {
		this.code = code;
	}

	public Rest(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public Rest(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public Rest<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Rest<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public Rest<T> setData(T data) {
		this.data = data;
		return this;
	}

}