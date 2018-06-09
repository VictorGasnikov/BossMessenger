package net.pixelatedd3v.bossmessenger.listeners;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.ui.gui.UIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onEvent(PlayerQuitEvent event) {
		if (Settings.CANCEL_DEFAULT_QUIT_MESSAGE) {
			event.setQuitMessage(null);
		}
		final Player player = event.getPlayer();
		MessengerManager.quit(player);
		Settings.PLAYER_QUIT_EVENT.fire(player);
		UIManager.stopSession(player, true);
	}
}