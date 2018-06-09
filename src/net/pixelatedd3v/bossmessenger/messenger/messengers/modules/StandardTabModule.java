package net.pixelatedd3v.bossmessenger.messenger.messengers.modules;

import net.pixelatedd3v.bossmessenger.messenger.message.PlayerTimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.protocol.Tab;
import org.bukkit.entity.Player;

public class StandardTabModule implements MessengerModule {

	@Override
	public void setMessage(PlayerTimedMessage message, Messenger messenger) {
		String[] text = message.getText().replace("%nl%", "\n").split("%sep%");
		Tab.setMessage(message.getPlayer(), text[0], text.length > 1 ? text[1] : "");
	}

	@Override
	public void removeMessage(Player player, Messenger messenger) {
		Tab.removeBar(player);
	}

	@Override
	public String getName() {
		return "Tab";
	}

	@Override
	public MessengerModuleType getType() {
		return MessengerModuleType.TAB;
	}

	@Override
	public boolean update() {
		return true;
	}
}
