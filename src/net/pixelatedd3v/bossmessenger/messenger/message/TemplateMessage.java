package net.pixelatedd3v.bossmessenger.messenger.message;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.*;

@SerializableAs("Message")
public class TemplateMessage extends TimedMessage implements ConfigurationSerializable, Cloneable {
	private int interval;

	public TemplateMessage(String message, int show, int interval, Collection<MessageAttribute> attributes) {
		super(message, show, true, attributes);
		this.interval = interval;
	}

	public TemplateMessage(String message, int show, int interval, MessageAttribute... attributes) {
		this(message, show, interval, Arrays.asList(attributes));
	}

	public TemplateMessage(Map<String, Object> from) {
		this((String) from.get("Text"), (int) from.get("Show"), (int) from.get("Interval"), (Collection<MessageAttribute>) from.get("Attributes"));
		String percent = (String) from.get("Percent");
		if (percent != null) {
			setPercent(new Percent(percent));
		}
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = new HashMap<>();

		output.put("Text", getText());
		//		output.put("Percent", getPercent().getStringPercent());
		output.put("Show", getShow());
		output.put("Interval", interval);
		output.put("Attributes", new ArrayList<>(getAttributes().values()));
		return output;
	}
}
