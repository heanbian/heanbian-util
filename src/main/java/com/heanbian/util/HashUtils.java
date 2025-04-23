package com.heanbian.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class HashUtils {

	public static String sha3(String input) {
		return sha3(input, SHA3_TYPE.SHA3_224);
	}

	public static String sha3(String input, SHA3_TYPE sha3Type) {
		return sha3(input, sha3Type.getAlg());
	}

	private static String sha3(String input, String alg) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(alg);
			byte[] buf = md.digest(input.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(buf);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(alg, e);
		}
	}

	static enum SHA3_TYPE {

		SHA3_224("SHA3-224"), //
		SHA3_256("SHA3-256"), //
		SHA3_384("SHA3-384"), //
		SHA3_512("SHA3-512");

		private final String alg;

		SHA3_TYPE() {
			this("SHA3-224");
		}

		SHA3_TYPE(String alg) {
			this.alg = alg;
		}

		public String getAlg() {
			return alg;
		}

	}

}
