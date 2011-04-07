package com.goeswhere.springdemo;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

@Component
public class NumberLister implements Iterable<Integer> {
	private static final List<Integer> LIST = ImmutableList.of(1, 2, 3, 4, 5);

	@Override
	public Iterator<Integer> iterator() {
		return LIST.iterator();
	}
}
