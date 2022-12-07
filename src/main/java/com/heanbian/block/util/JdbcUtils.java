package com.heanbian.block.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {

	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = threadLocal.get();
			if (conn == null) {
				conn = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:5506/testdb?useUnicode=true&characterEncoding=utf-8", "testname",
						"testpwd");
				threadLocal.set(conn);
			}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void close(Statement psmt, Connection conn) {
		close(null, psmt, conn);
	}

	public static void close(ResultSet rs, Statement psmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (psmt != null) {
			try {
				psmt.close();
			} catch (Exception e) {
			}
		}
		if (conn != null) {
			threadLocal.set(null);
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			threadLocal.set(null);
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public static void beginTx(Connection conn) {
		try {
			if (conn != null) {
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
		}
	}

	public static void commitTx(Connection conn) {
		try {
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
		}
	}

	public static void rollbackTx(Connection conn) {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
		}
	}

}
