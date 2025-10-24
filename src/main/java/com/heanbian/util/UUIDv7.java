package com.heanbian.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Objects;
import java.util.random.RandomGenerator;

public final class UUIDv7 implements Comparable<UUIDv7> {

	private static final byte VERSION = 0x07;
	private static final byte VARIANT = (byte) 0x80; 

	private final long mostSignificantBits;
	private final long leastSignificantBits;

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	private static final RandomGenerator FAST_RANDOM = RandomGenerator.of("L128X256MixRandom");
	private static final HexFormat HEX_FORMAT = HexFormat.of().withUpperCase();

	private UUIDv7(long mostSignificantBits, long leastSignificantBits) {
		this.mostSignificantBits = mostSignificantBits;
		this.leastSignificantBits = leastSignificantBits;
	}

	public static UUIDv7 generate() {
		return generate(SECURE_RANDOM);
	}

	public static UUIDv7 generate(RandomGenerator random) {
		Objects.requireNonNull(random, "Random generator must not be null");

		long timestamp = getCurrentTimestamp();

		long msb = ((timestamp & 0x0FFFFFFFFFFFL) << 16) //
				| ((VERSION & 0x0FL) << 12) //
				| (random.nextLong() & 0x0FFFL); //

		long lsb = (VARIANT & 0xFFL) << 56 //
				| (random.nextLong() & 0x3FFFFFFFFFFFFFFFL); //

		return new UUIDv7(msb, lsb);
	}

	public static UUIDv7 generateFast() {
		return generate(FAST_RANDOM);
	}

	public static UUIDv7 fromString(String uuid) {
		Objects.requireNonNull(uuid, "UUID string must not be null");

		if (uuid.length() != 36) {
			throw new IllegalArgumentException("Invalid UUID string length: " + uuid.length());
		}

		String cleanUuid = uuid.replace("-", "");
		if (cleanUuid.length() != 32) {
			throw new IllegalArgumentException("Invalid UUID format: " + uuid);
		}

		try {
			long msb = Long.parseUnsignedLong(cleanUuid.substring(0, 16), 16);
			long lsb = Long.parseUnsignedLong(cleanUuid.substring(16, 32), 16);

			if (((msb >>> 12) & 0x0FL) != VERSION) {
				throw new IllegalArgumentException("Not a UUIDv7: version mismatch");
			}

			if (((lsb >>> 62) & 0x03L) != 0x02L) {
				throw new IllegalArgumentException("Not a UUIDv7: variant mismatch");
			}

			return new UUIDv7(msb, lsb);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid UUID string: " + uuid, e);
		}
	}

	public static UUIDv7 fromBytes(byte[] bytes) {
		Objects.requireNonNull(bytes, "Byte array must not be null");

		if (bytes.length != 16) {
			throw new IllegalArgumentException("Byte array must be 16 bytes long");
		}

		long msb = 0;
		long lsb = 0;

		for (int i = 0; i < 8; i++) {
			msb = (msb << 8) | (bytes[i] & 0xff);
		}
		for (int i = 8; i < 16; i++) {
			lsb = (lsb << 8) | (bytes[i] & 0xff);
		}

		return new UUIDv7(msb, lsb);
	}

	public long getTimestamp() {
		return (mostSignificantBits >>> 16) & 0x0FFFFFFFFFFFL;
	}

	public byte getVersion() {
		return (byte) ((mostSignificantBits >>> 12) & 0x0FL);
	}

	@Override
	public String toString() {
		char[] chars = new char[36];
		String hex = HEX_FORMAT.formatHex(toBytes());

		// 插入连字符
		chars[0] = hex.charAt(0);
		chars[1] = hex.charAt(1);
		chars[2] = hex.charAt(2);
		chars[3] = hex.charAt(3);
		chars[4] = hex.charAt(4);
		chars[5] = hex.charAt(5);
		chars[6] = hex.charAt(6);
		chars[7] = hex.charAt(7);
		chars[8] = '-';
		chars[9] = hex.charAt(8);
		chars[10] = hex.charAt(9);
		chars[11] = hex.charAt(10);
		chars[12] = hex.charAt(11);
		chars[13] = '-';
		chars[14] = hex.charAt(12);
		chars[15] = hex.charAt(13);
		chars[16] = hex.charAt(14);
		chars[17] = hex.charAt(15);
		chars[18] = '-';
		chars[19] = hex.charAt(16);
		chars[20] = hex.charAt(17);
		chars[21] = hex.charAt(18);
		chars[22] = hex.charAt(19);
		chars[23] = '-';
		chars[24] = hex.charAt(20);
		chars[25] = hex.charAt(21);
		chars[26] = hex.charAt(22);
		chars[27] = hex.charAt(23);
		chars[28] = hex.charAt(24);
		chars[29] = hex.charAt(25);
		chars[30] = hex.charAt(26);
		chars[31] = hex.charAt(27);
		chars[32] = hex.charAt(28);
		chars[33] = hex.charAt(29);
		chars[34] = hex.charAt(30);
		chars[35] = hex.charAt(31);

		return new String(chars);
	}

	public byte[] toBytes() {
		byte[] bytes = new byte[16];

		for (int i = 0; i < 8; i++) {
			bytes[i] = (byte) (mostSignificantBits >>> (56 - (i * 8)));
		}
		for (int i = 8; i < 16; i++) {
			bytes[i] = (byte) (leastSignificantBits >>> (56 - ((i - 8) * 8)));
		}

		return bytes;
	}

	public java.util.UUID toJavaUuid() {
		return new java.util.UUID(mostSignificantBits, leastSignificantBits);
	}

	public static UUIDv7 fromJavaUuid(java.util.UUID uuid) {
		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();

		if (((msb >>> 12) & 0x0FL) != VERSION || ((lsb >>> 62) & 0x03L) != 0x02L) {
			throw new IllegalArgumentException("Not a valid UUIDv7");
		}

		return new UUIDv7(msb, lsb);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UUIDv7 uuidv7 = (UUIDv7) obj;
		return mostSignificantBits == uuidv7.mostSignificantBits && leastSignificantBits == uuidv7.leastSignificantBits;
	}

	@Override
	public int hashCode() {
		return Objects.hash(mostSignificantBits, leastSignificantBits);
	}

	@Override
	public int compareTo(UUIDv7 other) {
		int msbCompare = Long.compareUnsigned(this.mostSignificantBits, other.mostSignificantBits);
		if (msbCompare != 0)
			return msbCompare;
		return Long.compareUnsigned(this.leastSignificantBits, other.leastSignificantBits);
	}

	private static long getCurrentTimestamp() {
		return Instant.now().toEpochMilli() & 0x0FFFFFFFFFFFL;
	}

	public long getMostSignificantBits() {
		return mostSignificantBits;
	}

	public long getLeastSignificantBits() {
		return leastSignificantBits;
	}

}
