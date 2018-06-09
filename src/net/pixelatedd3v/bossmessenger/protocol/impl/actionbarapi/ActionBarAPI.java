package net.pixelatedd3v.bossmessenger.protocol.impl.actionbarapi;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.pixelatedd3v.bossmessenger.BossMessenger;
import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

import java.lang.reflect.*;

import org.bukkit.scheduler.*;

import java.util.*;

public class ActionBarAPI implements Listener {
	public static Plugin plugin;
	public static boolean works;
	public static String nmsver;
	private static boolean useOldMethods;
	
	public void onEnable() {
		ActionBarAPI.plugin = BossMessenger.INSTANCE;
		ActionBarAPI.nmsver = Bukkit.getServer().getClass().getPackage().getName();
		ActionBarAPI.nmsver = ActionBarAPI.nmsver.substring(ActionBarAPI.nmsver.lastIndexOf(".") + 1);
		if (ActionBarAPI.nmsver.equalsIgnoreCase("v1_8_R1") || ActionBarAPI.nmsver.startsWith("v1_7_")) {
			ActionBarAPI.useOldMethods = true;
		}
	}
	
	public static void sendActionBar(final Player player, final String message) {
		if (!player.isOnline()) {
			return;
		}
		final ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
		Bukkit.getPluginManager().callEvent((Event) actionBarMessageEvent);
		if (actionBarMessageEvent.isCancelled()) {
			return;
		}
		if (ActionBarAPI.nmsver.startsWith("v1_12_")) {
			sendActionBarPost112(player, message);
		} else {
			sendActionBarPre112(player, message);
		}
	}
	
	private static void sendActionBarPost112(final Player player, final String message) {
		if (!player.isOnline()) {
			return;
		}
		try {
			final Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + ActionBarAPI.nmsver + ".entity.CraftPlayer");
			final Object craftPlayer = craftPlayerClass.cast(player);
			Class<?> c4 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".PacketPlayOutChat");
			final Class<?> c2 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".Packet");
			final Class<?> c3 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatComponentText");
			c4 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".IChatBaseComponent");
			final Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatMessageType");
			final Object[] chatMessageTypes = (Object[]) chatMessageTypeClass.getEnumConstants();
			Object chatMessageType = null;
			for (final Object obj : chatMessageTypes) {
				if (obj.toString().equals("GAME_INFO")) {
					chatMessageType = obj;
				}
			}
			final Object o = c3.getConstructor(String.class).newInstance(message);
			final Object ppoc = c4.getConstructor(c4, chatMessageTypeClass).newInstance(o, chatMessageType);
			final Method m1 = craftPlayerClass.getDeclaredMethod("getHandle", (Class<?>[]) new Class[0]);
			final Object h = m1.invoke(craftPlayer, new Object[0]);
			final Field f1 = h.getClass().getDeclaredField("playerConnection");
			final Object pc = f1.get(h);
			final Method m2 = pc.getClass().getDeclaredMethod("sendPacket", c2);
			m2.invoke(pc, ppoc);
		} catch (Exception ex) {
			ex.printStackTrace();
			ActionBarAPI.works = false;
		}
	}
	
	private static void sendActionBarPre112(final Player player, final String message) {
		if (!player.isOnline()) {
			return;
		}
		try {
			final Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + ActionBarAPI.nmsver + ".entity.CraftPlayer");
			final Object craftPlayer = craftPlayerClass.cast(player);
			Class<?> c4 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".PacketPlayOutChat");
			final Class<?> c2 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".Packet");
			Object ppoc;
			if (ActionBarAPI.useOldMethods) {
				final Class<?> c3 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatSerializer");
				c4 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".IChatBaseComponent");
				final Method m3 = c3.getDeclaredMethod("a", String.class);
				final Object cbc = c4.cast(m3.invoke(c3, "{\"text\": \"" + message + "\"}"));
				ppoc = c4.getConstructor(c4, Byte.TYPE).newInstance(cbc, (byte) 2);
			} else {
				final Class<?> c3 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatComponentText");
				c4 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".IChatBaseComponent");
				final Object o = c3.getConstructor(String.class).newInstance(message);
				ppoc = c4.getConstructor(c4, Byte.TYPE).newInstance(o, (byte) 2);
			}
			final Method m2 = craftPlayerClass.getDeclaredMethod("getHandle", (Class<?>[]) new Class[0]);
			final Object h = m2.invoke(craftPlayer, new Object[0]);
			final Field f1 = h.getClass().getDeclaredField("playerConnection");
			final Object pc = f1.get(h);
			final Method m3 = pc.getClass().getDeclaredMethod("sendPacket", c2);
			m3.invoke(pc, ppoc);
		} catch (Exception ex) {
			ex.printStackTrace();
			ActionBarAPI.works = false;
		}
	}
	
	public static void sendActionBar(final Player player, final String message, int duration) {
		sendActionBar(player, message);
		if (duration >= 0) {
			new BukkitRunnable() {
				public void run() {
					ActionBarAPI.sendActionBar(player, "");
				}
			}.runTaskLater(ActionBarAPI.plugin, (long) (duration + 1));
		}
		while (duration > 40) {
			duration -= 40;
			new BukkitRunnable() {
				public void run() {
					ActionBarAPI.sendActionBar(player, message);
				}
			}.runTaskLater(ActionBarAPI.plugin, (long) duration);
		}
	}
	
	public static void sendActionBarToAllPlayers(final String message) {
		sendActionBarToAllPlayers(message, -1);
	}
	
	public static void sendActionBarToAllPlayers(final String message, final int duration) {
		for (final Player p : Bukkit.getOnlinePlayers()) {
			sendActionBar(p, message, duration);
		}
	}
	
	static {
		ActionBarAPI.works = true;
		ActionBarAPI.useOldMethods = false;
	}
}
