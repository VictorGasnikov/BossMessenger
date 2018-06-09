package net.pixelatedd3v.bossmessenger.api.events;

public class GlobalMessageEvent {
	private String text;
	private String messenger;
	private MessagePriority priority;

	public GlobalMessageEvent(String text, String messenger, MessagePriority priority) {
		this.text = text;
		this.messenger = messenger;
		this.priority = priority;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMessenger() {
		return messenger;
	}

	public MessagePriority getPriority() {
		return priority;
	}
}
