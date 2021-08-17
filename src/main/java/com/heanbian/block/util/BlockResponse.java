package com.heanbian.block.util;

public final class BlockResponse<T> {

	private int code;
	private String message;
	private T data;

	public static <T> BlockResponse<T> of(int code) {
		return new BlockResponse<>(code, null, null);
	}

	public static <T> BlockResponse<T> of(String message) {
		return new BlockResponse<>(0, message, null);
	}

	public static <T> BlockResponse<T> of(int code, String message) {
		return new BlockResponse<>(code, message, null);
	}

	public static <T> BlockResponse<T> of(String message, T data) {
		return new BlockResponse<>(0, message, data);
	}

	public static <T> BlockResponse<T> of(int code, String message, T data) {
		return new BlockResponse<>(code, message, data);
	}

	public BlockResponse() {
	}

	public BlockResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public BlockResponse<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public BlockResponse<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public BlockResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

}