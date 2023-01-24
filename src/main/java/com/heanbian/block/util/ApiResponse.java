package com.heanbian.block.util;

public record ApiResponse<T>(int code, String message, T data) {

	public static <T> ApiResponse<T> fail() {
		return new ApiResponse<>(1, "failed", null);
	}

	public static <T> ApiResponse<T> success() {
		return new ApiResponse<>(0, "success", null);
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(0, "success", data);
	}

	public static <T> ApiResponse<T> of(int code) {
		return new ApiResponse<>(code, null, null);
	}

	public static <T> ApiResponse<T> of(int code, String message) {
		return new ApiResponse<>(code, message, null);
	}

	public static <T> ApiResponse<T> of(int code, String message, T data) {
		return new ApiResponse<>(code, message, data);
	}

}