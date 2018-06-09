package net.pixelatedd3v.bossmessenger.protocol.impl.titles_p_1_12;

import net.pixelatedd3v.bossmessenger.protocol.impl.Reflection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TitleAPI extends JavaPlugin implements Listener {

	private static Class<?> classPacket;
	private static Class<?> classEntityPlayer;
	private static Class<?> classPlayerConnection;
	private static Field fieldPlayerConnection;
	private static Method methodSendPacket;
	private static Class<?> classIChatBaseComponent;
	private static Method methodChatSerializerA;
	private static Class<?> classPacketPlayOutTitle;
	private static Class<?> classPacketPlayOutPlayerListHeaderFooter;
	private static Class<?> classEnumTitleAction;
	private static Constructor<?> constructorPacketPlayOutTitle1;
	private static Constructor<?> constructorPacketPlayOutTitle2;
	private static Object fieldTimes;
	private static Object fieldSubtitle;
	private static Object fieldTitle;
	private static Constructor<?> constructorPacketPlayOutPlayerListHeaderFooter;

	static {
		try {
			classPacket = Reflection.getNMSClass("Packet");
			classEntityPlayer = Reflection.getNMSClass("EntityPlayer");
			classPlayerConnection = Reflection.getNMSClass("PlayerConnection");
			fieldPlayerConnection = classEntityPlayer.getDeclaredField("playerConnection");
			methodSendPacket = classPlayerConnection.getMethod("sendPacket", classPacket);
			classIChatBaseComponent = Reflection.getNMSClass("IChatBaseComponent");
			methodChatSerializerA = Reflection.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);
			classPacketPlayOutTitle = Reflection.getNMSClass("PacketPlayOutTitle");
			classPacketPlayOutPlayerListHeaderFooter = Reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
			constructorPacketPlayOutPlayerListHeaderFooter = classPacketPlayOutPlayerListHeaderFooter.getConstructor(classIChatBaseComponent);
			classPacketPlayOutTitle = Reflection.getNMSClass("PacketPlayOutTitle");
			classEnumTitleAction = Reflection.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
			constructorPacketPlayOutTitle1 = classPacketPlayOutTitle.getConstructor(classEnumTitleAction, classIChatBaseComponent, int.class, int.class, int.class);
			constructorPacketPlayOutTitle2 = classPacketPlayOutTitle.getConstructor(classEnumTitleAction, classIChatBaseComponent);
			fieldTimes = classEnumTitleAction.getField("TIMES").get(null);
			fieldSubtitle = classEnumTitleAction.getField("SUBTITLE").get(null);
			fieldTitle = classEnumTitleAction.getField("TITLE").get(null);

		} catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void sendTitle(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut, String title, String subtitle) {
		try {
			final Object connection = fieldPlayerConnection.get(Reflection.getHandle(player)); // PlayerConnection
			final Object packetPlayOutTimes = constructorPacketPlayOutTitle1.newInstance(fieldTimes, null, fadeIn, stay, fadeOut); // PacketPlayOutTitle
			methodSendPacket.invoke(connection, packetPlayOutTimes);
			if (subtitle != null) {
				final Object titleSub = chatSerializerA(subtitle); // IChatBaseComponent
				final Object packetPlayOutSubTitle = constructorPacketPlayOutTitle2.newInstance(fieldSubtitle, titleSub); // PacketPlayOutTitle
				methodSendPacket.invoke(connection, packetPlayOutSubTitle);
			}
			if (title != null) {
				final Object titleMain = chatSerializerA(title); // IChatBaseComponent
				final Object packetPlayOutTitle = constructorPacketPlayOutTitle2.newInstance(fieldTitle, titleMain); // PacketPlayOutTitle
				methodSendPacket.invoke(connection, packetPlayOutTitle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendTabTitle(final Player player, String header, String footer) {
		if (header == null) {
			header = "";
		}
		if (footer == null) {
			footer = "";
		}
		try {
			final Object connection = fieldPlayerConnection.get(Reflection.getHandle(player)); // PlayerConnection
			final Object tabTitle = chatSerializerA(header); // IChatBaseComponent
			final Object tabFoot = chatSerializerA(footer); // IChatBaseComponent
			final Object headerPacket = constructorPacketPlayOutPlayerListHeaderFooter.newInstance(tabTitle); // PacketPlayOutPlayerListHeaderFooter
			final Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFoot);
			methodSendPacket.invoke(connection, headerPacket);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	private static Object chatSerializerA(String msg) {
		try {
			return methodChatSerializerA.invoke(null, "{\"text\": \"" + msg + "\"}");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
}
