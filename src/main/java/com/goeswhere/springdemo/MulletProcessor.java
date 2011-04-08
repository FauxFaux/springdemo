package com.goeswhere.springdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class MulletProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		try {
			final Class<? extends Object> bc = bean.getClass();
			for (Field f : bc.getDeclaredFields())
				if (Modifier.isStatic(f.getModifiers()) && f.getName().startsWith("_")
						&& f.getType().isAssignableFrom(Function.class)) {
					ParameterizedType t = (ParameterizedType) f.getGenericType();
					final Type input = t.getActualTypeArguments()[0];
					final Type output = t.getActualTypeArguments()[1];
					if (!bc.equals(input))
						continue;

					final Method meth = bc.getDeclaredMethod(f.getName().substring(1), new Class[0]);
					if (!meth.getReturnType().isAssignableFrom((Class<?>) output)
							&& !(meth.getReturnType().equals(void.class) && output.equals(Void.class)))
						throw new RuntimeException(meth + " should return " + output);

					f.set(null, new Function() {
						@Override
						public Object apply(Object input) {
							try {
								return meth.invoke(input);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
					});
				}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
