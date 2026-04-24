package com.heanbian.util;

import java.util.Comparator;

public final class NaturalOrderComparator<T> implements Comparator<T> {

    private final boolean caseInsensitive;

    public NaturalOrderComparator() {
        this(false);
    }

    public NaturalOrderComparator(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public int compare(T o1, T o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }

        String a = String.valueOf(o1);
        String b = String.valueOf(o2);

        int ia = 0;
        int ib = 0;
        int aLen = a.length();
        int bLen = b.length();

        while (ia < aLen && ib < bLen) {
            char ra = a.charAt(ia);
            char rb = b.charAt(ib);

            if (Character.isDigit(ra) && Character.isDigit(rb)) {
                int aEnd = findNumberEnd(a, ia);
                int bEnd = findNumberEnd(b, ib);

                int result = compareNumerically(a, ia, aEnd, b, ib, bEnd);
                if (result != 0) {
                    return result;
                }

                int rawLengthCompare = Integer.compare(aEnd - ia, bEnd - ib);
                if (rawLengthCompare != 0) {
                    return rawLengthCompare;
                }

                ia = aEnd;
                ib = bEnd;
                continue;
            }

            int charCompare = compareChars(ra, rb);
            if (charCompare != 0) {
                return charCompare;
            }

            if (caseInsensitive && ra != rb) {
                int fallback = Character.compare(ra, rb);
                if (fallback != 0) {
                    return fallback;
                }
            }

            ia++;
            ib++;
        }

        return Integer.compare(aLen - ia, bLen - ib);
    }

    private int findNumberEnd(String s, int start) {
        int end = start;
        while (end < s.length() && Character.isDigit(s.charAt(end))) {
            end++;
        }
        return end;
    }

    private int compareNumerically(String a, int aStart, int aEnd, String b, int bStart, int bEnd) {
        int aNonZero = aStart;
        while (aNonZero < aEnd && a.charAt(aNonZero) == '0') {
            aNonZero++;
        }

        int bNonZero = bStart;
        while (bNonZero < bEnd && b.charAt(bNonZero) == '0') {
            bNonZero++;
        }

        int aDigits = aEnd - aNonZero;
        int bDigits = bEnd - bNonZero;

        if (aDigits != bDigits) {
            return Integer.compare(aDigits, bDigits);
        }

        for (int i = 0; i < aDigits; i++) {
            char ac = a.charAt(aNonZero + i);
            char bc = b.charAt(bNonZero + i);
            if (ac != bc) {
                return Character.compare(ac, bc);
            }
        }

        return 0;
    }

    private int compareChars(char a, char b) {
        if (!caseInsensitive) {
            return Character.compare(a, b);
        }
        return Character.compare(Character.toUpperCase(a), Character.toUpperCase(b));
    }
}
