package com.heanbian.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public final class ID {

	private static final String DEFAULT_ALPHABET_STR = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int DEFAULT_SIZE = 21;
	private static final char[] DEFAULT_ALPHABET = DEFAULT_ALPHABET_STR.toCharArray();
	private static final SecureRandom RANDOM = new SecureRandom();

	private static final int DEFAULT_MASK;
	private static final int DEFAULT_STEP;

	static {
		int alphabetLength = DEFAULT_ALPHABET.length;
		DEFAULT_MASK = calculateMask(alphabetLength);
		DEFAULT_STEP = (int) Math.ceil(1.6 * DEFAULT_MASK * DEFAULT_SIZE / alphabetLength);
	}

	private static final ThreadLocal<byte[]> DEFAULT_BUFFER = ThreadLocal.withInitial(() -> new byte[DEFAULT_STEP]);

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String nanoId() {
		return optimizedDefaultNanoId();
	}

	public static String nanoId(int size) {
		return nanoId(RANDOM, DEFAULT_ALPHABET, size);
	}

	private static String optimizedDefaultNanoId() {
		char[] chars = new char[DEFAULT_SIZE];
		byte[] bytes = DEFAULT_BUFFER.get();
		int mask = DEFAULT_MASK;
		int pos = 0;

		while (pos < DEFAULT_SIZE) {
			RANDOM.nextBytes(bytes);
			for (byte b : bytes) {
				int index = b & mask;
				if (index < DEFAULT_ALPHABET.length) {
					chars[pos++] = DEFAULT_ALPHABET[index];
					if (pos == DEFAULT_SIZE) {
						return new String(chars);
					}
				}
			}
		}
		return new String(chars);
	}

	public static String nanoId(final Random random, final char[] alphabet, final int size) {
		validateParameters(random, alphabet, size);

		final int mask = calculateMask(alphabet.length);
		final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);
		final char[] chars = new char[size];
		final byte[] bytes = new byte[step];
		int pos = 0;

		while (pos < size) {
			random.nextBytes(bytes);
			for (byte b : bytes) {
				int index = b & mask;
				if (index < alphabet.length) {
					chars[pos++] = alphabet[index];
					if (pos == size) {
						return new String(chars);
					}
				}
			}
		}
		return new String(chars);
	}

	private static int calculateMask(int alphabetLength) {
		return (2 << (int) (Math.log(alphabetLength - 1) / Math.log(2))) - 1;
	}

	private static void validateParameters(Random random, char[] alphabet, int size) {
		if (random == null) {
			throw new IllegalArgumentException("Random cannot be null");
		}
		if (alphabet == null) {
			throw new IllegalArgumentException("Alphabet cannot be null");
		}
		if (alphabet.length == 0 || alphabet.length >= 256) {
			throw new IllegalArgumentException("Alphabet length invalid");
		}
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be positive");
		}
	}

}