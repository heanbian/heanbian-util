package com.heanbian.block.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public final class HeanbianId {

	private static final String JJ = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int DEFAULT_SIZE = 21;
	private static final char[] ALPHABET = JJ.toCharArray();
	private static final SecureRandom RANDOM = new SecureRandom();

	public static String uuid() {
		return uuid(true);
	}

	public static String uuid(boolean hyphen) {
		return hyphen ? UUID.randomUUID().toString().replaceAll("-", "") : UUID.randomUUID().toString();
	}

	public static String nanoId() {
		return nanoId(RANDOM, ALPHABET, DEFAULT_SIZE);
	}

	public static String nanoId(final Random random, final char[] alphabet, final int size) {
		if (random == null) {
			throw new IllegalArgumentException("random cannot be null.");
		}

		if (alphabet == null) {
			throw new IllegalArgumentException("alphabet cannot be null.");
		}

		if (alphabet.length == 0 || alphabet.length >= 256) {
			throw new IllegalArgumentException("alphabet must contain between 1 and 255 symbols.");
		}

		if (size <= 0) {
			throw new IllegalArgumentException("size must be greater than zero.");
		}

		final int mask = (2 << (int) Math.floor(Math.log(alphabet.length - 1) / Math.log(2))) - 1;
		final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);

		final StringBuilder idBuilder = new StringBuilder();

		while (true) {
			final byte[] bytes = new byte[step];
			random.nextBytes(bytes);
			for (int i = 0; i < step; i++) {
				final int alphabetIndex = bytes[i] & mask;
				if (alphabetIndex < alphabet.length) {
					idBuilder.append(alphabet[alphabetIndex]);
					if (idBuilder.length() == size) {
						return idBuilder.toString();
					}
				}
			}
		}
	}

	private HeanbianId() {}
}