package com.goeswhere.springdemo;

import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.google.common.io.CharStreams;

@Component
public class UrlReader {
	public String chomp(final String url) {
		try {
			final InputStreamReader isr = new InputStreamReader(new URL(url).openConnection().getInputStream());
			try {
				return CharStreams.toString(isr).trim();
			} finally {
				isr.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
