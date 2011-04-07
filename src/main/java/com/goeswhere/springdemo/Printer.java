package com.goeswhere.springdemo;

import org.springframework.stereotype.Component;

@Component
public class Printer {
	public void print(String string) {
		System.out.println(string);
	}
}
