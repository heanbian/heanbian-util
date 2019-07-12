package com.heanbian.block.core.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class HWorkerId {
	
	private final static long twepoch = 12888349746579L;
	// 机器标识位数
	private final static long workerIdBits = 5L;
	// 数据中心标识位数
	private final static long datacenterIdBits = 5L;
	// 毫秒内自增位数
	private final static long sequenceBits = 12L;
	// 机器ID偏左移12位
	private final static long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private final static long datacenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	// sequence掩码，确保sequnce不会超出上限
	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
	// 上次时间戳
	private static long lastTimestamp = -1L;
	// 序列
	private long sequence = 0L;
	// 服务器ID
	private long workerId = 1L;
	private static long workerMask = -1L ^ (-1L << workerIdBits);
	// 进程编码
	private long processId = 1L;
	private static long processMask = -1L ^ (-1L << datacenterIdBits);
	private static HWorkerId keyWorker = null;

	static {
		keyWorker = new HWorkerId();
	}

	public static synchronized String id() {
		return Long.toString(keyWorker.nextId());
	}

	private HWorkerId() {
		this.workerId = getMachineNumber();
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		this.processId = Long.valueOf(runtimeMXBean.getName().split("@")[0]).longValue();
		this.workerId = workerId & workerMask;
		this.processId = processId & processMask;
	}

	private long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			throw new RuntimeException("timeGen() is error.");
		}
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		return ((timestamp - twepoch) << timestampLeftShift) | (processId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	private long getMachineNumber() {
		StringBuilder sb = new StringBuilder();
		Enumeration<NetworkInterface> e = null;
		try {
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException s) {
		}
		while (e.hasMoreElements()) {
			NetworkInterface ni = e.nextElement();
			sb.append(ni.toString());
		}
		return sb.toString().hashCode();
	}
}
