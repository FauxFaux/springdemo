package com.goeswhere.springutils;
import java.beans.Introspector;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextLoader;
import org.springframework.util.ClassUtils;

import com.goeswhere.springdemo.EntryPoint;
import com.google.common.collect.Lists;

public class MagicLoader implements ContextLoader {

	private static final String PREFIX = "magic-loader:/";

	/** This is so fail. */
	@Override
	public String[] processLocations(Class<?> clazz, String... locations) {
		final List<String> ret = Lists.newArrayList();
		final WithOverridden overriden = clazz.getAnnotation(WithOverridden.class);
		if (null == overriden)
			return new String[0];

		for (Class<?> c : overriden.value())
			ret.add(PREFIX + c.getName());
		return ret.toArray(new String[0]);
	}

	@Override
	public ApplicationContext loadContext(String... locations) throws Exception {
		final Class<?>[] cls = new Class<?>[locations.length];
		int i = 0;
		for (String loc : locations) {
			if (!loc.startsWith(PREFIX))
				throw new IllegalArgumentException(loc + " isn't in the correct namespace");
			cls[i++] = Class.forName(loc.substring(PREFIX.length()));
		}

		final AnnotationConfigApplicationContext ctx = EntryPoint.makeScannedContext();
		ctx.setBeanNameGenerator(new AnnotationBeanNameGenerator() {
			@Override
			protected String buildDefaultBeanName(BeanDefinition definition) {
				String shortName = ClassUtils.getShortName(definition.getBeanClassName());
				if (shortName.startsWith("Fake"))
					shortName = shortName.substring("Fake".length());
				return Introspector.decapitalize(shortName);
			}
		});
		ctx.register(cls);
		ctx.refresh();
		return ctx;
	}

}
