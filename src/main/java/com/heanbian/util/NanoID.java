package com.heanbian.util;

import java.security.SecureRandom;
import java.util.random.RandomGenerator;

public final class NanoID {

	public static final String DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int DEFAULT_SIZE = 21;
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	private static final RandomGenerator RANDOM_GENERATOR = RandomGenerator.of("L128X256MixRandom");

	private NanoID() {
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
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than zero.");
		}

		if (alphabet == null || alphabet.isEmpty()) {
			throw new IllegalArgumentException("Alphabet must not be null or empty.");
		}

		if (alphabet.length() >= 256) {
			throw new IllegalArgumentException("Alphabet must contain less than 256 characters.");
		}

		final int mask = (2 << (int) Math.floor(Math.log(alphabet.length() - 1) / Math.log(2))) - 1;
		final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length());

		final StringBuilder idBuilder = new StringBuilder(size);
		final byte[] bytes = new byte[step];

		while (true) {
			random.nextBytes(bytes);

			for (int i = 0; i < step; i++) {
				final int alphabetIndex = bytes[i] & mask;

				if (alphabetIndex < alphabet.length()) {
					idBuilder.append(alphabet.charAt(alphabetIndex));
					if (idBuilder.length() == size) {
						return idBuilder.toString();
					}
				}
			}
		}
	}

	public static String fastId() {
		return id(DEFAULT_SIZE);
	}

	public static String fastId(int size) {
		return id(size, DEFAULT_ALPHABET, RANDOM_GENERATOR);
	}

}