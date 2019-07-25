package com.heanbian.block.reactive.util;

import java.io.Reader;
import java.io.StringReader;
import java.util.Objects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class XmlUtils {

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

//	public static void main(String[] args) {
//		String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + 
//				"<response>" + 
//				"<code>0</code>" +
//				"<message>操作成功</message>" + 
//				"</response>";
//		HXmlResponse d = HXmlUtils.parseXml(xmlString, HXmlResponse.class);
//		System.out.println(d.toString());
//	}

}
