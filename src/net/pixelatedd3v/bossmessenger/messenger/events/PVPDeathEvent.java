package net.pixelatedd3v.bossmessenger.messenger.events;

import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class PVPDeathEvent extends Event {

	public PVPDeathEvent(String name, boolean enabled, String text, int show, List<String> messengerNames, Collection<MessageAttribute> attributes) {
		super(name, enabled, text, show, messengerNames, attributes);
	}

	public void fire(Player victim, Player killer) {
		if (isEnabled()) {
			TimedMessage message = new TimedMessage(getText().replaceAll("(?i)%killer%", killer.getName()).replaceAll("(?i)%victim%", victim.getName()), getShow(), getAttributes().values());
			for (Messenger messenger : getMessengers()) {
				messenger.event(message);
			}
		}
	}
}
