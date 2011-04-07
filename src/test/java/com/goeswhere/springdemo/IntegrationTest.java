package com.goeswhere.springdemo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.goeswhere.springdemo.fakes.FakePrinter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"/spring/test-context.xml",
	"/spring/test-fakeurl.xml",
	"/spring/context-autorun.xml"
})
public class IntegrationTest {
	@Autowired Main main;
	@Autowired FakePrinter printer;

	@Test
	public void testGo() {
		assertEquals("49,50,51,52,53,", printer.current());
	}
}
