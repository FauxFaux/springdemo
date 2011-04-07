package com.goeswhere.springdemo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.goeswhere.springdemo.fakes.FakeUrlReader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"/spring/test-context.xml",
	"/spring/test-fakeurl.xml"
})
public class FetcherTest {
	@Autowired Fetcher fetcher;
	@Autowired FakeUrlReader reader;

	@Test
	public void testGo() {
		assertEquals("55", fetcher.fetch(7));
		assertEquals("57", fetcher.fetch(9));

		assertEquals(Arrays.asList(
				"http://faux.uwcs.co.uk/demo1/7",
				"http://faux.uwcs.co.uk/demo1/9"),
				reader.fetched());
	}
}
