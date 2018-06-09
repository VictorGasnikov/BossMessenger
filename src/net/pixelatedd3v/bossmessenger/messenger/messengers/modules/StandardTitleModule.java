package net.pixelatedd3v.bossmessenger.messenger.messengers.modules;

import net.pixelatedd3v.bossmessenger.messenger.message.PlayerTimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.protocol.Title;
import org.bukkit.entity.Player;

public class StandardTitleModule implements MessengerModule {

	@Override
	public void setMessage(PlayerTimedMessage message, Messenger messenger) {
		String[] text = message.getText().split("%sep%");
		Title.setMessage(message.getPlayer(), text[0], text.length > 1 ? text[1] : "", Integer.MAX_VALUE);
	}

	@Override
	public void removeMessage(Player player, Messenger messenger) {
		Title.removeBar(player);
	}

	@Override
	public String getName() {
		return "Title";
	}

	@Override
	public MessengerModuleType getType() {
		return MessengerModuleType.TITLE;
	}

	@Override
	public boolean update() {
		return true;
	}
}
