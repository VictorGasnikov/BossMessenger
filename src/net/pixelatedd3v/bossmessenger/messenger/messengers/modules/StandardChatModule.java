package net.pixelatedd3v.bossmessenger.messenger.messengers.modules;

import net.pixelatedd3v.bossmessenger.messenger.message.PlayerTimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import org.bukkit.entity.Player;

public class StandardChatModule implements MessengerModule {

	@Override
	public void setMessage(PlayerTimedMessage message, Messenger messenger) {
		message.getPlayer().sendMessage(message.getText().split("%nl%"));
	}

	@Override
	public void removeMessage(Player player, Messenger messenger) {

	}

	@Override
	public String getName() {
		return "Chat";
	}

	@Override
	public MessengerModuleType getType() {
		return MessengerModuleType.CHAT;
	}

	@Override
	public boolean update() {
		return false;
	}
}
