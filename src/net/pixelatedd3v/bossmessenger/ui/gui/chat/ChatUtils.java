package net.pixelatedd3v.bossmessenger.ui.gui.chat;

import org.bukkit.command.CommandSender;

public class ChatUtils {

	public static final String CANCEL = "§aType §e/bm cancel§a to cancel";

	public static void chatUIMessage(CommandSender sender, String... message) {
		for (int i = 0; i < 100; i++) {
			sender.sendMessage(" ");
		}
		sender.sendMessage("§e============");
		sender.sendMessage(CANCEL);
		for (String msg : message) {
			sender.sendMessage("§a" + msg);
		}

		sender.sendMessage("§e============");
	}
}
