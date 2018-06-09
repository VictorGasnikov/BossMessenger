package net.pixelatedd3v.bossmessenger.messenger.messengers.modules;

import net.pixelatedd3v.bossmessenger.messenger.message.PlayerTimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import org.bukkit.entity.Player;

public interface MessengerModule {

	public void setMessage(PlayerTimedMessage message, Messenger messenger);

	public void removeMessage(Player player, Messenger messenger);

	public String getName();

	public MessengerModuleType getType();

	public boolean update();
}
