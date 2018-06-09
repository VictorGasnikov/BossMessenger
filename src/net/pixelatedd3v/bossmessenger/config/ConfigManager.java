package net.pixelatedd3v.bossmessenger.config;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.MessageListStorage;
import net.pixelatedd3v.bossmessenger.messenger.events.*;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.pixelatedd3v.bossmessenger.protocol.BossBar;
import net.pixelatedd3v.bossmessenger.protocol.impl.Reflection;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ConfigManager {
	public static final File configFile = new File("plugins/BossMessenger/config.yml");
	public static FileConfiguration CONFIG = BossMessenger.INSTANCE.getConfig();
	private static final File messagesFile = new File("plugins/BossMessenger/messages.yml");
	public static FileConfiguration MESSAGES;
	private static final File tasksFile = new File("plugins/BossMessenger/tasks.yml");
	public static FileConfiguration TASKS;
	private static final File blacklistFile = new File("plugins/BossMessenger/blacklist.yml");
	public static FileConfiguration BLACKLIST;
	private static final File eventsFile = new File("plugins/BossMessenger/events.yml");
	public static FileConfiguration EVENTS;
	
	public static String messageLists = "MessageLists";
	public static String tasks = "Tasks";
	public static String events = "Events";
	public static String broadcastDefaultPercent = "Broadcast.Percent";
	public static String broadcastDefaultTime = "Broadcast.DefaultTime";
	public static String scheduleDefaultPercent = "Schedule.Percent";
	public static String scheduleDefaultTime = "Schedule.DefaultTime";
	public static String rdmColorList = "RdmColorList";
	public static String usePlaceholderApi = "UsePlaceholderAPI";
	public static String useMvdwPlaceholdersAPI = "UseMvdwPlaceholderAPI";
	public static String worldBlacklist = "Worlds";
	public static String playerBlacklist = "Players";
	public static String checkUpdates = "CheckUpdates";
	public static String protocolUpdateInterval = "Protocol.Wither.UpdateInterval";
	public static String protocolHealthyWitherRange = "Protocol.Wither.HealthyWitherRange";
	public static String protocolDamagedWitherRange = "Protocol.Wither.DamagedWitherRange";
	public static String pmFormat = "PrivateMessage.Format";
	public static String pmDefaultPercent = "PrivateMessage.Percent";
	public static String pmDefaultTime = "PrivateMessage.DefaultTime";
	public static String pmMessengerModule = "PrivateMessage.MessageType";
	public static String welcomeMessengerModule = "WelcomeMessageType";
	public static String mySqlHost = "MySql.Host";
	public static String mySqlPort = "MySql.Port";
	public static String mySqlDatabase = "MySql.Database";
	public static String mySqlUsername = "MySql.Username";
	public static String mySqlPassword = "MySql.Password";
	public static String mySqlAutoSyncEnabled = "MySql.AutoSync.Enabled";
	public static String mySqlAutoSyncDelay = "MySql.AutoSync.Delay";
	
	public static String cancelDefaultJoinMessage = "CancelDefaultMessages.Join";
	public static String cancelDefaultQuitMessage = "CancelDefaultMessages.Quit";
	public static String cancelDefaultDeathMessage = "CancelDefaultMessages.Death";
	
	public static String defaultBossBarColor = "DefaultBossBar.Color";
	public static String defaultBossBarStyle = "DefaultBossBar.Style";
	public static String defaultBossBarCreateFog = "DefaultBossBar.CreateFog";
	public static String useMessageListPermissions = "UseMessageListPermissions";
	
	public static String defaultBossBarDarkenSky = "DefaultBossBar.DarkenSky";
	private static String configVersion = "ConfigVersion";
	private static int version = 0;
	
	public static void reloadConfig() {
		CONFIG = YamlConfiguration.loadConfiguration(configFile);
		loadConfig();
	}
	
	public static void createConfig() {
		MESSAGES = YamlConfiguration.loadConfiguration(messagesFile);
		TASKS = YamlConfiguration.loadConfiguration(tasksFile);
		EVENTS = YamlConfiguration.loadConfiguration(eventsFile);
		BLACKLIST = YamlConfiguration.loadConfiguration(blacklistFile);
		if (CONFIG.getInt(configVersion) != version) {
			reset();
		}
		if (!MESSAGES.contains(messageLists) || MESSAGES.getConfigurationSection(messageLists).getKeys(false).isEmpty()) {
			List<TemplateMessage> bossbar = new ArrayList<>();
			bossbar.add(new TemplateMessage("&bYo &5%player%&b, wazzup?", 100, 0, new MessageAttribute("Percent", new Percent(100))));
			bossbar.add(new TemplateMessage("&aBossMessenger - best messaging plugin by &bVictor2748", 100, 0, new MessageAttribute("Percent", new Percent(90))));
			bossbar.add(new TemplateMessage("&aRight now, there are &b%online_players%&c/&b%max_players% &aPlayers online", 100, 0, new MessageAttribute("percent", new Percent("server_fullness"))));
			bossbar.add(new TemplateMessage("&aYou are rank &e%rank%", 100, 0, new MessageAttribute("Percent", new Percent(70))));
			bossbar.add(new TemplateMessage("&eThis message will disappear in &b%sec%", 100, 0, new MessageAttribute("Percent", new Percent(60))));
			bossbar.add(new TemplateMessage("&5This message has auto-reducing percentage", 100, 0, new MessageAttribute("Percent", new Percent("auto"))));
			MessageList bossbarlist = new MessageList(bossbar, "default");
			saveMessageList(bossbarlist);
			if (Settings.OLD_MC_VERSION) {
				List<TemplateMessage> actionbar = new ArrayList<>();
				actionbar.add(new TemplateMessage("&bThese are messages on the &4ActionBar", 150, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&ado &1/bm admingui&a to change/disable them", 150, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&aYou have &b$%econ_dollars%.%econ_cents%", 150, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&4And ", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&4Here", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&4Is", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&4An", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&4Animated Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1A&4nimated Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1An&4imated Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Ani&4mated Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Anim&4ated Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Anima&4ted Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animat&4ed Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animate&4d Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animated &4Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animated M&4essage", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animated Me&4ssage", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animated Mes&4sage", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animated Mess&4age", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animated Messa&4ge", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animated Messag&4e", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				actionbar.add(new TemplateMessage("&1Animated Message", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				MessageList actionbarlist = new MessageList(actionbar, "actionbar", MessengerModuleType.ACTIONBAR, MessageListStorage.CONFIG, false);
				saveMessageList(actionbarlist);
				
				List<TemplateMessage> title = new ArrayList<>();
				title.add(new TemplateMessage("&bThis is %sep%&2an &4important &2announcement", 100, 0, new MessageAttribute("Percent", new Percent(100))));
				title.add(new TemplateMessage("&cOn Titles%sep%&2and Subtitles", 100, 450, new MessageAttribute("Percent", new Percent(100))));
				MessageList titlelist = new MessageList(title, "titles", MessengerModuleType.TITLE, MessageListStorage.CONFIG, false);
				saveMessageList(titlelist);
				
				List<TemplateMessage> tab = new ArrayList<>();
				tab.add(new TemplateMessage("&bThis is a%sep%&aMessage on TAB", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage(" ", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage("&bThis is a%sep%&aMessage on TAB", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage(" ", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage("&bThis is a%sep%&aMessage on TAB", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage(" ", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage("&aHeader%sep%&c& Footer", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage(" ", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage("&aHeader%sep%&c& Footer", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage(" ", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage("&aHeader%sep%&c& Footer", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				tab.add(new TemplateMessage(" ", 10, 0, new MessageAttribute("Percent", new Percent(100))));
				MessageList tablist = new MessageList(tab, "tab", MessengerModuleType.TAB, MessageListStorage.CONFIG, false);
				saveMessageList(tablist);
			}
			List<TemplateMessage> chat = new ArrayList<>();
			chat.add(new TemplateMessage("&bChat Message &c#1", 1000, 0, new MessageAttribute("Percent", new Percent(100))));
			chat.add(new TemplateMessage("&bChat Message &c#2", 1000, 0, new MessageAttribute("Percent", new Percent(100))));
			chat.add(new TemplateMessage("&bChat Message &c#3", 1000, 0, new MessageAttribute("Percent", new Percent(100))));
			chat.add(new TemplateMessage("&bChat Message &c#4", 1000, 0, new MessageAttribute("Percent", new Percent(100))));
			MessageList chatlist = new MessageList(chat, "chat", MessengerModuleType.CHAT, MessageListStorage.CONFIG, false);
			saveMessageList(chatlist);
			
			saveMessages();
		}
		if (!TASKS.contains(tasks) || TASKS.getConfigurationSection(tasks).getKeys(false).isEmpty()) {
			List<Task> tasks = new ArrayList<>();
			List<String> makeitraincmds = new ArrayList<>();
			makeitraincmds.add("say Oops, sorry! We are rong again. No rain will be!");
			tasks.add(new Task("makeitrain", "&4[&6Weather&aForecast&4] &bRa1n 1t w1ll b3 in &e%sec%", makeitraincmds));
			List<String> stoptheservercmds = new ArrayList<>();
			stoptheservercmds.add("stop");
			tasks.add(new Task("stoptheserver", "&4Server is going down in &e%sec%", stoptheservercmds));
			List<String> starttheskynet = new ArrayList<>();
			starttheskynet.add("say Now, Skynet will initiate a massive nuclear attack in your a$$");
			tasks.add(new Task("starttheskynet", "&aSkynet activated! Its gonna f*** you up in %sec% seconds.", starttheskynet));
			saveTasks(tasks);
			saveTasksInConfig();
		}
		if (!BLACKLIST.contains(playerBlacklist)) {
			BLACKLIST.set(playerBlacklist, new ArrayList<String>());
			saveBlacklistInConfig();
		}
		if (!BLACKLIST.contains(worldBlacklist)) {
			BLACKLIST.set(worldBlacklist, new ArrayList<String>());
			saveBlacklistInConfig();
		}
		save();
	}
	
	public static void loadConfiguration() {
		// Load messages
		loadMessages();
		
		// Load tasks
		loadTasks();
		
		// Load events
		// loadEvents();
		
		// Load blacklists
		loadBlacklist();
		
		// Load other properties
		loadConfig();
	}
	
	public static void loadMessages() {
		ConfigurationSection secLists = MESSAGES.getConfigurationSection(messageLists);
		Set<String> listNames = secLists.getKeys(false);
		for (String key : listNames) {
			MessageList list = (MessageList) secLists.get(key);
			Settings.MESSAGES.put(key, list);
		}
	}
	
	public static void loadTasks() {
		ConfigurationSection secTasks = TASKS.getConfigurationSection(tasks);
		Set<String> taskNames = secTasks.getKeys(false);
		for (String key : taskNames) {
			Task task = (Task) secTasks.get(key);
			Settings.TASKS.put(key, task);
		}
	}
	
	public static void loadEvents() {
		boolean e1 = saveEventIfDoesNotExist("PlayerJoin", true, "&ePlayer &a%target%&e joined.", "100", 100, "default"); // FIXME: 2016-03-18
		boolean e2 = saveEventIfDoesNotExist("PlayerQuit", true, "&6Player &e%target%&6 just left. We'll miss him ;(", "100", 100, "default");
		boolean e3 = saveEventIfDoesNotExist("PlayerPVPDeath", false, "&ePlayer &a%victim%&e got killed by &4%killer%", "100", 100, "default");
		boolean e4 = saveEventIfDoesNotExist("WelcomeEvent", !Settings.OLD_MC_VERSION, "&eWelcome &a%player%%sep%&ato &5%server_name%", "100", 100, "default");
		if (e1 || e2 || e3 || e4) {
			saveEventsInConfig();
		}
		// if (!EVENTS.contains(events) ||
		// EVENTS.getConfigurationSection(events).getKeys(false).isEmpty()) {
		// saveEvent(new Event("PlayerJoin", true, "&eWelcome &a%target%&e to
		// &b%server_name%", new Percent("100"), 100,
		// Arrays.asList("default")));
		// saveEvent(new Event("PlayerQuit", true, "&6Player &e%target%&6 just
		// left. We'll miss him ;(", new Percent("100"), 100,
		// Arrays.asList("default")));
		// saveEvent(new Event("PlayerPVPDeath", false, "&ePlayer &a%victim%&e
		// got killed by &4%killer%", new Percent("100"), 100,
		// Arrays.asList("default")));
		// saveEvent(new Event("WelcomeEvent", true, "&eWelcome
		// &a%player%%sep%&ato &5%server_name%", new Percent("100"), 100,
		// Arrays.asList("default")));
		// saveEventsInConfig();
		// }
		
		Settings.PLAYER_JOIN_EVENT = loadEvent("PlayerJoin", JoinEvent.class);
		Settings.PLAYER_QUIT_EVENT = loadEvent("PlayerQuit", QuitEvent.class);
		Settings.PLAYER_PVP_DEATH_EVENT = loadEvent("PlayerPVPDeath", PVPDeathEvent.class);
		Settings.PLAYER_WELCOME_EVENT = loadEvent("WelcomeEvent", WelcomeEvent.class);
		Settings.EVENTS.add(Settings.PLAYER_JOIN_EVENT);
		Settings.EVENTS.add(Settings.PLAYER_QUIT_EVENT);
		Settings.EVENTS.add(Settings.PLAYER_PVP_DEATH_EVENT);
		Settings.EVENTS.add(Settings.PLAYER_WELCOME_EVENT);
	}
	
	private static boolean saveEventIfDoesNotExist(String name, boolean enabled, String message, String percent, int show, String... messengerNames) {
		String path = events + "." + name;
		if (!EVENTS.contains(path) || EVENTS.getConfigurationSection(path).getKeys(false).isEmpty()) {
			saveEvent(new Event(name, enabled, message, show, Arrays.asList(messengerNames), new MessageAttribute("Percent", new Percent(percent))));
			return true;
		}
		return false;
	}
	
	public static void loadBlacklist() {
		for (String uuid : BLACKLIST.getStringList(playerBlacklist)) {
			Settings.PLAYER_BLACKLIST.add(UUID.fromString(uuid));
		}
		
		Settings.WORLD_BLACKLIST = BLACKLIST.getStringList(worldBlacklist);
		Settings.WORLD_BLACKLIST_ENABLED = !Settings.WORLD_BLACKLIST.isEmpty();
	}
	
	public static void loadConfig() {
		Settings.CHECK_UPDATES = CONFIG.getBoolean(checkUpdates);
		Settings.USE_MESSAGE_LIST_PERMISSIONS = CONFIG.getBoolean(useMessageListPermissions);
		Settings.RDM_COLORS = CONFIG.getString(rdmColorList);
		Settings.USE_PLACEHOLDER_API = CONFIG.getBoolean(usePlaceholderApi);
		Settings.USE_MVDW_PLACEHOLDERS = CONFIG.getBoolean(useMvdwPlaceholdersAPI);
		
		Settings.BROADCAST_PERCENT = new Percent(CONFIG.getString(broadcastDefaultPercent));
		Settings.BROADCAST_DEFAULT_TIME = CONFIG.getInt(broadcastDefaultTime);
		
		Settings.SCHEDULE_PERCENT = new Percent(CONFIG.getString(scheduleDefaultPercent));
		Settings.SCHEDULE_DEFAULT_TIME = CONFIG.getInt(scheduleDefaultTime);
		
		Settings.PROTOCOL_UPDATE_INTERVAL = CONFIG.getInt(protocolUpdateInterval);
		Settings.PROTOCOL_HEALTHY_WITHER_RANGE = CONFIG.getInt(protocolHealthyWitherRange);
		Settings.PROTOCOL_DAMAGED_WITHER_RANGE = CONFIG.getInt(protocolDamagedWitherRange);
		
		Settings.PM_FORMAT = CONFIG.getString(ChatColor.translateAlternateColorCodes('&', pmFormat));
		Settings.PM_PERCENT = new Percent(CONFIG.getString(pmDefaultPercent));
		Settings.PM_DEFAULT_TIME = CONFIG.getInt(pmDefaultTime);
		Settings.PM_MODULE_TYPE = MessengerModuleType.valueOf(CONFIG.getString(pmMessengerModule));
		
		Settings.WELCOME_MODULE_TYPE = MessengerModuleType.valueOf(CONFIG.getString(welcomeMessengerModule));
		if (!BossBar.useOld) {
			Settings.DEFAULT_BOSSBAR_COLOR = BarColor.valueOf(CONFIG.getString(defaultBossBarColor));
			Settings.DEFAULT_BOSSBAR_STYLE = BarStyle.valueOf(CONFIG.getString(defaultBossBarStyle));
			Settings.DEFAULT_BOSSBAR_DARKEN_SKY = CONFIG.getBoolean(defaultBossBarDarkenSky);
			Settings.DEFAULT_BOSSBAR_CREATE_FOG = CONFIG.getBoolean(defaultBossBarCreateFog);
			//		Settings.DEFAULT_BOSSBAR_PLAY_BOSS_MUSIC = CONFIG.getBoolean(defaultBossBarPlayBossMusic);
		}
		
		Settings.CANCEL_DEFAULT_JOIN_MESSAGE = CONFIG.getBoolean(cancelDefaultJoinMessage);
		Settings.CANCEL_DEFAULT_QUIT_MESSAGE = CONFIG.getBoolean(cancelDefaultQuitMessage);
		Settings.CANCEL_DEFAULT_DEATH_MESSAGE = CONFIG.getBoolean(cancelDefaultDeathMessage);
	}
	
	public static void loadPriorityConfig() {
		CONFIG.options().copyDefaults(true);
		Settings.MYSQL_HOST = CONFIG.getString(mySqlHost);
		Settings.MYSQL_PORT = CONFIG.getString(mySqlPort);
		Settings.MYSQL_DATABASE = CONFIG.getString(mySqlDatabase);
		Settings.MYSQL_USERNAME = CONFIG.getString(mySqlUsername);
		Settings.MYSQL_PASSWORD = CONFIG.getString(mySqlPassword);
		Settings.MYSQL_AUTOSYNC_DELAY = CONFIG.getInt(mySqlAutoSyncDelay);
		Settings.MYSQL_AUTOSYNC_ENABLED = CONFIG.getBoolean(mySqlAutoSyncEnabled);
	}
	
	public static void saveMessageLists(Collection<MessageList> lists) {
		for (MessageList list : lists) {
			saveMessageList(list);
		}
	}
	
	public static void saveMessageList(MessageList list) {
		MESSAGES.set(messageLists + "." + list.getName(), list);
	}
	
	public static void removeMessageList(String listname) {
		MESSAGES.set(messageLists + "." + listname, null);
	}
	
	public static void saveMessages() {
		try {
			MESSAGES.save(messagesFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveTasks(Collection<Task> tasks) {
		for (Task task : tasks) {
			saveTask(task);
		}
	}
	
	public static void saveTask(Task task) {
		TASKS.set(tasks + "." + task.getName(), task);
	}
	
	public static void removeTask(String taskname) {
		TASKS.set(tasks + "." + taskname, null);
	}
	
	public static void saveTasksInConfig() {
		try {
			TASKS.save(tasksFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveBlacklistPlayers() {
		List<String> uuids = new ArrayList<>();
		for (UUID uuid : Settings.PLAYER_BLACKLIST) {
			uuids.add(uuid.toString());
		}
		BLACKLIST.set(playerBlacklist, uuids);
	}
	
	public static void saveBlacklistWorlds() {
		BLACKLIST.set(worldBlacklist, Settings.WORLD_BLACKLIST);
	}
	
	public static void saveBlacklistInConfig() {
		try {
			BLACKLIST.save(blacklistFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Event loadEvent(String eventname) {
		return loadEvent(eventname, Event.class);
	}
	
	public static <T> T loadEvent(String eventname, Class<T> type) {
		ConfigurationSection sec = EVENTS.getConfigurationSection(events + "." + eventname);
		String text = sec.getString("Text");
		List<MessageAttribute> attributes = (List<MessageAttribute>) sec.getList("Attributes");
		if (attributes == null) {
			attributes = new ArrayList<>();
		}
		String pct = sec.getString("Percent");
		if (pct != null) {
			attributes.add(new MessageAttribute("Percent", new Percent(pct)));
		}
		int show = sec.getInt("Show");
		boolean enabled = sec.getBoolean("Enabled");
		List<String> msgLists = sec.getStringList("MessageLists");
		T result = null;
		try {
			result = (T) type.getConstructor(String.class, boolean.class, String.class, int.class, List.class, Collection.class).newInstance(eventname, enabled, text, show, msgLists, attributes);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void saveEvent(Event event) {
		ConfigurationSection sec = EVENTS.createSection(events + "." + event.getName());
		sec.set("Text", event.getText());
		sec.set("Attributes", new ArrayList<>(event.getAttributes().values()));
		sec.set("Show", event.getShow());
		sec.set("Enabled", event.isEnabled());
		List<String> messengerNames = new ArrayList<>();
		for (Messenger messenger : event.getMessengers()) {
			messengerNames.add(messenger.getName());
		}
		sec.set("MessageLists", messengerNames);
	}
	
	public static void removeEvent(String eventname) {
		EVENTS.set(events + "." + eventname, null);
	}
	
	public static void saveEventsInConfig() {
		try {
			EVENTS.save(eventsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save() {
		BossMessenger.INSTANCE.saveConfig();
	}
	
	public static void reset() {
		File configFile = new File(BossMessenger.INSTANCE.getDataFolder(), "config.yml");
		configFile.delete();
		BossMessenger.INSTANCE.saveDefaultConfig();
		BossMessenger.INSTANCE.reloadConfig();
	}
	
	public static void setConfigValue(String key, Object value) {
		CONFIG.set(key, value);
	}
}
