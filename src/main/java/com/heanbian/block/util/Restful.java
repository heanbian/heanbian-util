package com.heanbian.block.util;

public record Restful<T> (int code, String message, T data) {

	public static <T> Restful<T> fail() {
		return new Restful<>(1, "fail", null);
	}

	public static <T> Restful<T> success() {
		return new Restful<>(0, "success", null);
	}

	public static <T> Restful<T> success(T data) {
		return new Restful<>(0, "success", data);
	}

	public static <T> Restful<T> of(int code) {
		return new Restful<>(code, null, null);
	}

	public static <T> Restful<T> of(int code, String message) {
		return new Restful<>(code, message, null);
	}

	public static <T> Restful<T> of(int code, String message, T data) {
		return new Restful<>(code, message, data);
	}

}