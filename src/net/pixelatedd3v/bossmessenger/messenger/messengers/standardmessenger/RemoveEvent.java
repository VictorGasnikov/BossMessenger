package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import org.bukkit.scheduler.BukkitRunnable;

public class RemoveEvent extends BukkitRunnable {
	private final StandardMessenger standardMessager;

	public RemoveEvent(StandardMessenger standardMessager) {
		this.standardMessager = standardMessager;
	}

	@Override
	public void run() {
		this.standardMessager.hasEvent = false;
		this.standardMessager.updateForAll();
	}
}