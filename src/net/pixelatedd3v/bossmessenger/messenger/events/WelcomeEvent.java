package net.pixelatedd3v.bossmessenger.messenger.events;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.messenger.messengers.PrivateMessengerPool;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class WelcomeEvent extends Event {

	public WelcomeEvent(String name, boolean enabled, String text, int show, List<String> messengerNames, Collection<MessageAttribute> attributes) {
		super(name, enabled, text, show, messengerNames, attributes);

	}

	public void fire(Player player) {
		if (isEnabled()) {
			TimedMessage message = new TimedMessage(getText(), getShow(), getAttributes().values());
			PrivateMessengerPool pool = MessengerManager.getPrivateMessengerPool(player, true);
			Messenger messenger = pool.getMessenger(Settings.WELCOME_MODULE_TYPE, true);
			messenger.broadcast(message);
		}
	}
}