package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.api.events.MessagePriority;
import net.pixelatedd3v.bossmessenger.messenger.generator.MessageGenerator;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import org.bukkit.scheduler.BukkitRunnable;

public class MessageShow extends BukkitRunnable {
	private final StandardMessenger standardMessager;
	private TemplateMessage message;
	private boolean next;

	public MessageShow(StandardMessenger standardMessenger, TemplateMessage message, boolean next) {
		this.standardMessager = standardMessenger;
		this.next = next;
		//		if (message != null) {
		this.message = message;
		//		} else {
		//			this.message = StandardMessenger.NO_MESSAGE;
		//		}
	}

	@Override
	public void run() {
		if (message != null) {
			this.standardMessager.auto = MessageGenerator.generateGlobalMessage(message, standardMessager, MessagePriority.AUTO);
			int show = message.getShow();
			int interval = message.getInterval();
			this.standardMessager.isShowing = true;
			if (message.isAutoUpdated()) {
				this.standardMessager.autoAutoProcess = new DelayedMessageUpdater(standardMessager, message, MessagePriority.AUTO).getTask();
			} else {
				if (MessagePriority.AUTO.isHigherOrEqual(this.standardMessager.getPriority())) {
					this.standardMessager.setMessage(this.standardMessager.auto);
				}
				if (next) {
					if (interval > 0) {
						this.standardMessager.autoProcess = new MessageDelay(this.standardMessager, interval).runTaskLater(BossMessenger.INSTANCE, show);
					} else {
						this.standardMessager.autoProcess = this.standardMessager.nextMessage().runTaskLater(BossMessenger.INSTANCE, show);
					}
				}
			}
		} else {
			this.standardMessager.autoProcess = new MessageDelay(this.standardMessager, 100).runTask(BossMessenger.INSTANCE);
		}
	}
}