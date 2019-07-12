package com.heanbian.block.core.util;

import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/*
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
*/

public class HXmlUtils {

	@SuppressWarnings("unchecked")
	public static <T> T parseXml(String xmlString, Class<T> clazz) {
		Objects.requireNonNull(xmlString, "xmlString must not be null");
		Objects.requireNonNull(clazz, "clazz must not be null");
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			try (Reader reader = new StringReader(xmlString)) {
				return (T) unmarshaller.unmarshal(reader);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	public static void main(String[] args) {
		String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + 
				"<response>" + 
				"<code>0</code>" +
				"<message>操作成功</message>" + 
				"</response>";

		HRestResponseXml d = HXmlUtils.parseXml(xmlString, HRestResponseXml.class);
		System.out.println(d.toString());
	}

	@XmlRootElement(name = "response")
	@XmlAccessorType(XmlAccessType.FIELD)
	static class HRestResponseXml {

		@XmlElement(name = "code")
		private int code;

		@XmlElement(name = "message")
		private String message;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
	*/
}
