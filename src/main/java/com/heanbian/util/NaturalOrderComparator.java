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
        String a = o1.toString();
        String b = o2.toString();

        int ia = 0, ib = 0;
        int aLen = a.length(), bLen = b.length();

        while (ia < aLen && ib < bLen) {
            char ca = charAt(a, ia);
            char cb = charAt(b, ib);

            if (Character.isDigit(ca) && Character.isDigit(cb)) {
                // 定位数字块结尾
                int aEnd = findNumberEnd(a, ia);
                int bEnd = findNumberEnd(b, ib);

                int compareResult = compareNumerically(a, ia, aEnd, b, ib, bEnd);
                if (compareResult != 0) {
                    return compareResult;
                }

                // 移动索引至数字块后
                ia = aEnd;
                ib = bEnd;
            } else {
                if (ca != cb) {
                    return ca - cb;
                }
                ia++;
                ib++;
            }
        }

        // 处理剩余长度差异
        return Integer.compare(aLen - ia, bLen - ib);
    }

    private int findNumberEnd(String s, int start) {
        int end = start;
        while (end < s.length() && Character.isDigit(s.charAt(end))) {
            end++;
        }
        return end;
    }

    private int compareNumerically(String a, int aStart, int aEnd,
                                   String b, int bStart, int bEnd) {
        // 跳过前导零
        int aNonZero = aStart;
        while (aNonZero < aEnd && a.charAt(aNonZero) == '0') aNonZero++;
        int bNonZero = bStart;
        while (bNonZero < bEnd && b.charAt(bNonZero) == '0') bNonZero++;

        // 计算有效数字长度
        int aDigits = aEnd - aNonZero;
        int bDigits = bEnd - bNonZero;

        // 比较有效数字长度
        if (aDigits != bDigits) {
            return Integer.compare(aDigits, bDigits);
        }

        // 逐位比较有效数字
        for (int i = 0; i < aDigits; i++) {
            char ac = a.charAt(aNonZero + i);
            char bc = b.charAt(bNonZero + i);
            if (ac != bc) {
                return Character.compare(ac, bc);
            }
        }

        // 处理全零情况，比较块长度
        if (aDigits == 0) {
            return Integer.compare(aEnd - aStart, bEnd - bStart);
        }

        return 0; // 数值相等
    }

    private char charAt(String s, int index) {
        if (index >= s.length()) {
            return 0;
        }
        char c = s.charAt(index);
        return caseInsensitive ? Character.toUpperCase(c) : c;
    }
}