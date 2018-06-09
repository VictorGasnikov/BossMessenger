package net.pixelatedd3v.bossmessenger.ui;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.GroupManager;
import net.pixelatedd3v.bossmessenger.Reference;
import net.pixelatedd3v.bossmessenger.Updater;
import net.pixelatedd3v.bossmessenger.config.ConfigManager;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.lang.LangUtils;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.MessageListStorage;
import net.pixelatedd3v.bossmessenger.messenger.events.Event;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger.StandardMessenger;
import net.pixelatedd3v.bossmessenger.messenger.task.ScheduledTask;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.pixelatedd3v.bossmessenger.protocol.impl.babywither.BossBarPlugin;
import net.pixelatedd3v.bossmessenger.utils.MessageUtils;
import net.pixelatedd3v.bossmessenger.utils.MySqlUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Actions {
	public static void createList(String listname) {
		MessageList list = new MessageList(new ArrayList<TemplateMessage>(), listname);
		Settings.addMessageList(list);
		BossMessenger.MESSENGERS.put(listname, new StandardMessenger(list, Reference.DEFAULT_MODULE.getModule()));
		ConfigManager.saveMessages();
	}

	public static void deleteList(String listname) {
		Settings.removeMessageList(listname);
		BossMessenger.MESSENGERS.remove(listname).dispose();
		ConfigManager.saveMessages();
	}

	public static void deleteMessage(String listname, int id) {
		Settings.removeMessage(listname, id);
		BossMessenger.MESSENGERS.get(listname).restart();
		ConfigManager.saveMessages();
	}

	public static void addMessage(String listname, String text, int show, int interval, Collection<MessageAttribute> attributes) {
		Settings.addMessage(new TemplateMessage(text, show, interval, attributes), listname);
		BossMessenger.MESSENGERS.get(listname).restart();
		ConfigManager.saveMessages();
	}

	public static void addMessage(String listname, String text, Percent percent, int show, int interval) {
		Settings.addMessage(new TemplateMessage(text, show, interval, new MessageAttribute("Percent", percent)), listname);
		BossMessenger.MESSENGERS.get(listname).restart();
		ConfigManager.saveMessages();
	}

	public static void editText(MessageList list, TemplateMessage message, String value) {
		message.setText(value);
		saveMessages(list);
	}

	public static void editPercent(MessageList list, TemplateMessage message, String value) {
		message.setPercent(new Percent(value));
		saveMessages(list);
	}

	public static boolean editShow(MessageList list, TemplateMessage message, String value) {
		int show = convertPositiveInteger(value);
		if (show != -1) {
			message.setShow(show);
			saveMessages(list);
			return true;
		}
		return false;
	}

	public static int convertPositiveInteger(String value) {
		try {
			int show = Integer.parseInt(value);
			if (show > 0) {
				return show;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public static boolean editInterval(MessageList list, TemplateMessage message, String value) {
		try {
			int newInterval = Integer.parseInt(value);
			if (newInterval >= 0) {
				message.setInterval(newInterval);
				saveMessages(list);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static void saveMessages(MessageList list) {
		ConfigManager.saveMessageList(list);
		ConfigManager.saveMessages();
	}

	public static boolean checkUpdate() {
		String latestVersion = Updater.getVersion(BossMessenger.RESOURCE_ID);
		if (latestVersion != null && !latestVersion.equals(BossMessenger.VERSION)) {
			Settings.UPDATE_AVAILABLE = true;
			Settings.UPDATE_VERSION = latestVersion;
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (GroupManager.hasPermission(player, Reference.PERMISSION_UPDATE_NOTIFICATION)) {
					LangUtils.sendUpdateNotification(player);
				}
			}
			return true;
		}
		return false;
	}

	public static boolean broadcast(String text, String time) {
		int show = convertPositiveInteger(time);
		if (show != -1) {
			broadcast(text, show);
			return true;
		} else {
			return false;
		}
	}

	public static void broadcast(String text, int time) {
		TimedMessage message = new TimedMessage(text, time * 20, new MessageAttribute("Percent", Settings.BROADCAST_PERCENT));
		for (Messenger messenger : BossMessenger.MESSENGERS.values()) {
			messenger.broadcast(message);
		}
	}

	public static void quickBroadcast(String text) {
		broadcast(text, Settings.BROADCAST_DEFAULT_TIME);
	}

	public static boolean groupBroadcast(String text, String time, Messenger messenger) {
		int show;
		try {
			show = Integer.parseInt(time);
			Validate.isTrue(show > 0);
		} catch (Exception e) {
			return false;
		}
		groupBroadcast(text, show, messenger);
		return true;
	}

	public static void groupBroadcast(String text, int time, Messenger messenger) {
		messenger.broadcast(new TimedMessage(text, time * 20, new MessageAttribute("Percent", Settings.BROADCAST_PERCENT)));
	}

	public static void reload(CommandSender sender) {
		// Bukkit.getPluginManager().disablePlugin(BossMessenger.INSTANCE);
		// Bukkit.getPluginManager().enablePlugin(BossMessenger.INSTANCE);
		LangUtils.sendMessage(sender, Lang.RELOADING);
		BossBarPlugin.instance.onDisable();
		ConfigManager.reloadConfig();
		new BukkitRunnable() {

			@Override
			public void run() {
				BossBarPlugin.startUpdater();
			}
		}.runTaskLater(BossMessenger.INSTANCE, 20L);
		LangUtils.sendMessage(sender, Lang.RELOADED);
	}

	public static void toggleRandom(MessageList list) {
		list.setRandom(!list.isRandom());
		ConfigManager.saveMessageList(list);
		ConfigManager.saveMessages();
	}

	public static void renameList(MessageList list, String newName) {
		String oldName = list.getName();
		Messenger messenger = BossMessenger.MESSENGERS.remove(oldName);
		Settings.removeMessageList(oldName);
		MessageList newList = new MessageList(list.getMessages(), newName, list.isRandom());
		messenger.setMessages(newList);
		BossMessenger.MESSENGERS.put(newName, messenger);
		Settings.addMessageList(newList);
		ConfigManager.saveMessages();
	}

	public static void moveMessage(Messenger messenger, int id, int newId) {
		MessageList list = messenger.getMessages();
		list.getMessages().add(newId, list.removeMessage(id));
		ConfigManager.saveMessages();
	}

	public static void createTask(String taskname, String text) {
		Task task = new Task(taskname, text, new ArrayList<String>());
		Settings.addTask(task);
		ConfigManager.saveTasksInConfig();
	}

	public static void changeTaskMessage(Task task, String message) {
		task.setMessage(message);
		ConfigManager.saveTask(task);
		ConfigManager.saveTasksInConfig();
	}

	public static void deleteTask(String taskname) {
		Settings.removeTask(taskname);
		ConfigManager.saveTasksInConfig();
	}

	public static void renameTask(Task task, String newName) {
		String oldName = task.getName();
		Settings.removeTask(oldName);
		task.setName(newName);
		Settings.addTask(task);
		ConfigManager.saveTasksInConfig();
	}

	public static void addTaskCmd(Task task, String cmd) {
		Settings.addTaskCmd(task, cmd);
		ConfigManager.saveTasksInConfig();
	}

	public static void removeTaskCmd(Task task, int id) {
		Settings.removeTaskCmd(task, id);
		ConfigManager.saveTasksInConfig();
	}

	public static void schedule(Task task, int time) {
		TimedMessage message = new TimedMessage(task.getMessage(), time * 20, new MessageAttribute("Percent", Settings.SCHEDULE_PERCENT));
		List<String> cmds = task.getCommands();
		MessengerManager.scheduleForAll(message, cmds);
	}

	public static void groupSchedule(Messenger messenger, Task task, int time) {
		TimedMessage message = new TimedMessage(task.getMessage(), time * 20, new MessageAttribute("Percent", Settings.SCHEDULE_PERCENT));
		List<String> cmds = task.getCommands();
		messenger.schedule(message, cmds);
	}

	public static boolean cancelTask(ScheduledTask task) {
		if (task != null) {
			task.cancel();
			return true;
		} else {
			return false;
		}
	}

	public static void saveEvent(Event event) {
		ConfigManager.saveEvent(event);
		ConfigManager.saveEventsInConfig();
	}

	public static void toggleEvent(Event event) {
		event.setEnabled(!event.isEnabled());
		saveEvent(event);
	}

	public static void sendMessageToPlayer(Player player, TimedMessage message) {
		Messenger messenger = MessengerManager.getPrivateMessengerPool(player, true).getMessenger(Settings.PM_MODULE_TYPE, true);
		messenger.broadcast(message);
	}

	public static void privateMessage(String from, Player to, String text) {
		TimedMessage message = new TimedMessage(Settings.PM_FORMAT.replaceAll("%name%", from).replaceAll("%msg%", text), Settings.PM_DEFAULT_TIME * 20, new MessageAttribute("Percent", Settings.PM_PERCENT));
		sendMessageToPlayer(to, message);
	}

	public static void toggleMessages(Player player) {
		UUID uuid = player.getUniqueId();
		if (Settings.PLAYER_BLACKLIST.contains(uuid)) {
			Settings.PLAYER_BLACKLIST.remove(uuid);
			MessageUtils.update(player);
		} else {
			Settings.PLAYER_BLACKLIST.add(uuid);
			MessageUtils.remove(player);
		}
	}

	public static void syncMySql(CommandSender sender) {

	}

	public static boolean createMySqlList(MessageList list) {
		list.setStorage(MessageListStorage.MYSQL);
		MySqlUtils.createTableForList(list.getName());
		MySqlUtils.fillMySqlTable(list);
		return true;
	}

	public static boolean deleteMySqlList(String listname) {

		return true;
	}
}
