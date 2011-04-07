package com.goeswhere.springdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EntryPoint {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("/spring/production-context.xml");
	}
}
