package net.pixelatedd3v.bossmessenger.protocol;

import net.pixelatedd3v.bossmessenger.protocol.impl.titleapi.TitleAPI;
import org.bukkit.entity.Player;

public class Title {
	public static void setMessage(Player player, String big, String small, int show) {
		TitleAPI.sendTitle(player, 0, show, 0, big, small);
	}
	
	public static void removeBar(Player player) {
		TitleAPI.sendTitle(player, 0, 0, 0, "", "");
	}
	
	public static void init() {
	}
}
