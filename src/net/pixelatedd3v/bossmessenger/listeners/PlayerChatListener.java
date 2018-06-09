package net.pixelatedd3v.bossmessenger.listeners;

import net.pixelatedd3v.bossmessenger.ui.gui.UIManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

	@EventHandler
	public void onEvent(AsyncPlayerChatEvent event) {
		if (UIManager.onChat(event.getPlayer(), event.getMessage())) {
			event.setCancelled(true);
		}
	}
}
