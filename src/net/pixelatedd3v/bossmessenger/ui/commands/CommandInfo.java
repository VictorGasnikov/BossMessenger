package net.pixelatedd3v.bossmessenger.ui.commands;

import net.pixelatedd3v.bossmessenger.GroupManager;
import net.pixelatedd3v.bossmessenger.lang.LangUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum CommandInfo {
	CREATELIST("createlist", true, "createlist <listname>", "Create a new empty list."),
	DELETELIST("deletelist", true, "deletelist <listname>", "Delete the specified list."),
	ADD("add", true, "add <listname> <percent> <show> <interval> <message>", "Add a message to the specified list."),
	DELETE("delete", true, "delete <listname> <message #>", "Deletes a message from the specified list."),
	LIST("list", true, "list [listname]", "Displays all messages from the specified list in chat. If the list is not specified, displays the names of all existing lists."),
	BROADCAST("broadcast", true, "broadcast <sec> <message>", "Broadcasts a message for specified time."),
	QB("qb", true, "qb <message>", "Broadcasts a message for the default time"),
	GB("gb", true, "gb <listname> <sec> <message>", "Broadcasts a message only for the specified list."),
	CREATETASK("createtask", true, "createtask <taskname> <message>", "Creates a new task with the specified message."),
	DELETETASK("deletetask", true, "deletetask <taskname>", "Deletes the specified task."),
	ADDTASKCMD("addtaskcmd", true, "addtaskcmd <taskname> <command>", "Adds a command to the task. Type the command without slash (/)"),
	TASKLIST("tasklist", true, "tasklist", "Lists all the available tasks."),
	TASKINFO("taskinfo", true, "taskinfo <taskname>", "Displays the information about the specified task."),
	SCHEDULE("schedule", true, "schedule <taskname> <time>", "Schedules the specified task for the specified time (in seconds)."),
	QS("qs", true, "qs <taskname>", "Schedules the specified task for the default time."),
	GS("gs", true, "gs <listname> <taskname> <time>", "Schedules the specified task for the specified time, displaying the message only for the specified list."),
	DISABLEWORLD("disableworld", true, "disableworld <world>", "Disables BossMessenger in the specified world."),
	ENABLEWORLD("enableworld", true, "enableworld <world>", "Re-enables BossMessenger in the specified world."),
	ADMINGUI("admingui", true, "admingui", "Opens up an admin GUI.", "§a"),
	//	PLAYERGUI("playergui", true, "playergui", "Opens up a player GUI.", "§a"),
	GUI("gui", true, "gui", "Opens up a player GUI.", "§a"),
	TOGGLE("toggle", true, "toggle", "Toggles message visibility for yourself."),
	INFO("info", false, "info", "Displays information about this plugin."),
	RELOAD("reload", true, "reload", "Reloads the plugin."),
	PM("pm", true, "pm <player> <message>", "PM a player."),
	HELP("help", false, "help", "List all available commands.");

	private String command;
	private boolean permission;
	private String usage;
	private String description;
	private String prefix;

	private CommandInfo(String command, boolean permission, String usage, String description) {
		this(command, permission, usage, description, "§e");
	}

	private CommandInfo(String command, boolean permission, String usage, String description, String prefix) {
		this.command = command;
		this.permission = permission;
		this.usage = usage;
		this.description = description;
		this.prefix = prefix;
	}

	public String getCommand() {
		return command;
	}

	public boolean needsPermission() {
		return permission;
	}

	public String getUsage() {
		return usage;
	}

	public String getDescription() {
		return description;
	}

	public String getPrefix() {
		return prefix;
	}

	public static void listCommands(CommandSender sender, String label) {
		sender.sendMessage(" ");
		sender.sendMessage(ChatColor.DARK_AQUA + "===" + ChatColor.AQUA + " BossMessenger by Victor2748 " + ChatColor.DARK_AQUA + "===");
		// sender.sendMessage(ChatColor.DARK_GREEN + "Usage: " + ChatColor.GREEN
		// + "/bm <command> <params>");
		// sender.sendMessage(ChatColor.ITALIC +
		// "HOVER commands to see desc. and examples");

		for (CommandInfo cmd : values()) {
			if (!cmd.needsPermission() || GroupManager.hasPermission(sender, cmd)) {
				sender.sendMessage(cmd.getPrefix() + "/" + label + " " + cmd.getUsage());
			}
		}
	}

	public static void sendUsage(CommandSender sender, String label, CommandInfo cmd) {
		LangUtils.sendError(sender, "Usage: /" + label + " " + cmd.getUsage());
	}
}
