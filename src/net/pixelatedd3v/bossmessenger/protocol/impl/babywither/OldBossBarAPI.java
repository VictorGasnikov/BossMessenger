package net.pixelatedd3v.bossmessenger.protocol.impl.babywither;

import net.pixelatedd3v.bossmessenger.protocol.impl.Reflection;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class OldBossBarAPI {
	private static final Map<UUID, BossBarHack> barMap;

	static {
		barMap = new ConcurrentHashMap<UUID, BossBarHack>();
	}

	public static void setMessage(final Player player, final String message, final float percentage) {
		UUID uuid = player.getUniqueId();
		BossBarHack bossBar = OldBossBarAPI.barMap.remove(uuid);
		if (bossBar != null) {
			bossBar.destroy();
		} else {

		}
		bossBar = new BossBarHack(player, message, percentage * 3);
		OldBossBarAPI.barMap.put(uuid, bossBar);
		bossBar.setVisible(true);
		// final BossBar bar = BossBarAPI.barMap.get(player.getUniqueId());
		// if (!bar.message.equals(message)) {
		// bar.setMessage(message);
		// }
		// final float newHealth = percentage / 100.0f * bar.getMaxHealth();
		// if (bar.health != newHealth) {
		// bar.setHealth(newHealth);
		// }
		// if (!bar.isVisible()) {
		// bar.setVisible(true);
		// }
	}

	public static boolean hasBar(final Player player) {
		return OldBossBarAPI.barMap.containsKey(player.getUniqueId());
	}

	public static void removeBar(final Player player) {
		final BossBarHack bar = getBossBar(player);
		if (bar == null) {
			return;
		}
		bar.destroy();
		OldBossBarAPI.barMap.remove(player.getUniqueId());
	}

	public static void setHealth(final Player player, final float percentage) {
		final BossBarHack bar = getBossBar(player);
		if (bar == null) {
			return;
		}
		bar.setHealth(percentage);
	}

	@Nullable
	public static BossBarHack getBossBar(final Player player) {
		if (player == null) {
			return null;
		}
		return OldBossBarAPI.barMap.get(player.getUniqueId());
	}

	public static Collection<BossBarHack> getBossBars() {
		final List<BossBarHack> list = new ArrayList<BossBarHack>(OldBossBarAPI.barMap.values());
		return list;
	}

	protected static void sendPacket(final Player p, final Object packet) {
		if (p == null || packet == null) {
			throw new IllegalArgumentException("player and packet cannot be null");
		}
		try {
			final Object handle = Reflection.getHandle(p);
			final Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
			Reflection.getMethod(connection.getClass(), "sendPacket", Reflection.getNMSClass("Packet")).invoke(connection, packet);
		} catch (Exception ex) {
		}
	}
}
