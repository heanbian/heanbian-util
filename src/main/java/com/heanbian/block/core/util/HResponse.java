package com.heanbian.block.core.util;

public class HResponse {

	public static final int SUCCESS = 0;
	public static final String SUCCESS_STRING = "success";

	public static final int FAIL = 1;
	public static final String FAIL_STRING = "fail";

	private int code;
	private String message;

	public static HResponse block(int code, String message) {
		return block(code, message);
	}

	public static HResponse success(String message) {
		return block(SUCCESS, message);
	}

	public static HResponse success() {
		return block(SUCCESS, SUCCESS_STRING);
	}

	public static HResponse fail(String message) {
		return block(FAIL, message);
	}

	public static HResponse fail() {
		return block(FAIL, FAIL_STRING);
	}

	protected HResponse() {
	}

	protected HResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public HResponse setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public HResponse setMessage(String message) {
		this.message = message;
		return this;
	}

}