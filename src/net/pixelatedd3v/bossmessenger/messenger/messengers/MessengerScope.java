package net.pixelatedd3v.bossmessenger.messenger.messengers;

public enum MessengerScope {
	GROUP(1),
	PLAYER(2);

	private int priority;

	private MessengerScope(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}
