package net.pixelatedd3v.bossmessenger.protocol.impl.babywither.reflection;

import net.pixelatedd3v.bossmessenger.protocol.impl.Reflection;

import java.lang.reflect.Field;

public abstract class NMSClass {
	private static boolean initialized;
	public static Class<?> Entity;
	public static Class<?> EntityLiving;
	public static Class<?> EntityInsentient;
	public static Class<?> EntityAgeable;
	public static Class<?> EntityHorse;
	public static Class<?> EntityArmorStand;
	public static Class<?> EntityWither;
	public static Class<?> EntityWitherSkull;
	public static Class<?> EntitySlime;
	public static Class<?> World;
	public static Class<?> PacketPlayOutSpawnEntityLiving;
	public static Class<?> PacketPlayOutSpawnEntity;
	public static Class<?> PacketPlayOutEntityDestroy;
	public static Class<?> PacketPlayOutAttachEntity;
	public static Class<?> PacketPlayOutEntityTeleport;
	public static Class<?> PacketPlayOutEntityMetadata;
	public static Class<?> DataWatcher;
	public static Class<?> WatchableObject;
	public static Class<?> ItemStack;
	public static Class<?> ChunkCoordinates;
	public static Class<?> BlockPosition;
	public static Class<?> Vector3f;
	public static Class<?> EnumEntityUseAction;

	static {
		if (!NMSClass.initialized) {
			String[] fn = {"Entity", "EntityLiving", "EntityInsentient", "EntityAgeable", "EntityHorse", "EntityArmorStand", "EntityWither", "EntityWitherSkull", "EntitySlime", "World", "PacketPlayOutSpawnEntityLiving", "PacketPlayOutSpawnEntity", "PacketPlayOutEntityDestroy", "PacketPlayOutAttachEntity", "PacketPlayOutEntityTeleport", "PacketPlayOutEntityMetadata", "DataWatcher", "WatchableObject", "ItemStack", "ChunkCoordinates", "BlockPosition", "Vector3f", "EnumEntityUseAction"};
			Field[] declaredFields = NMSClass.class.getDeclaredFields();
			for (int length = (declaredFields).length, i = 0; i < length; ++i) {
				final Field f = declaredFields[i];
				if (f.getType().equals(Class.class)) {
					try {
						f.set(null, Reflection.getNMSClassWithException(fn[i - 1]));
					} catch (Exception e2) {
						if (fn[i - 1].equals("WatchableObject")) {
							try {
								f.set(null, Reflection.getNMSClassWithException("DataWatcher$WatchableObject"));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
