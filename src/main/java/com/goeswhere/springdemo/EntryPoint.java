package com.goeswhere.springdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EntryPoint {
	public static void main(String[] args) {
		getMainClass().run();
	}

	private static Main getMainClass() {
		final AnnotationConfigApplicationContext ctx = makeScannedContext();
		ctx.refresh();
		return ctx.getBean(Main.class);
	}

	public static AnnotationConfigApplicationContext makeScannedContext() {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan(EntryPoint.class.getPackage().getName());
		return ctx;
	}
}
