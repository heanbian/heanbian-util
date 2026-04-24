package com.heanbian.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.random.RandomGenerator;

public final class UUIDv7 implements Comparable<UUIDv7> {

    private static final byte VERSION = 0x07;
    private static final long VARIANT_MASK = 0x8000000000000000L;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final ThreadLocal<RandomGenerator> FAST_RANDOM =
            ThreadLocal.withInitial(() -> RandomGenerator.of("L128X256MixRandom"));

    private final long mostSignificantBits;
    private final long leastSignificantBits;

    private UUIDv7(long mostSignificantBits, long leastSignificantBits) {
        validate(mostSignificantBits, leastSignificantBits);
        this.mostSignificantBits = mostSignificantBits;
        this.leastSignificantBits = leastSignificantBits;
    }

    public static UUIDv7 generate() {
        return generate(SECURE_RANDOM);
    }

    public static UUIDv7 generateFast() {
        return generate(FAST_RANDOM.get());
    }

    public static UUIDv7 generate(RandomGenerator random) {
        Objects.requireNonNull(random, "random must not be null");

        long timestamp = Instant.now().toEpochMilli() & 0x0000FFFFFFFFFFFFL;
        long randA = random.nextLong() & 0x0FFFL;
        long randB = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;

        long msb = (timestamp << 16) | (((long) VERSION) << 12) | randA;
        long lsb = VARIANT_MASK | randB;

        return new UUIDv7(msb, lsb);
    }

    public static UUIDv7 fromString(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        try {
            return fromJavaUuid(UUID.fromString(uuid));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid UUID string: " + uuid, ex);
        }
    }

    public static UUIDv7 fromBytes(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes must not be null");
        if (bytes.length != 16) {
            throw new IllegalArgumentException("Byte array must be 16 bytes long");
        }

        long msb = 0L;
        long lsb = 0L;

        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (bytes[i] & 0xFFL);
        }
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (bytes[i] & 0xFFL);
        }

        return new UUIDv7(msb, lsb);
    }

    public static UUIDv7 fromJavaUuid(UUID uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        return new UUIDv7(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }

    public long getTimestamp() {
        return (mostSignificantBits >>> 16) & 0x0000FFFFFFFFFFFFL;
    }

    public byte getVersion() {
        return (byte) ((mostSignificantBits >>> 12) & 0x0FL);
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[16];

        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (mostSignificantBits >>> (56 - i * 8));
        }
        for (int i = 0; i < 8; i++) {
            bytes[8 + i] = (byte) (leastSignificantBits >>> (56 - i * 8));
        }

        return bytes;
    }

    public UUID toJavaUuid() {
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    public long getMostSignificantBits() {
        return mostSignificantBits;
    }

    public long getLeastSignificantBits() {
        return leastSignificantBits;
    }

    @Override
    public String toString() {
        return toJavaUuid().toString();
    }

    @Override
    public int compareTo(UUIDv7 other) {
        Objects.requireNonNull(other, "other must not be null");

        int msbCompare = Long.compareUnsigned(this.mostSignificantBits, other.mostSignificantBits);
        if (msbCompare != 0) {
            return msbCompare;
        }
        return Long.compareUnsigned(this.leastSignificantBits, other.leastSignificantBits);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UUIDv7 other)) {
            return false;
        }
        return mostSignificantBits == other.mostSignificantBits
                && leastSignificantBits == other.leastSignificantBits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mostSignificantBits, leastSignificantBits);
    }

    private static void validate(long msb, long lsb) {
        if (((msb >>> 12) & 0x0FL) != VERSION) {
            throw new IllegalArgumentException("Not a UUIDv7: version mismatch");
        }
        if (((lsb >>> 62) & 0x03L) != 0x02L) {
            throw new IllegalArgumentException("Not a UUIDv7: variant mismatch");
        }
    }
}
