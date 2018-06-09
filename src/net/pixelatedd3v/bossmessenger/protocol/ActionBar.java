package net.pixelatedd3v.bossmessenger.protocol;

import net.pixelatedd3v.bossmessenger.protocol.impl.actionbarapi.theAction;
import org.bukkit.entity.Player;

public class ActionBar {

	public static void setMessage(Player player, String message) {
		theAction.sendAction(player, message);
	}

	public static void removeBar(Player player) {
		theAction.sendAction(player, "");
	}

	public static void init() {
//		plugin = new ActionBarAPI();
//		plugin.onEnable();
	}
}
