package com.heanbian.util.test;

import com.heanbian.util.AddrUtils;
import com.heanbian.util.DeUtils;
import com.heanbian.util.EmailUtils;
import com.heanbian.util.MobileUtils;
import com.heanbian.util.NanoID;
import com.heanbian.util.NaturalOrderComparator;
import com.heanbian.util.UUIDv7;

public class UtilSmokeTest {

	public static void main(String[] args) {
		testAddrUtils();
		System.out.println("-----------------------------");
		testEmailUtils();
		System.out.println("-----------------------------");
		testMobileUtils();
		System.out.println("-----------------------------");
		testDeUtils();
		System.out.println("-----------------------------");
		testNanoId();
		System.out.println("-----------------------------");
		testUuidV7();
		System.out.println("-----------------------------");
		testNaturalOrderComparator();
	}

	public static void testAddrUtils() {
		System.out.println(AddrUtils.isValidIPv4("127.0.0.1"));
		System.out.println(AddrUtils.isValidIPv4("256.0.0.1"));
		System.out.println(AddrUtils.isValidIPv6("::1"));
		System.out.println(AddrUtils.isRange("192.168.1.0/24"));
		System.out.println(AddrUtils.isRange("192.168.1.0/abc"));
	}

	public static void testEmailUtils() {
		System.out.println(EmailUtils.isValid("user@example.com"));
		System.out.println(EmailUtils.isValid("a.b+c@sub.example.com"));
		System.out.println(EmailUtils.isValid("bad@@example.com"));
		System.out.println(EmailUtils.isValid("bad@"));
	}

	public static void testMobileUtils() {
		System.out.println(MobileUtils.isValid("13800138000"));
		System.out.println(MobileUtils.isValid("+8613800138000"));
		System.out.println(MobileUtils.isValid("8613800138000"));
		System.out.println(MobileUtils.isValid("12800138000"));
	}

	public static void testDeUtils() {
		System.out.println(DeUtils.maskMobile("13800138000"));
		System.out.println(DeUtils.maskEmail("abcde@example.com"));
	}

	public static void testNanoId() {
		String id = NanoID.id();
		System.out.println(id);
		System.out.println(id.length());
	}

	public static void testUuidV7() {
		UUIDv7 uuid = UUIDv7.generate();
		System.out.println(uuid.getVersion());
		System.out.println(UUIDv7.fromString(uuid.toString()));
		System.out.println(UUIDv7.fromBytes(uuid.toBytes()));
	}

	public static void testNaturalOrderComparator() {
		NaturalOrderComparator<String> comparator = new NaturalOrderComparator<>();
		System.out.println(comparator.compare("file2", "file10") < 0);
		System.out.println(comparator.compare("file01", "file1") > 0);
	}
}
