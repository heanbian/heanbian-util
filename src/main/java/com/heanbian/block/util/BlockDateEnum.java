package com.heanbian.block.util;

public enum BlockDateEnum {

	DATE_TIME("yyyy-MM-dd HH:mm:ss"), DATE("yyyy-MM-dd"), TIME("HH:mm:ss"), DATE_TIME_OF("yyyyMMddHHmmss"),
	DATE_OF("yyyyMMdd"), TIME_OF("HHmmss");

	private final String value;

	BlockDateEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
