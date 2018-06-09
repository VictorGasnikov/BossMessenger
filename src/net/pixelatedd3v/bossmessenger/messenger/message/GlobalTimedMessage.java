package net.pixelatedd3v.bossmessenger.messenger.message;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
public class GlobalTimedMessage extends TimedMessage {

	public GlobalTimedMessage(String text, int show, boolean evaluate, Collection<MessageAttribute> attributes) {
		super(text, show, evaluate, attributes);
	}

	public GlobalTimedMessage(String text, int show, boolean evaluate, MessageAttribute... attributes) {
		this(text, show, evaluate, Arrays.asList(attributes));
	}
}
