package net.pixelatedd3v.bossmessenger.messenger.message;

import java.util.Arrays;
import java.util.Collection;
public class TimedMessage extends Message {
	private int show;
	private boolean evaluate;

	public TimedMessage(String text, int show, boolean evaluate, Collection<MessageAttribute> attributes) {
		super(text, attributes);
		this.show = show;
		this.evaluate = evaluate;
	}

	public TimedMessage(String text, int show, Collection<MessageAttribute> attributes) {
		this(text, show, true, attributes);
	}

	public TimedMessage(String text, int show, MessageAttribute... attributes) {
		this(text, show, Arrays.asList(attributes));
	}

	public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}

	public boolean evaluate() {
		return evaluate;
	}
}
