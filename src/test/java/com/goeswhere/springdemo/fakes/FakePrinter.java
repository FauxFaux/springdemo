package com.goeswhere.springdemo.fakes;

import com.goeswhere.springdemo.Printer;

/** Buffers the things it'd print for access */
public class FakePrinter extends Printer {
	private final StringBuilder sb = new StringBuilder();
	@Override
	public void print(String string) {
		sb.append(string).append(',');
	}

	public String current() {
		return sb.toString();
	}
}
