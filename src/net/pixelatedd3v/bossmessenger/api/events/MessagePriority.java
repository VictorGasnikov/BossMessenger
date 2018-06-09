package net.pixelatedd3v.bossmessenger.api.events;

public enum MessagePriority {
	AUTO(1),
	SCHEDULE(2),
	EVENT(3),
	BROADCAST(4),
	DISPLAY(5);

	private int priority;

	private MessagePriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public boolean isHigherOrEqual(MessagePriority priority) {
		if (priority == null) {
			return true;
		}
		return this.priority >= priority.getPriority();
	}

	public boolean isHigher(MessagePriority priority) {
		if (priority == null) {
			return true;
		}
		return this.priority > priority.getPriority();
	}
}
