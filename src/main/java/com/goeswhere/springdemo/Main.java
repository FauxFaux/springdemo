package com.goeswhere.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Main {
	@Autowired Printer printer;
	@Autowired NumberLister lister;
	@Autowired Fetcher fetcher;

	public void run() {
		for (int i : lister)
			printer.print(fetcher.fetch(i));
	}
}
