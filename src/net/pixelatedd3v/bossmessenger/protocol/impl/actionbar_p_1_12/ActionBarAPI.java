package net.pixelatedd3v.bossmessenger.protocol.impl.actionbar_p_1_12;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ActionBarAPI implements Listener {
	public static String nmsver;

	private Map<Player, String> messages = new HashMap<>();

	private boolean old;
	private Class<?> c1;
	private Class<?> c2;
	private Class<?> c3;
	private Class<?> c5;
	private Method m2;

	public void removeBar(Player player) {
		messages.remove(player);
		sendActionBar(player, " ");
	}

	public void setMessage(Player player, String message) {
		messages.put(player, message);
		sendActionBar(player, message);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		messages.remove(event.getPlayer());
	}

	public void onEnable() {
		nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = ActionBarAPI.nmsver.substring(ActionBarAPI.nmsver.lastIndexOf(".") + 1);
		Bukkit.getPluginManager().registerEvents(this, BossMessenger.INSTANCE);

		try {
			c1 = Class.forName("org.bukkit.craftbukkit." + ActionBarAPI.nmsver + ".entity.CraftPlayer");
			c2 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".PacketPlayOutChat");
			c3 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".Packet");
			old = ActionBarAPI.nmsver.equalsIgnoreCase("v1_8_R1")/* || !ActionBarAPI.nmsver.startsWith("v1_8_")*/;
			c5 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".IChatBaseComponent");
			m2 = c1.getDeclaredMethod("getHandle", (Class<?>[]) new Class[0]);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		new BukkitRunnable() {

			@Override
			public void run() {
				for (Entry<Player, String> entry : messages.entrySet()) {
					sendActionBar(entry.getKey(), entry.getValue());
				}
			}
		}.runTaskTimer(BossMessenger.INSTANCE, 20, 20);
	}

	private void sendActionBar(final Player player, final String message) {
		try {
			final Object p = c1.cast(player);
			Object ppoc = null;
			if (old) {
				final Class<?> c4 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatSerializer");
				final Method m3 = c4.getDeclaredMethod("a", String.class);
				final Object cbc = c5.cast(m3.invoke(c4, "{\"text\": \"" + message + "\"}"));
				ppoc = c2.getConstructor(c5, Byte.TYPE).newInstance(cbc, (byte) 2);
			} else {
				final Class<?> c4 = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatComponentText");
				final Object o = c4.getConstructor(String.class).newInstance(message);
				ppoc = c2.getConstructor(c5, Byte.TYPE).newInstance(o, (byte) 2);
			}
			final Object h = m2.invoke(p, new Object[0]);
			final Field f1 = h.getClass().getDeclaredField("playerConnection");
			final Object pc = f1.get(h);
			final Method m3 = pc.getClass().getDeclaredMethod("sendPacket", c3);
			m3.invoke(pc, ppoc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
