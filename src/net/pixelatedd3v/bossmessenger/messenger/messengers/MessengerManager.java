package net.pixelatedd3v.bossmessenger.messenger.messengers;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.GroupManager;
import net.pixelatedd3v.bossmessenger.Reference;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger.FinishSchedule;
import net.pixelatedd3v.bossmessenger.messenger.task.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class MessengerManager {

	public static final Map<UUID, PrivateMessengerPool> PLAYER_MESSENGERS = new HashMap<>();
	// public static final Map<UUID, Messenger> PLAYER_MESSENGERS = new
	// HashMap<UUID, Messenger>();
	public static ScheduledTask GLOBALTASK;
	public static BukkitTask TASK;
	public static List<String> SCHEDULE_COMMANDS;

	public static boolean scheduleForAll(TimedMessage message, List<String> cmds) {
		boolean output = false;
		if (GLOBALTASK != null) {
			GLOBALTASK.cancel();
			output = true;
		}
		GLOBALTASK = new ScheduledTask(null, message);
		TASK = new FinishSchedule(null, cmds).runTaskLater(BossMessenger.INSTANCE, message.getShow());
		for (Messenger messenger : BossMessenger.MESSENGERS.values()) {
			messenger.schedule(message, null);
		}

		return output;
	}

	public static void cancelGlobalTask() {
		if (GLOBALTASK != null) {
			GLOBALTASK.cancel();
		}
	}

	public static PrivateMessengerPool getPrivateMessengerPool(Player player, boolean create) {
		UUID uuid = player.getUniqueId();
		PrivateMessengerPool pool = PLAYER_MESSENGERS.get(uuid);
		if (pool != null) {
			return pool;
		} else {
			return create ? createPrivateMessengerPool(player) : null;
		}
	}

	public static PrivateMessengerPool createPrivateMessengerPool(Player player) {
		PrivateMessengerPool messenger = new PrivateMessengerPool(player);
		PLAYER_MESSENGERS.put(player.getUniqueId(), messenger);
		return messenger;
	}

	public static List<Messenger> getPlayerGroupMessengers(Player player) {
		List<Messenger> output = new ArrayList<>();
		for (String name : BossMessenger.MESSENGERS.keySet()) {
			if (GroupManager.hasAssignedPermission(player, Reference.PERMISSION_LIST + name)) {
				Messenger messenger = BossMessenger.MESSENGERS.get(name);
				if (messenger != null) {
					output.add(BossMessenger.MESSENGERS.get(name));
				}
			}
		}
		return output;
	}

	public static List<Messenger> getPlayerMessengers(Player player) {
		List<Messenger> output = getPlayerGroupMessengers(player);
		PrivateMessengerPool pool = getPrivateMessengerPool(player, false);
		if (pool != null) {
			for (Messenger messenger : pool.getMessengers()) {
				if (messenger.getPriority() != null) {
					output.add(messenger);
				}
			}
		}
		return output;
	}

	public static Map<UUID, Player> getPlayersForMessenger(String listName) {
		Map<UUID, Player> output = new HashMap<>();

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (GroupManager.hasAssignedPermission(player, Reference.PERMISSION_LIST + listName)) {
				output.put(player.getUniqueId(), player);
			}
		}
		return output;
	}

	public static void quit(Player player) {
		for (Messenger messenger : MessengerManager.getPlayerGroupMessengers(player)) {
			messenger.removePlayer(player);
		}
		PLAYER_MESSENGERS.remove(player.getUniqueId());
	}
}
