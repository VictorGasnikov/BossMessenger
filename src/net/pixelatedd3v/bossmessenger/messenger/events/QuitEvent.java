package net.pixelatedd3v.bossmessenger.messenger.events;

import net.pixelatedd3v.bossmessenger.messenger.manager.VnpManager;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class QuitEvent extends Event {

	public QuitEvent(String name, boolean enabled, String text, int show, List<String> messengerNames, Collection<MessageAttribute> attributes) {
		super(name, enabled, text, show, messengerNames, attributes);
	}

	public void fire(Player player) {
		if (isEnabled() && VnpManager.getInstance().isVisible(player)) {
			TimedMessage message = new TimedMessage(getText().replaceAll("(?i)%target%", player.getName()), getShow(), getAttributes().values());
			for (Messenger messenger : getMessengers()) {
				messenger.event(message);
			}
		}
	}
}
