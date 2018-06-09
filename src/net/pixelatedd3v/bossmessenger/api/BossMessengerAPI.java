package net.pixelatedd3v.bossmessenger.api;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.api.events.EventManager;
import net.pixelatedd3v.bossmessenger.api.events.GlobalMessageListener;
import net.pixelatedd3v.bossmessenger.api.events.PlayerMessageListener;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;

import java.util.Arrays;

public class BossMessengerAPI {

	public BossMessengerAPI() {

	}

	public void display(String text, String percent, int show, boolean evaluate) {
		for (Messenger messenger : BossMessenger.MESSENGERS.values()) {
			messenger.display(new TimedMessage(text, show, evaluate, Arrays.asList(new MessageAttribute("Percent", new Percent(percent)))));
		}
	}

	public boolean display(String text, String percent, int show, String list, boolean evaluate) {
		Messenger messenger = BossMessenger.MESSENGERS.get(list);
		if (messenger != null) {
			messenger.display(new TimedMessage(text, show, evaluate, Arrays.asList(new MessageAttribute("Percent", new Percent(percent)))));
			return true;
		}
		return false;
	}

	public void registerGlobalMessageListener(GlobalMessageListener listener) {
		EventManager.registerGlobalListener(listener);
	}

	public void registerPlayerMessageListener(PlayerMessageListener listener) {
		EventManager.registerPlayerListener(listener);
	}

	// public void display(Player player, String text, String percent, int show)
	// {
	// MessengerManager.getPlayerPersonalMessenger(player).display(new
	// TimedMessage(text, new Percent(percent), show));
	// }
}
