package net.pixelatedd3v.bossmessenger.listeners;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.GroupManager;
import net.pixelatedd3v.bossmessenger.Reference;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.LangUtils;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onEvent(PlayerJoinEvent event) {
		if (Settings.CANCEL_DEFAULT_JOIN_MESSAGE) {
			event.setJoinMessage(null);
		}
		final Player player = event.getPlayer();
		if (Settings.UPDATE_AVAILABLE && GroupManager.hasPermission(player, Reference.PERMISSION_UPDATE_NOTIFICATION)) {
			LangUtils.sendUpdateNotification(player);
		}
		for (Messenger messenger : MessengerManager.getPlayerGroupMessengers(player)) {
			messenger.addPlayer(player);
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				Settings.PLAYER_JOIN_EVENT.fire(player);
				Settings.PLAYER_WELCOME_EVENT.fire(player);
			}
		}.runTaskLater(BossMessenger.INSTANCE, 1L);
	}
}
