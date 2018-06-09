package net.pixelatedd3v.bossmessenger.messenger.task;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger.FinishSchedule;
import net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger.StandardMessenger;

public class ScheduledTask {
	private Messenger messenger;
	private TimedMessage message;
	private long time;
	private boolean running = true;

	public ScheduledTask(Messenger messenger, TimedMessage message) {
		this.messenger = messenger;
		this.message = message;
		this.time = System.currentTimeMillis();
	}

	public long getTime() {
		return time;
	}

	public Messenger getMessenger() {
		return messenger;
	}

	public TimedMessage getMessage() {
		return message;
	}

	public void cancel() {
		if (messenger != null) {
			messenger.cancelScheduledTask();
			if (messenger instanceof StandardMessenger) {
				new FinishSchedule((StandardMessenger) messenger, null).run();
			}
		} else {
			if (MessengerManager.TASK != null) {
				MessengerManager.TASK.cancel();
				new FinishSchedule(null, MessengerManager.SCHEDULE_COMMANDS).run();
			}
			MessengerManager.GLOBALTASK = null;
			for (Messenger messenger : BossMessenger.MESSENGERS.values()) {
				messenger.cancelScheduledTask();
				if (messenger instanceof StandardMessenger) {
					new FinishSchedule((StandardMessenger) messenger, null).run();
				}
			}
		}
	}

	public boolean isRunning() {
		return running;
	}
}
