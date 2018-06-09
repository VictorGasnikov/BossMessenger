package net.pixelatedd3v.bossmessenger.messenger.messengers.modules;

import net.pixelatedd3v.bossmessenger.messenger.message.PlayerTimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.protocol.ActionBar;
import org.bukkit.entity.Player;

public class StandardActionbarModule implements MessengerModule {

	@Override
	public void setMessage(PlayerTimedMessage message, Messenger messenger) {
		ActionBar.setMessage(message.getPlayer(), message.getText());
	}

	@Override
	public void removeMessage(Player player, Messenger messenger) {
		ActionBar.removeBar(player);
	}

	@Override
	public String getName() {
		return "ActionBar";
	}

	@Override
	public MessengerModuleType getType() {
		return MessengerModuleType.ACTIONBAR;
	}

	@Override
	public boolean update() {
		return true;
	}
}
