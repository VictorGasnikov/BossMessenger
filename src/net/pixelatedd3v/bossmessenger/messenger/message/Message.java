package net.pixelatedd3v.bossmessenger.messenger.message;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Message {
	private String text;
	private Map<String, MessageAttribute> attributes = new HashMap<>();

	public Message(String text, Collection<MessageAttribute> attributes) {
		this.text = text;
		if (attributes != null) {
			for (MessageAttribute attribute : attributes) {
				this.attributes.put(attribute.getKey(), attribute);
			}
		}
	}

	public Message(String text, MessageAttribute... attributes) {
		this(text, Arrays.asList(attributes));
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TemplateMessage clone() {
		return this.clone();
	}

	public MessageAttribute getAttribute(String key) {
		return attributes.get(key);
	}

	public void setAttribute(MessageAttribute attribute) {
		attributes.put(attribute.getKey(), attribute);
	}

	public Map<String, MessageAttribute> getAttributes() {
		return attributes;
	}


	public Percent getPercent() {
		MessageAttribute attribute = getAttribute("Percent");
		if (attribute != null) {
			Object pct = attribute.getValue();
			if (pct.getClass().equals(Percent.class)) {
				return (Percent) pct;
			}
		}
		return null;
	}

	public void setPercent(Percent percent) {
		setAttribute(new MessageAttribute("Percent", percent));
	}

	public boolean isAutoUpdated() {
		Percent percent = getPercent();
		return (percent != null && percent.isAuto()) || getText().contains("%sec%") || getText().contains("%timer%");
	}

	@Override
	public String toString() {
		return text;
	}
}
