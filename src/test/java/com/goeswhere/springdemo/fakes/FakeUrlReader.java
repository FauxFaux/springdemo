package com.goeswhere.springdemo.fakes;

import java.util.List;

import com.goeswhere.springdemo.UrlReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/** Returns the ascii representation of the int value of the last character of the URL */
public class FakeUrlReader extends UrlReader {
	private List<String> l = Lists.newArrayList();

	@Override
	public String chomp(String url) {
		l.add(url);
		return String.valueOf((int)url.substring(url.length()-1).charAt(0));
	}

	public List<String> fetched() {
		return ImmutableList.copyOf(l);
	}
}
