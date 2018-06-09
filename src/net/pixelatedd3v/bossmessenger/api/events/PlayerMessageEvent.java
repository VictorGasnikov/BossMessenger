package net.pixelatedd3v.bossmessenger.api.events;

import org.bukkit.entity.Player;

public class PlayerMessageEvent extends GlobalMessageEvent {
	private Player player;

	public PlayerMessageEvent(Player player, String text, String messenger, MessagePriority priority) {
		super(text, messenger, priority);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
