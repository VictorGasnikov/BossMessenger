package net.pixelatedd3v.bossmessenger.messenger.message;

import java.util.Arrays;
import java.util.Collection;
public class GlobalMessage extends Message {

	public GlobalMessage(String text, Collection<MessageAttribute> attributes) {
		super(text, attributes);
	}

	public GlobalMessage(String text, MessageAttribute... attributes) {
		super(text, Arrays.asList(attributes));
	}
}
