package com.goeswhere.springdemo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.goeswhere.springdemo.fakes.FakePrinter;
import com.goeswhere.springdemo.fakes.FakeUrlReader;
import com.goeswhere.springutils.MagicLoader;
import com.goeswhere.springutils.WithOverridden;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=MagicLoader.class)
@WithOverridden({FakePrinter.class, FakeUrlReader.class})
public class IntegrationTest {
	@Autowired Main main;
	@Autowired FakePrinter printer;

	@Test
	public void testGo() {
		main.run();
		assertEquals("49,50,51,52,53,", printer.current());
	}
}
