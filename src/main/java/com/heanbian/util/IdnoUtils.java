package com.heanbian.util;

import module java.base;

public final class IdnoUtils {

    private static final List<String> PROVINCES = List.of(
        "11", "12", "13", "14", "15", "21", "22", "23", 
        "31", "32", "33", "34", "35", "36", "37", "41", 
        "42", "43", "44", "45", "46", "50", "51", "52", 
        "53", "54", "61", "62", "63", "64", "65", "71", 
        "81", "82", "91"
    );

    private static final char[] CHECK_CODES = { 
        '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'
    };
    
    private static final int[] WEIGHTS = { 
        7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };
    
    private static final String[] ZODIACS = { 
        "\u7334", "\u9e21", "\u72d7", "\u732a", 
        "\u9f20", "\u725b", "\u864e", "\u5154", 
        "\u9f99", "\u86c7", "\u9a6c", "\u7f8a"
    };

	public static String getSex(String idno) {
		return validated(idno) ? ((idno.charAt(16) - '0') % 2 == 0 ? "女" : "男") : "保密";
	}

	public static int getAge(String idno) {
		LocalDate birthday = getBirthday(idno);
		return birthday != null ? Period.between(birthday, LocalDate.now()).getYears() : -1;
	}

	public static LocalDate getBirthday(String idno) {
		if (!validated(idno)) {
			return null;
		}

		String dateStr = idno.substring(6, 14);
		try {
			return LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static boolean validated(String idno) {
		if (idno == null || idno.isEmpty() || idno.length() != 18) {
			return false;
		}
		
		if (!PROVINCES.contains(idno.substring(0, 2))) {
			return false;
		}

		char[] chars = idno.toCharArray();
		for (int i = 0; i < 17; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}

		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum += (chars[i] - '0') * WEIGHTS[i];
		}
		char checkCode = CHECK_CODES[sum % 11];

		return chars[17] == checkCode;
	}

	public static String getZodiac(String idno) {
		LocalDate date = getBirthday(idno);
		return date != null ? getZodiac(date.getYear()) : null;
	}

	public static String getZodiac(int year) {
		return ZODIACS[year % 12];
	}
}