package net.pixelatedd3v.bossmessenger.messenger.message;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlayerTimedMessage extends GlobalTimedMessage {
	private Player player;

	public PlayerTimedMessage(Player player, String text, int show, boolean evaluate, Collection<MessageAttribute> attributes) {
		super(text, show, evaluate, attributes);
		this.player = player;
	}

	public PlayerTimedMessage(Player player, String text, int show, boolean evaluate, MessageAttribute... attributes) {
		this(player, text, show, evaluate, Arrays.asList(attributes));
	}

	public Player getPlayer() {
		return player;
	}
}
