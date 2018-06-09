package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import org.bukkit.scheduler.BukkitRunnable;

public class RemoveBroadcast extends BukkitRunnable {
	private final StandardMessenger standardMessager;

	public RemoveBroadcast(StandardMessenger standardMessager) {
		this.standardMessager = standardMessager;
	}

	@Override
	public void run() {
		this.standardMessager.isBroadcasting = false;
		this.standardMessager.updateForAll();
	}
}