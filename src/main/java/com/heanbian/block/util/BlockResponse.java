package com.heanbian.block.util;

/**
 * 
 * @author heanbian
 *
 */
public final class BlockResponse<T> {

	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final String SUCCESS_STRING = "success";
	public static final String FAIL_STRING = "fail";

	private int code;
	private String message;
	private T data;

	public static <T> BlockResponse<T> block(int code, String message, T data) {
		return new BlockResponse<>(code, message, data);
	}

	public static <T> BlockResponse<T> block(int code, String message) {
		return block(code, message, null);
	}

	public static <T> BlockResponse<T> success(T data) {
		return block(SUCCESS, SUCCESS_STRING, data);
	}

	public static <T> BlockResponse<T> success() {
		return block(SUCCESS, SUCCESS_STRING);
	}

	public static <T> BlockResponse<T> fail(T data) {
		return block(FAIL, FAIL_STRING, data);
	}

	public static <T> BlockResponse<T> fail() {
		return block(FAIL, FAIL_STRING);
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