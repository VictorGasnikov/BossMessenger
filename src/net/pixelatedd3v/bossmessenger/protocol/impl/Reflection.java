package net.pixelatedd3v.bossmessenger.protocol.impl;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {
	public static String version;

	static {
		final String name = Bukkit.getServer().getClass().getPackage().getName();
		version = String.valueOf(name.substring(name.lastIndexOf(46) + 1)) + ".";
	}

	public static Class<?> getNMSClass(final String className) {
		final String fullName = "net.minecraft.server." + version + className;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public static Class<?> getNMSClassWithException(final String className) throws Exception {
		final String fullName = "net.minecraft.server." + version + className;
		final Class<?> clazz = Class.forName(fullName);
		return clazz;
	}

	public static Class<?> getOBCClass(final String className) {
		final String fullName = "org.bukkit.craftbukkit." + version + className;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public static Object getHandle(final Object obj) {
		try {
			return getMethod(obj.getClass(), "getHandle", (Class<?>[]) new Class[0]).invoke(obj, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Field getField(final Class<?> clazz, final String name) {
		try {
			final Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Method getMethod(final Class<?> clazz, final String name, final Class<?>... args) {
		Method[] methods;
		for (int length = (methods = clazz.getMethods()).length, i = 0; i < length; ++i) {
			final Method m = methods[i];
			if (m.getName().equals(name) && (args.length == 0 || classListEqual(args, m.getParameterTypes()))) {
				m.setAccessible(true);
				return m;
			}
		}
		return null;
	}

	public static Object newInstance(final Class<?> clazz, final Class<?>[] types, Object... params) {
		try {
			return clazz.getConstructor(types).newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean classListEqual(final Class<?>[] l1, final Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length) {
			return false;
		}
		for (int i = 0; i < l1.length; ++i) {
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		}
		return equal;
	}
}
