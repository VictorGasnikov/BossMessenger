package net.pixelatedd3v.bossmessenger.protocol;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.protocol.impl.Reflection;
import net.pixelatedd3v.bossmessenger.protocol.impl.babywither.BossBarPlugin;
import net.pixelatedd3v.bossmessenger.protocol.impl.babywither.OldBossBarAPI;
import net.pixelatedd3v.bossmessenger.protocol.impl.bossbar.BossBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarFlag;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class BossBar {
	private static BossBarPlugin PLUGIN;
	public static boolean useOld = !(Reflection.version.startsWith("v1_9") || Reflection.version.startsWith("v1_1"));

	public static void setMessage(final Player player, String text, float percent, Messenger messenger) {
		if (useOld) {
			OldBossBarAPI.setMessage(player, text, percent);
		} else {
			List<BarFlag> flags = new ArrayList<>();
			if (Settings.DEFAULT_BOSSBAR_CREATE_FOG) {
				flags.add(BarFlag.CREATE_FOG);
			}
			if (Settings.DEFAULT_BOSSBAR_DARKEN_SKY) {
				flags.add(BarFlag.DARKEN_SKY);
			}
			//			if (Settings.DEFAULT_BOSSBAR_PLAY_BOSS_MUSIC) {
			//				flags.add(BarFlag.PLAY_BOSS_MUSIC);
			//			}
			org.bukkit.boss.BossBar bar = Bukkit.createBossBar(text, Settings.DEFAULT_BOSSBAR_COLOR, Settings.DEFAULT_BOSSBAR_STYLE, (BarFlag[]) flags.toArray(new BarFlag[0]));
			bar.setProgress(percent / 100);
			BossBarAPI.setMessage(player, messenger.getName(), bar);
		}
	}

	public static void removeBar(Player player, Messenger messenger) {
		if (useOld) {
			OldBossBarAPI.removeBar(player);
		} else {
			BossBarAPI.removeMessage(player, messenger.getName());
		}
	}

	public static void removeAll(Player player) {
		if (useOld) {
			OldBossBarAPI.removeBar(player);
		} else {
			BossBarAPI.removeAllMessages(player);
		}
	}

	public static void init(Plugin plugin) {
		PLUGIN = new BossBarPlugin();
	}

	public static void destroy(Plugin plugin) {
		PLUGIN.onDisable();
	}
}
