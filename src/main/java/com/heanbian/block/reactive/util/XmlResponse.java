package com.heanbian.block.reactive.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResponse {

	public static final int SUCCESS = 0;
	public static final String SUCCESS_STRING = "success";

	public static final int FAIL = 1;
	public static final String FAIL_STRING = "fail";

	@XmlElement(name = "code")
	private int code;

	@XmlElement(name = "message")
	private String message;

	public static XmlResponse block(int code, String message) {
		return block(code, message);
	}

	public static XmlResponse success(String message) {
		return block(SUCCESS, message);
	}

	public static XmlResponse success() {
		return block(SUCCESS, SUCCESS_STRING);
	}

	public static XmlResponse fail(String message) {
		return block(FAIL, message);
	}

	public static XmlResponse fail() {
		return block(FAIL, FAIL_STRING);
	}

	protected XmlResponse() {
	}

	protected XmlResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public XmlResponse setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public XmlResponse setMessage(String message) {
		this.message = message;
		return this;
	}

}