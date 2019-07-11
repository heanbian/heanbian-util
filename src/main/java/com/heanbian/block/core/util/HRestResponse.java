package com.heanbian.block.core.util;

public class HRestResponse {

	public static final int SUCCESS = 0;
	public static final int FAIL = 1;

	private int code;
	private String message;

	public static HRestResponse success(String message) {
		return block(SUCCESS, message);
	}

	public static HRestResponse fail(String message) {
		return block(FAIL, message);
	}

	public static HRestResponse block(int code, String message) {
		return new HRestResponse(code, message);
	}

	protected HRestResponse() {
	}

	protected HRestResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public HRestResponse setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public HRestResponse setMessage(String message) {
		this.message = message;
		return this;
	}

}