package com.heanbian.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Set;

public final class IdnoUtils {

    private static final Set<String> PROVINCES = Set.of(
            "11", "12", "13", "14", "15",
            "21", "22", "23",
            "31", "32", "33", "34", "35", "36", "37",
            "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54",
            "61", "62", "63", "64", "65",
            "71", "81", "82", "91"
    );

    private static final char[] CHECK_CODES = {
            '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'
    };

    private static final int[] WEIGHTS = {
            7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };

    private static final String[] ZODIACS = {
            "猴", "鸡", "狗", "猪",
            "鼠", "牛", "虎", "兔",
            "龙", "蛇", "马", "羊"
    };

    private IdnoUtils() {
    }

    public static String getSex(String idno) {
        String value = normalize(idno);
        if (value == null || !validated(value)) {
            return "保密";
        }
        return ((value.charAt(16) - '0') % 2 == 0) ? "女" : "男";
    }

    public static int getAge(String idno) {
        LocalDate birthday = getBirthday(idno);
        return birthday != null ? Period.between(birthday, LocalDate.now()).getYears() : -1;
    }

    public static LocalDate getBirthday(String idno) {
        String value = normalize(idno);
        if (value == null || !validated(value)) {
            return null;
        }
        return parseBirthday(value);
    }

    public static boolean validated(String idno) {
        String value = normalize(idno);
        if (value == null) {
            return false;
        }

        if (!PROVINCES.contains(value.substring(0, 2))) {
            return false;
        }

        for (int i = 0; i < 17; i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }

        LocalDate birthday = parseBirthday(value);
        if (birthday == null || birthday.isAfter(LocalDate.now())) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (value.charAt(i) - '0') * WEIGHTS[i];
        }

        return value.charAt(17) == CHECK_CODES[sum % 11];
    }

    public static String getZodiac(String idno) {
        LocalDate birthday = getBirthday(idno);
        return birthday != null ? getZodiac(birthday.getYear()) : null;
    }

    public static String getZodiac(int year) {
        return ZODIACS[Math.floorMod(year, 12)];
    }

    private static LocalDate parseBirthday(String idno) {
        String dateStr = idno.substring(6, 14);
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    private static String normalize(String idno) {
        if (idno == null) {
            return null;
        }
        String value = idno.trim().toUpperCase(Locale.ROOT);
        return value.length() == 18 ? value : null;
    }
}
