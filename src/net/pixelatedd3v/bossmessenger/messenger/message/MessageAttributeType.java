package net.pixelatedd3v.bossmessenger.messenger.message;

/**
 * Created by Me on 2016-03-21.
 */
public enum MessageAttributeType {
	PERCENT("Percent"),
	BAR_COLOR("BarColor"),
	BAR_FRAGMENTS("BarFragments");

	private String key;

	MessageAttributeType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
