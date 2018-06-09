package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.api.events.MessagePriority;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.utils.MessageUtils;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DelayedMessageUpdater extends BukkitRunnable {
	private final StandardMessenger standardMessenger;
	private MessagePriority priority;
	private TimedMessage message;
	private boolean reduce;
	private BukkitTask task;
	private int left;
	private boolean hasPercent;
	private float decrease;
	private float percent = 100;

	public DelayedMessageUpdater(StandardMessenger standardMessenger, TimedMessage message, MessagePriority priority) {
		this.standardMessenger = standardMessenger;
		this.message = message;
		this.priority = priority;
		Percent pct = message.getPercent();
		left = Math.round(message.getShow() / 20);
		if (left == 0) {
			left = 1;
		}
		if (pct != null) {
			this.reduce = pct.isAuto();
			if (reduce) {
				decrease = 100F / left;
			} else {
				percent = pct.getFloatPercent();
			}
		}
		task = runTaskTimer(BossMessenger.INSTANCE, 0, 20);
	}

	public BukkitTask getTask() {
		return task;
	}

	@Override
	public void run() {
		if (left > 0) {
			boolean removeAfter = left == 1;
			String sText = message.getText().replaceAll("%sec%", Integer.toString(left));
			if (sText.contains("%timer%")) {
				sText = sText.replaceAll("%timer%", MessageUtils.getTimer(left));
			}
			Percent sPercent = new Percent(percent);
			List<MessageAttribute> attributes = Arrays.asList(new MessageAttribute("Percent", sPercent));
			int sShow = 20;

			switch (priority) {
				case AUTO:
					int interval = removeAfter ? ((TemplateMessage) message).getInterval() : 0;
					TemplateMessage autoMessage = new TemplateMessage(sText, sShow, interval, attributes);
					this.standardMessenger.autoProcess = new MessageShow(this.standardMessenger, autoMessage, removeAfter).runTask(BossMessenger.INSTANCE);
					break;
				case SCHEDULE:
					TimedMessage scheduleMessage = new TimedMessage(sText, sShow, true, attributes);
					if (removeAfter) {
						this.standardMessenger.schedule(scheduleMessage, this.standardMessenger.scheduleCommands);
					} else {
						this.standardMessenger.schedule(scheduleMessage);
					}
					break;
				case EVENT:
					this.standardMessenger.event(new TimedMessage(sText, sShow, true, attributes), removeAfter);
					break;
				case BROADCAST:
					this.standardMessenger.broadcast(new TimedMessage(sText, sShow, true, attributes), removeAfter);
					break;
				case DISPLAY:
					this.standardMessenger.display(new TimedMessage(sText, sShow, true, attributes), removeAfter);
					break;

				default:
					break;
			}
			left--;
			if (reduce) {
				percent -= decrease;
			}
		} else {
			cancel();
		}
	}

}