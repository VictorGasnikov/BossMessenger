package net.pixelatedd3v.bossmessenger.protocol.impl.babywither.reflection;

import net.pixelatedd3v.bossmessenger.protocol.impl.Reflection;

import java.lang.reflect.Field;

public abstract class NMUClass {
	private static boolean initialized;
	public static Class<?> gnu_trove_map_TIntObjectMap;
	public static Class<?> gnu_trove_map_hash_TIntObjectHashMap;
	public static Class<?> gnu_trove_impl_hash_THash;
	public static Class<?> io_netty_channel_Channel;

	static {
		if (!NMUClass.initialized) {
			String[] fm = {"gnu_trove_map_TIntObjectMap", "gnu_trove_map_hash_TIntObjectHashMap", "gnu_trove_impl_hash_THash", "io_netty_channel_Channel"};
			Field[] declaredFields;
			for (int length = (declaredFields = NMUClass.class.getDeclaredFields()).length, i = 0; i < length; ++i) {
				final Field f = declaredFields[i];
				if (f.getType().equals(Class.class)) {
					try {
						final String name = fm[i - 1].replace("_", ".");
						if (Reflection.version.contains("1_8")) {
							f.set(null, Class.forName(name));
						} else {
							f.set(null, Class.forName("net.minecraft.util." + name));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
