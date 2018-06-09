package net.pixelatedd3v.bossmessenger.messenger.message;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Me on 2016-03-09.
 */
@SerializableAs("Attribute")
public class MessageAttribute implements ConfigurationSerializable {
	private String key;
	private Object value;

	public MessageAttribute(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public MessageAttribute(Map<String, Object> map) {
		this((String) map.get("Key"), map.get("Value"));
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = new HashMap<>();

		output.put("Key", key);
		output.put("Value", value);
		return output;
	}
}
