package net.pixelatedd3v.bossmessenger.protocol.impl.bossbar;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Created by Me on 2016-03-02.
 */
public class BossBarAPI {
	private static Map<UUID, Map<String, BossBar>> bossbars = new HashMap<>();

	public static void setMessage(Player player, String list, BossBar bar) {
		removeMessage(player, list);
		registerBar(player, list, bar);
		bar.addPlayer(player);
	}

	public static void removeMessage(Player player, String list) {
		Map<String, BossBar> bars = bossbars.get(player.getUniqueId());
		if (bars != null) {
			BossBar bar = bars.remove(list);
			if (bar != null) {
				bar.removeAll();
			}
		}
	}

	public static void removeAllMessages(Player player) {
		Map<String, BossBar> bars = bossbars.get(player.getUniqueId());
		if (bars != null) {
			for (BossBar bar : bars.values()) {
				bar.removeAll();
			}
			bars.clear();
		}
	}

	private static void registerBar(Player player, String list, BossBar bar) {
		UUID uuid = player.getUniqueId();
		Map<String, BossBar> bars = bossbars.get(uuid);
		if (bars == null) {
			bossbars.put(uuid, bars = new HashMap<>());
		}
		bars.put(list, bar);
	}
}
