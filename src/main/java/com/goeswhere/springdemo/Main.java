package com.goeswhere.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class Main {
	@Autowired Printer printer;
	@Autowired NumberLister lister;
	@Autowired Fetcher fetcher;

	public static Function<Main, Void> _test;

	public void test() {
		System.out.println("HI THERE");
	}

	public void run() {
		_test.apply(this);

		for (int i : lister)
			printer.print(fetcher.fetch(i));
	}
}
