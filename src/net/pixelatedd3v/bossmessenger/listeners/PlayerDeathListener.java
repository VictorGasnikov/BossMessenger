package net.pixelatedd3v.bossmessenger.listeners;

import net.pixelatedd3v.bossmessenger.config.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onEvent(PlayerDeathEvent event) {
		if (Settings.CANCEL_DEFAULT_DEATH_MESSAGE) {
			event.setDeathMessage(null);
		}
		Player victim = event.getEntity();
		Player killer = victim.getKiller();
		if (killer != null) {
			Settings.PLAYER_PVP_DEATH_EVENT.fire(victim, killer);
		}
	}
}
