package com.heanbian.block.core.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class HXmlResponse {

	public static final int SUCCESS = 0;
	public static final String SUCCESS_STRING = "success";

	public static final int FAIL = 1;
	public static final String FAIL_STRING = "fail";

	@XmlElement(name = "code")
	private int code;

	@XmlElement(name = "message")
	private String message;

	public static HXmlResponse block(int code, String message) {
		return block(code, message);
	}

	public static HXmlResponse success(String message) {
		return block(SUCCESS, message);
	}

	public static HXmlResponse success() {
		return block(SUCCESS, SUCCESS_STRING);
	}

	public static HXmlResponse fail(String message) {
		return block(FAIL, message);
	}

	public static HXmlResponse fail() {
		return block(FAIL, FAIL_STRING);
	}

	protected HXmlResponse() {
	}

	protected HXmlResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public HXmlResponse setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public HXmlResponse setMessage(String message) {
		this.message = message;
		return this;
	}

}