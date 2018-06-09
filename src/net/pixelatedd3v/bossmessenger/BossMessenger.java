package net.pixelatedd3v.bossmessenger;

import net.pixelatedd3v.bossmessenger.api.BossMessengerAPI;
import net.pixelatedd3v.bossmessenger.api.events.PlayerMessageEvent;
import net.pixelatedd3v.bossmessenger.api.events.PlayerMessageListener;
import net.pixelatedd3v.bossmessenger.config.ConfigManager;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.listeners.*;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.generator.*;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.global.RandomPercentEvaluator;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.global.ServerFullnessEvaluator;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.player.PlayerHealthEvaluator;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.global.MaxPlayersGenerator;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.global.OnlinePlayersGenerator;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.global.RdmColorGenerator;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.global.ServerNameGenerator;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.player.PlayerNameGenerator;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.player.PlayerWorldGenerator;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger.StandardMessenger;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.pixelatedd3v.bossmessenger.protocol.ActionBar;
import net.pixelatedd3v.bossmessenger.protocol.BossBar;
import net.pixelatedd3v.bossmessenger.protocol.Title;
import net.pixelatedd3v.bossmessenger.protocol.impl.titleapi.TitleAPI;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.commands.Commands;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BossMessenger extends JavaPlugin {
	
	public static BossMessenger INSTANCE;
	public static String VERSION;
	public static final Map<String, Messenger> MESSENGERS = new HashMap<>();
	public static final String LINK = "https://www.spigotmc.org/resources/7287/";
	public static final int RESOURCE_ID = 7287;
	private static String user2 = "%%__USER__%%";
	
	private BossMessengerAPI api = new BossMessengerAPI();
	
	public void onEnable() {
		user2.equals("123");
		INSTANCE = this;
		VERSION = getDescription().getVersion();
		
		//Do general stuff
		Pattern versionPattern = Pattern.compile("[\\w\\-\\d().\\s]*\\(MC: ([\\d.]+)\\)");
		Matcher versionMatcher = versionPattern.matcher(Bukkit.getVersion());
		if (versionMatcher.find()) {
			Settings.MC_VERSION = versionMatcher.group(1);
		}
		Settings.OLD_MC_VERSION = Settings.MC_VERSION.charAt(2) != 55;
		
		getCommand("bm").setExecutor(new Commands());
		
		ConfigurationSerialization.registerClass(Percent.class, "Percent");
		ConfigurationSerialization.registerClass(MessageAttribute.class, "Attribute");
		ConfigurationSerialization.registerClass(TemplateMessage.class, "Message");
		ConfigurationSerialization.registerClass(MessageList.class, "MessageList");
		ConfigurationSerialization.registerClass(Task.class, "Task");
		ConfigManager.loadPriorityConfig();
		ConfigManager.createConfig();
		ConfigManager.loadConfiguration();
		
		BossBar.init(this);
		if (Settings.OLD_MC_VERSION) {
			ActionBar.init();
			Title.init();
		}
		
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayChangeWorldListener(), this);
		
		api.registerPlayerMessageListener(new PlayerMessageListener() {
			
			@Override
			public void onMessageEvaluation(PlayerMessageEvent event) {
				Player player = event.getPlayer();
				String text = event.getText();
				boolean hasDollars = text.contains("%econ_dollars%");
				boolean hasCents = text.contains("%econ_cents%");
				if (hasDollars || hasCents) {
					if (Settings.ECONOMY != null) {
						String[] balance = Double.toString(Settings.ECONOMY.getBalance(player)).split("\\.");
						if (hasDollars) {
							text = text.replaceAll("%econ_dollars%", balance[0]);
						}
						if (hasCents) {
							text = text.replaceAll("%econ_cents%", balance[1]);
						}
					} else {
						text = Lang.NO_ECON_PLUGIN;
					}
				}
				if (text.contains("%rank%")) {
					if (Settings.PERMISSIONS != null) {
						try {
							text = text.replaceAll("%rank%", Settings.PERMISSIONS.getPrimaryGroup(player));
						} catch (Exception e) {
							text = Lang.NO_PERM_PLUGIN;
						}
					} else {
						text = Lang.NO_PERM_PLUGIN;
					}
				}
				event.setText(text);
			}
		});
		
		MessageGenerator.addGlobalPlaceholder(new GlobalPlaceholder("%rdm_color%", new RdmColorGenerator(), PlaceholderMode.REPLACE_EACH));
		MessageGenerator.addGlobalPlaceholder(new GlobalPlaceholder("%online_players%", new OnlinePlayersGenerator(), PlaceholderMode.REPLACE_ALL));
		MessageGenerator.addGlobalPlaceholder(new GlobalPlaceholder("%max_players%", new MaxPlayersGenerator(), PlaceholderMode.REPLACE_ALL));
		MessageGenerator.addGlobalPlaceholder(new GlobalPlaceholder("%server_name%", new ServerNameGenerator(), PlaceholderMode.REPLACE_ALL));
		
		MessageGenerator.addPlayerPlaceholder(new PlayerPlaceholder(this, "%player%", new PlayerNameGenerator(), PlaceholderMode.REPLACE_ALL));
		MessageGenerator.addPlayerPlaceholder(new PlayerPlaceholder(this, "%world%", new PlayerWorldGenerator(), PlaceholderMode.REPLACE_ALL));
		
		MessageGenerator.addGlobalPercentEvaluator(new GlobalPercentVariable(INSTANCE, "server_fullness", new ServerFullnessEvaluator()));
		MessageGenerator.addGlobalPercentEvaluator(new GlobalPercentVariable(INSTANCE, "random_percent", new RandomPercentEvaluator()));
		
		MessageGenerator.addPlayerPercentEvaluator(new PlayerPercentVariable(INSTANCE, "player_health", new PlayerHealthEvaluator()));
		
		// Vault
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			if (setupEconomy()) {
				getLogger().info("Successfully hooked into Vault's economy.");
			}
			if (setupPermissions()) {
				getLogger().info("Successfully hooked into Vault's permission manager.");
			}
		}
		// PlaceholderAPI
		if (Settings.USE_PLACEHOLDER_API) {
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				
			} else {
				Settings.USE_PLACEHOLDER_API = false;
				getLogger().warning("Property 'UsePlaceholderAPI' is enabled in the config, but the PlaceholderAPI plugin is not installed/enabled!");
			}
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// Messengers
				for (MessageList list : Settings.MESSAGES.values()) {
					MESSENGERS.put(list.getName(), new StandardMessenger(list, list.getModule().getModule()));
				}
				ConfigManager.loadEvents();
				
				// Check Update
				if (Settings.CHECK_UPDATES) {
					Actions.checkUpdate();
				}
			}
		}.runTask(this);
	}
	
	public void onDisable() {
		ConfigManager.saveBlacklistPlayers();
		ConfigManager.saveBlacklistInConfig();
		
		for (Messenger messenger : MESSENGERS.values()) {
			messenger.dispose();
		}
		MESSENGERS.clear();
		Settings.MESSAGES.clear();
		Settings.TASKS.clear();
		Settings.PLAYER_BLACKLIST.clear();
		BossBar.destroy(this);
	}
	
	@Override
	public File getFile() {
		return super.getFile();
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			Settings.ECONOMY = economyProvider.getProvider();
		}
		
		return Settings.ECONOMY != null;
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			Settings.PERMISSIONS = permissionProvider.getProvider();
		}
		return Settings.PERMISSIONS != null;
	}
	
	public BossMessengerAPI getAPI() {
		return api;
	}
	//
	// public static boolean checkUpdate() {
	// Settings.UPDATER = new Updater(BossMessenger.INSTANCE, 64888,
	// BossMessenger.INSTANCE.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
	// Settings.UPDATE_AVAILABLE =
	// !Settings.UPDATER.getLatestName().equalsIgnoreCase(VERSION);
	// return Settings.UPDATE_AVAILABLE;
	// }
}
