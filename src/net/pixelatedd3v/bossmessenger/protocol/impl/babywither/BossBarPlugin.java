package net.pixelatedd3v.bossmessenger.protocol.impl.babywither;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarPlugin implements Listener {

	public static BossBarPlugin instance;
	public static int updaterTask;
	public static Runnable updater = new BukkitRunnable() {
		public void run() {
			for (BossBarHack bar : OldBossBarAPI.getBossBars()) {
				bar.updateMovement();
			}
		}
	};

	public BossBarPlugin() {
		BossBarPlugin.instance = this;
		Bukkit.getPluginManager().registerEvents(this, BossMessenger.INSTANCE);
		startUpdater();
	}

	public void onDisable() {
		for (BossBarHack bar : OldBossBarAPI.getBossBars()) {
			bar.destroy();
		}
		OldBossBarAPI.getBossBars().clear();
		stopUpdater();
	}

	public static void stopUpdater() {
		Bukkit.getScheduler().cancelTask(updaterTask);
	}

	public static void startUpdater() {
		updaterTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(BossMessenger.INSTANCE, updater, Settings.PROTOCOL_UPDATE_INTERVAL, Settings.PROTOCOL_UPDATE_INTERVAL);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(final PlayerQuitEvent e) {
		OldBossBarAPI.removeBar(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onKick(final PlayerKickEvent e) {
		OldBossBarAPI.removeBar(e.getPlayer());
	}
	//
	////	@EventHandler(priority = EventPriority.MONITOR)
	//	public void onTeleport(final PlayerTeleportEvent e) {
	//		this.handlePlayerTeleport(e.getPlayer(), e.getFrom(), e.getTo());
	//	}
	//
	////	@EventHandler(priority = EventPriority.MONITOR)
	//	public void onRespawn(final PlayerRespawnEvent e) {
	//		this.handlePlayerTeleport(e.getPlayer(), e.getPlayer().getLocation(), e.getRespawnLocation());
	//	}
	//
	//	protected void handlePlayerTeleport(final Player player, final Location from, final Location to) {
	//		if (!BossBarAPI.hasBar(player)) {
	//			return;
	//		}
	//		final BossBarHack bar = BossBarAPI.getBossBar(player);
	//		bar.setVisible(false);
	//		new BukkitRunnable() {
	//			public void run() {
	//				bar.setVisible(true);
	//			}
	//		}.runTaskLater(BossMessenger.INSTANCE, 20L);
	//	}
}
