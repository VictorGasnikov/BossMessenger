package net.pixelatedd3v.bossmessenger.messenger.events;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Event extends TimedMessage {
	private boolean enabled;
	private String name;
	private List<Messenger> messengers;

	public Event(String name, boolean enabled, String text, int show, List<String> messengerNames, Collection<MessageAttribute> attributes) {
		super(text, show, attributes);
		this.name = name;
		this.enabled = enabled;
		List<Messenger> messengers = new ArrayList<>();
		for (String messengerName : messengerNames) {
			Messenger messenger = BossMessenger.MESSENGERS.get(messengerName);
			if (messenger != null) {
				messengers.add(messenger);
			}
		}
		this.messengers = messengers;
	}

	public Event(String name, boolean enabled, String text, int show, List<String> messengerNames, MessageAttribute... attributes) {
		this(name, enabled, text, show, messengerNames, Arrays.asList(attributes));
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public List<Messenger> getMessengers() {
		return messengers;
	}
}
