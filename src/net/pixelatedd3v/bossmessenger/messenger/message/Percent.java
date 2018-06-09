package net.pixelatedd3v.bossmessenger.messenger.message;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;
@SerializableAs("Percent")
public class Percent implements ConfigurationSerializable {
	private String stringPercent;
	private boolean converted;
	private float floatPercent;

	public Percent(String percent) {
		this.stringPercent = percent;
		this.converted = false;
	}

	public Percent(float percent) {
		this.floatPercent = percent;
		this.converted = true;
	}

	public Percent(Map<String, Object> map) {
		this((String) map.get("Percent"));
	}

	public float getFloatPercent() {
		if (!converted) {
			convert();
		}
		return floatPercent;
	}

	private void convert() {
		try {
			convert(Float.parseFloat(stringPercent));
		} catch (NumberFormatException e) {
			convert(-1);
		}
	}

	public int getIntPercent() {
		if (!converted) {
			convert();
		}
		return (int) floatPercent;
	}

	public void setFloatPercent(float floatPercent) {
		this.floatPercent = floatPercent;
	}

	public String getStringPercent() {
		if (stringPercent == null) {
			stringPercent = Integer.toString((int) floatPercent);
		}
		return stringPercent;
	}

	public boolean isConverted() {
		return converted;
	}

	public void convert(float percent) {
		this.converted = true;
		this.floatPercent = percent;
	}

	public boolean isAuto() {
		return getStringPercent().equals("auto");
	}

	@Override
	public String toString() {
		return getStringPercent();
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = new HashMap<>();
		output.put("Percent", getStringPercent());
		return output;
	}
}
