package com.heanbian.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;
import java.util.zip.CRC32;

public final class HashUtils {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final HexFormat HEX = HexFormat.of().withLowerCase();

    private HashUtils() {
    }

    public static String generateShortHash(String input) {
        return crc32Base62(input);
    }

    public static String crc32Base62(String input) {
        Objects.requireNonNull(input, "input must not be null");

        CRC32 crc32 = new CRC32();
        crc32.update(input.getBytes(StandardCharsets.UTF_8));
        long checksum = crc32.getValue();
        return toBase62(checksum);
    }

    public static String sha256Hex(String input) {
        Objects.requireNonNull(input, "input must not be null");

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HEX.formatHex(bytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 is not available", ex);
        }
    }

    private static String toBase62(long value) {
        if (value == 0L) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        long current = value;
        while (current > 0) {
            int index = (int) (current % 62);
            sb.append(BASE62.charAt(index));
            current /= 62;
        }
        return sb.reverse().toString();
    }
}
