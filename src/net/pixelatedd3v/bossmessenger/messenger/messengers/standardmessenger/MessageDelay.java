package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import org.bukkit.scheduler.BukkitRunnable;

public class MessageDelay extends BukkitRunnable {
	private final StandardMessenger standardMessager;
	private int interval;

	public MessageDelay(StandardMessenger standardMessager, int interval) {
		this.standardMessager = standardMessager;
		this.interval = interval;
	}

	@Override
	public void run() {
		this.standardMessager.isShowing = false;
		this.standardMessager.updateForAll();
		this.standardMessager.autoProcess = this.standardMessager.nextMessage().runTaskLater(BossMessenger.INSTANCE, interval);
	}
}