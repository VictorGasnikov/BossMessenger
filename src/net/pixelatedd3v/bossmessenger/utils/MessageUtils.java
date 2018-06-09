package net.pixelatedd3v.bossmessenger.utils;

import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger.StandardMessenger;
import org.bukkit.entity.Player;

public class MessageUtils {

	public static void update(Player player) {
		for (Messenger messenger : MessengerManager.getPlayerMessengers(player)) {
			messenger.update(player);
		}
	}

	public static void remove(Player player) {
		for (Messenger messenger : MessengerManager.getPlayerMessengers(player)) {
			((StandardMessenger) messenger).removeMessageForPlayer(player);
		}
	}

	public static String getTimer(long time) {
		long l = time;
		int hours = (int) (l / 3600);
		l %= 3600;
		int minutes = (int) (l / 60);
		int seconds = (int) (l % 60);
		String sh = hours > 0 ? ((hours < 10 ? "0" + hours : Integer.toString(hours)) + ":") : "";
		String sm = minutes > 0 || time >= 3600 ? ((minutes < 10 ? "0" + minutes : Integer.toString(minutes)) + ":") : "";
		String ss = seconds < 10 ? "0" + seconds : Integer.toString(seconds);
		return sh + sm + ss;
	}
}
