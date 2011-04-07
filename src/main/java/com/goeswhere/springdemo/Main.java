package com.goeswhere.springdemo;

import org.springframework.stereotype.Component;

@Component
public class Main {
	public void run() {
		for (int i : new int[] { 1, 2, 3, 4, 5 })
			System.out.println(i);
	}
}
