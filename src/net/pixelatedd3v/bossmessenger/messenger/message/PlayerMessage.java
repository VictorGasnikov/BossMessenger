package net.pixelatedd3v.bossmessenger.messenger.message;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlayerMessage extends GlobalMessage {
	private Player player;

	public PlayerMessage(String text, Player player, Collection<MessageAttribute> attributes) {
		super(text, attributes);
		this.player = player;
	}
	public PlayerMessage(String text, Player player, MessageAttribute... attributes) {
		this(text, player, Arrays.asList(attributes));
	}

	public Player getPlayer() {
		return player;
	}
}
