package com.goeswhere.springdemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"/spring/test-context.xml"
})
public class UrlReaderTest {
	@Autowired UrlReader reader;
	private final List<Server> leaked = Lists.newArrayList();

	@Test
	public void testGo() throws Exception {
		assertEquals("/hi", reader.chomp("http://localhost:" + jetty() + "/hi"));
	}

	@Test
	public void testNoCaching() throws Exception {
		final int port = jetty(new Callable<Object>() {
			private int i;
			@Override
			public Object call() throws Exception {
				return ++i;
			}

		});

		assertEquals("/pony1", reader.chomp("http://localhost:" + port + "/pony"));
		assertEquals("/pony2", reader.chomp("http://localhost:" + port + "/pony"));
	}

	@Test
	public void testBoom() throws Exception {
		try {
			reader.chomp("http://localhost:" + jetty() + "/404");
			fail("should'a boom'd");
		} catch (RuntimeException e) {
			assertEquals(FileNotFoundException.class, e.getCause().getClass());
		}
	}

	private static int jetty() throws Exception {
		return jetty(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return "";
			}
		});
	}

	private static int jetty(final Callable<Object> callable) throws Exception {
		final Server s = new Server();
		final SocketConnector conn = new SocketConnector();
		s.addConnector(conn);
		conn.setHost("localhost");
		s.setHandler(new AbstractHandler() {
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request,
					HttpServletResponse response) throws IOException, ServletException {
				final String uri = request.getRequestURI();
				if ("/404".equals(uri)) {
					response.setStatus(404);
					return;
				}

				final ServletOutputStream os = response.getOutputStream();
				try {
					os.println(uri + callable.call());
				} catch (Exception e) {
					throw new IOException(e);
				}

				os.flush();
				os.close();
			}
		});

		s.start();
		return conn.getLocalPort();
	}

	@After
	public void tearDownJetties() throws Exception {
		for (Server s : leaked)
			s.stop();
	}
}
