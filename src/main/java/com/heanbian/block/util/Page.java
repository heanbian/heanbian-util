package com.heanbian.block.util;

import java.util.List;

public class Page<I> {

	private int pageNumber = 1;

	private int pageSize = 10;

	private long total;

	private List<I> list;

	public Page() {
	}

	public Page(int pageNumber, int pageSize, long total, List<I> list) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.total = total;
		this.list = list;
	}

	public static <I> Page<I> of(int pageNumber, int pageSize, long total, List<I> list) {
		return new Page<>(pageNumber, pageSize, total, list);
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public Page<I> setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Page<I> setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public long getTotal() {
		return total;
	}

	public Page<I> setTotal(long total) {
		this.total = total;
		return this;
	}

	public long getTotalPage() {
		return (total + pageSize - 1) / pageSize;
	}

	public List<I> getList() {
		return list;
	}

	public Page<I> setList(List<I> list) {
		this.list = list;
		return this;
	}

}