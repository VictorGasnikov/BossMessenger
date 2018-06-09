package net.pixelatedd3v.bossmessenger.config;

import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.events.*;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

import java.util.*;

public class Settings {

	public static final Map<String, MessageList> MESSAGES = new HashMap<>();
	public static final Map<String, Task> TASKS = new HashMap<>();
	public static String MC_VERSION;
	public static boolean OLD_MC_VERSION;
	public static String RDM_COLORS;
	public static Percent BROADCAST_PERCENT;
	public static int BROADCAST_DEFAULT_TIME;
	public static Percent SCHEDULE_PERCENT;
	public static int SCHEDULE_DEFAULT_TIME;
	public static String PM_FORMAT;
	public static Percent PM_PERCENT;
	public static int PM_DEFAULT_TIME;
	public static MessengerModuleType PM_MODULE_TYPE;
	public static MessengerModuleType WELCOME_MODULE_TYPE;
	public static boolean WORLD_BLACKLIST_ENABLED;
	public static List<String> WORLD_BLACKLIST;
	public static final List<UUID> PLAYER_BLACKLIST = new ArrayList<>();
	public static JoinEvent PLAYER_JOIN_EVENT;
	public static QuitEvent PLAYER_QUIT_EVENT;
	public static PVPDeathEvent PLAYER_PVP_DEATH_EVENT;
	public static WelcomeEvent PLAYER_WELCOME_EVENT;
	public static final List<Event> EVENTS = new ArrayList<>();
	public static Economy ECONOMY;
	public static Permission PERMISSIONS;
	public static boolean USE_PLACEHOLDER_API;
	public static boolean USE_MVDW_PLACEHOLDERS;
	public static boolean CHECK_UPDATES;
	public static boolean UPDATE_AVAILABLE;
	public static String UPDATE_VERSION;
	public static int PROTOCOL_UPDATE_INTERVAL;
	public static int PROTOCOL_HEALTHY_WITHER_RANGE;
	public static int PROTOCOL_DAMAGED_WITHER_RANGE;
	public static boolean MYSQL_ENABLED;
	public static String MYSQL_HOST;
	public static String MYSQL_PORT;
	public static String MYSQL_DATABASE;
	public static String MYSQL_USERNAME;
	public static String MYSQL_PASSWORD;
	public static int MYSQL_AUTOSYNC_DELAY;
	public static boolean MYSQL_AUTOSYNC_ENABLED;

	public static boolean CANCEL_DEFAULT_JOIN_MESSAGE;
	public static boolean CANCEL_DEFAULT_QUIT_MESSAGE;
	public static boolean CANCEL_DEFAULT_DEATH_MESSAGE;

	public static BarColor DEFAULT_BOSSBAR_COLOR;
	public static BarStyle DEFAULT_BOSSBAR_STYLE;
	public static boolean DEFAULT_BOSSBAR_CREATE_FOG;
	public static boolean DEFAULT_BOSSBAR_DARKEN_SKY;
	public static boolean USE_MESSAGE_LIST_PERMISSIONS;
	//	public static boolean DEFAULT_BOSSBAR_PLAY_BOSS_MUSIC;


	public static void addMessageList(MessageList list) {
		String name = list.getName();
		MESSAGES.put(name, list);
		ConfigManager.saveMessageList(list);
	}

	public static void removeMessageList(String listname) {
		MESSAGES.remove(listname);
		ConfigManager.removeMessageList(listname);
	}

	public static void addTask(Task task) {
		String name = task.getName();
		TASKS.put(name, task);
		ConfigManager.saveTask(task);
	}

	public static void removeTask(String taskname) {
		TASKS.remove(taskname);
		ConfigManager.removeTask(taskname);
	}

	public static boolean hasTask(String taskname) {
		return TASKS.containsKey(taskname);
	}

	public static void addMessage(TemplateMessage message, String listname) {
		MessageList list = MESSAGES.get(listname);
		list.addMessage(message);
		ConfigManager.saveMessageList(list);
	}

	public static void addTaskCmd(Task task, String cmd) {
		task.addCommand(cmd);
		ConfigManager.saveTask(task);
	}

	public static void removeTaskCmd(Task task, int id) {
		task.removeCommand(id);
		ConfigManager.saveTask(task);
	}

	public static void removeMessage(String listname, int id) {
		MessageList list = MESSAGES.get(listname);
		list.removeMessage(id);
		ConfigManager.saveMessageList(list);
	}

	public static boolean hasList(String listname) {
		return MESSAGES.containsKey(listname);
	}

	public static boolean hasMessage(String listname, int id) {
		return MESSAGES.get(listname).hasMessage(id);
	}
}
