package com.heanbian.util.test;

import com.heanbian.util.AddrUtils;

public class AddrUtilsTest {
	
	// 示例用法
    public static void main(String[] args) {
        String[] testCases = {
                "192.168.1.1",       // 有效IPv4
                "255.255.255.255",    // 有效IPv4
                "0.0.0.0",           // 有效IPv4
                "256.0.0.0",         // 无效IPv4
                "012.34.56.78",      // 无效IPv4（前导零）
                "2001:0db8:85a3:0000:0000:8a2e:0370:7334",  // 有效IPv6
                "2001:db8:85a3::8a2e:370:7334",             // 有效IPv6
                "::1",                                      // 有效IPv6
                "fe80::1%eth0",                             // 有效IPv6（带区域索引）
                "::ffff:192.168.1.1",                       // 有效IPv6（IPv4映射）
                "localhost",                                // 有效主机名
                "example.com",                              // 有效主机名
                "sub.example.com",                          // 有效主机名
                "-example.com",                             // 无效主机名（以连字符开头）
                "example-.com",                             // 无效主机名（以连字符结尾）
                "verylonghostname.abcdefghijklmnopqrstuvwxyz0123456789.abcdefghijklmnopqrstuvwxyz0123456789.abcdefghijklmnopqrstuvwxyz0123456789.com" // 有效（总长度253字符内）
        };

        for (String testCase : testCases) {
            boolean valid = AddrUtils.isValid(testCase);
            System.out.printf("%-60s => %s%n", testCase, valid ? "有效" : "无效");
        }
    }

}
