package net.pixelatedd3v.bossmessenger.protocol;

import net.pixelatedd3v.bossmessenger.protocol.impl.titleapi.TitleAPI;
import org.bukkit.entity.Player;

public class Tab {
	
	public static void setMessage(Player player, String header, String footer) {
//		TitleAPI.sendTabTitle(player, header, footer);
		TitleAPI.sendTabTitle(player, header, footer);
	}

	public static void removeBar(Player player) {
//		TitleAPI.sendTabTitle(player, "", "");
		TitleAPI.sendTabTitle(player, "", "");
	}
}
