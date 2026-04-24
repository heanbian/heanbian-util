package com.heanbian.util;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.random.RandomGenerator;

public final class NanoID {

    public static final String DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int DEFAULT_SIZE = 21;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final ThreadLocal<RandomGenerator> FAST_RANDOM =
            ThreadLocal.withInitial(() -> RandomGenerator.of("L128X256MixRandom"));

    private NanoID() {
        throw new AssertionError("No instances");
    }

    public static String id() {
        return id(DEFAULT_SIZE, DEFAULT_ALPHABET, SECURE_RANDOM);
    }

    public static String id(int size) {
        return id(size, DEFAULT_ALPHABET, SECURE_RANDOM);
    }

    public static String id(int size, String alphabet) {
        return id(size, alphabet, SECURE_RANDOM);
    }

    public static String id(int size, String alphabet, RandomGenerator random) {
        Objects.requireNonNull(random, "random must not be null");
        validate(size, alphabet);

        if (alphabet.length() == 1) {
            return String.valueOf(alphabet.charAt(0)).repeat(size);
        }

        int alphabetSize = alphabet.length();
        int mask = (2 << (31 - Integer.numberOfLeadingZeros(alphabetSize - 1))) - 1;
        int step = Math.max(1, (int) Math.ceil(1.6D * mask * size / alphabetSize));

        StringBuilder builder = new StringBuilder(size);
        byte[] bytes = new byte[step];

        while (builder.length() < size) {
            random.nextBytes(bytes);

            for (int i = 0; i < step && builder.length() < size; i++) {
                int alphabetIndex = bytes[i] & mask;
                if (alphabetIndex < alphabetSize) {
                    builder.append(alphabet.charAt(alphabetIndex));
                }
            }
        }

        return builder.toString();
    }

    public static String fastId() {
        return id(DEFAULT_SIZE, DEFAULT_ALPHABET, FAST_RANDOM.get());
    }

    public static String fastId(int size) {
        return id(size, DEFAULT_ALPHABET, FAST_RANDOM.get());
    }

    private static void validate(int size, String alphabet) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than zero");
        }
        if (alphabet == null || alphabet.isEmpty()) {
            throw new IllegalArgumentException("alphabet must not be null or empty");
        }
        if (alphabet.length() >= 256) {
            throw new IllegalArgumentException("alphabet must contain less than 256 characters");
        }

        for (int i = 0; i < alphabet.length(); i++) {
            for (int j = i + 1; j < alphabet.length(); j++) {
                if (alphabet.charAt(i) == alphabet.charAt(j)) {
                    throw new IllegalArgumentException("alphabet must not contain duplicate characters");
                }
            }
        }
    }
}
