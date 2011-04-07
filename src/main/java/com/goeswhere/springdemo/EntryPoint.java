package com.goeswhere.springdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EntryPoint {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("/spring/context-production.xml")
			.getBean(Main.class).run();
	}
}
