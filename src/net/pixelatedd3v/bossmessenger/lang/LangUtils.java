package net.pixelatedd3v.bossmessenger.lang;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class LangUtils {

	public static final String REGULAR_PREFIX = ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "BossMessenger" + ChatColor.DARK_GREEN + "] " + ChatColor.YELLOW;
	public static final String ERROR_PREFIX = ChatColor.DARK_RED + "[" + ChatColor.RED + "BossMessenger" + ChatColor.DARK_RED + "] " + ChatColor.GOLD;

	public static void sendError(CommandSender player, String msg) {
		player.sendMessage(ERROR_PREFIX + msg);
	}

	public static void sendMessage(CommandSender player, String msg) {
		player.sendMessage(REGULAR_PREFIX + msg);
	}

	public static void listLists(CommandSender sender) {
		sender.sendMessage(ChatColor.DARK_GREEN + "===" + ChatColor.GREEN + " Lists " + ChatColor.DARK_GREEN + "===");

		List<String> listnames = new ArrayList<String>(Settings.MESSAGES.keySet());
		int lists = Settings.MESSAGES.size();
		for (int count = 0; count < lists; count++) {
			sender.sendMessage(ChatColor.YELLOW + Integer.toString(count + 1) + ". " + ChatColor.RESET + listnames.get(count));
		}
	}

	public static void listMessages(CommandSender sender, MessageList list) {
		sender.sendMessage(ChatColor.DARK_GREEN + "=== " + ChatColor.GREEN + "Messages in " + list.getName() + ChatColor.DARK_GREEN + " ===");
		int size = list.size();

		for (int count = 0; count < size; count++) {
			TemplateMessage message = list.getMessage(count);
			sender.sendMessage(ChatColor.YELLOW + Integer.toString(count + 1) + ". " + ChatColor.RESET + message.getText());
		}
	}

	public static void listTasks(CommandSender sender) {
		sender.sendMessage(ChatColor.DARK_GREEN + "===" + ChatColor.GREEN + " Tasks " + ChatColor.DARK_GREEN + "===");

		List<String> tasknames = new ArrayList<String>(Settings.TASKS.keySet());
		int tasks = Settings.TASKS.size();
		for (int count = 0; count < tasks; count++) {
			sender.sendMessage(ChatColor.YELLOW + Integer.toString(count + 1) + ". " + ChatColor.RESET + tasknames.get(count));
		}
	}

	public static void showTaskInfo(CommandSender sender, Task task) {
		sender.sendMessage(ChatColor.DARK_GREEN + "=== " + ChatColor.GREEN + "Messages in " + task.getName() + ChatColor.DARK_GREEN + " ===");
		sender.sendMessage(ChatColor.YELLOW + "Message: " + ChatColor.RESET + task.getMessage());
		sender.sendMessage(ChatColor.YELLOW + "Commands: ");

		List<String> cmds = task.getCommands();
		for (int count = 0; count < cmds.size(); count++) {
			sender.sendMessage(ChatColor.YELLOW + Integer.toString(count + 1) + ". " + ChatColor.RESET + cmds.get(count));
		}
	}

	public static void sendUpdateNotification(CommandSender sender) {
		sender.sendMessage(" ");
		sender.sendMessage(ChatColor.DARK_AQUA + "===" + ChatColor.AQUA + " An update for BossMessenger is available " + ChatColor.DARK_AQUA + "===");
		sender.sendMessage(ChatColor.YELLOW + "New Version: " + ChatColor.GREEN + Settings.UPDATE_VERSION);
		sender.sendMessage(ChatColor.YELLOW + "Current Version: " + ChatColor.RED + BossMessenger.VERSION);
		sender.sendMessage(ChatColor.YELLOW + "Update Link: " + ChatColor.BLUE + ChatColor.UNDERLINE + BossMessenger.LINK);
		sender.sendMessage(" ");
	}

	public static void sendInfo(CommandSender sender) {
		sender.sendMessage(" ");
		sender.sendMessage(ChatColor.DARK_AQUA + "===" + ChatColor.AQUA + " BossMessenger by " + ChatColor.GREEN + "Victor2748 " + ChatColor.DARK_AQUA + "===");
		sender.sendMessage(ChatColor.YELLOW + "Version: " + ChatColor.GREEN + BossMessenger.INSTANCE.getDescription().getVersion());
		sender.sendMessage(ChatColor.YELLOW + "Author: " + "Victor2748");
		sender.sendMessage(ChatColor.YELLOW + "Spigot Link: " + ChatColor.GREEN + BossMessenger.LINK);
		sender.sendMessage(" ");
	}

	public static void noPerm(CommandSender sender) {
		sendError(sender, Lang.NO_PERMISSION);
	}
}
