package com.goeswhere.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Fetcher {

	@Autowired UrlReader reader;

	public String fetch(int i) {
		return reader.chomp("http://faux.uwcs.co.uk/demo1/" + i);
	}
}
