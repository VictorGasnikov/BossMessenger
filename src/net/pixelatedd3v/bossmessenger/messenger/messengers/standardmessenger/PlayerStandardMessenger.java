package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import net.pixelatedd3v.bossmessenger.api.events.MessagePriority;
import net.pixelatedd3v.bossmessenger.messenger.generator.MessageGenerator;
import net.pixelatedd3v.bossmessenger.messenger.message.GlobalTimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerScope;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModule;
import org.bukkit.entity.Player;

public class PlayerStandardMessenger extends StandardMessenger {
	private Player player;

	public PlayerStandardMessenger(Player player, MessengerModule module) {
		super(null, module);
		this.scope = MessengerScope.PLAYER;
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void setMessage(GlobalTimedMessage message) {
		setMessageForPlayer(MessageGenerator.generatePlayerMessage(message, this, player, MessagePriority.BROADCAST), getPriority());
	}

	@Override
	public void removeMessage() {
		removeMessageForPlayer(player);
	}

	@Override
	public String getName() {
		return "player:" + player.getName();
	}

//	@Override
//	public void removeMessageForPlayer(Player player) {
//		for (Messenger messenger : MessengerManager.getPlayerGroupMessengers(player)) {
//			if (messenger.getModule().getType() == module.getType()) {
//				messenger.update(player);
//				return;
//			}
//		}
//		module.removeMessage(player, this);
//	}
}
