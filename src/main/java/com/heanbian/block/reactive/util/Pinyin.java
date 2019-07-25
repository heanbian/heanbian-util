package com.heanbian.block.reactive.util;

import java.util.Objects;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public final class Pinyin {

	public static String pinyin(String text) {
		Objects.requireNonNull(text, "text must not be null");

		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] input = text.trim().toCharArray();
		StringBuffer output = new StringBuffer();

		try {
			for (int i = 0, len = input.length; i < len; i++) {
				if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
					output.append(temp[0]);
				} else {
					output.append(Character.toString(input[i]));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			throw new RuntimeException(e);
		}
		return output.toString();
	}
}
