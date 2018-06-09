package net.pixelatedd3v.bossmessenger.listeners;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.protocol.BossBar;
import net.pixelatedd3v.bossmessenger.protocol.Tab;
import net.pixelatedd3v.bossmessenger.protocol.Title;
import net.pixelatedd3v.bossmessenger.ui.gui.UIManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayChangeWorldListener implements Listener {

	@EventHandler
	public void onEvent(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		if (Settings.WORLD_BLACKLIST_ENABLED && Settings.WORLD_BLACKLIST.contains(world.getName().toLowerCase())) {
			BossBar.removeAll(player);
			Tab.removeBar(player);
			Title.removeBar(player);
		}
	}
}
