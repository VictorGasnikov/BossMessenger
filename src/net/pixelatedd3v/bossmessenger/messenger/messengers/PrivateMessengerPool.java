package net.pixelatedd3v.bossmessenger.messenger.messengers;

import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger.PlayerStandardMessenger;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PrivateMessengerPool {
	private Player player;
	private Map<MessengerModuleType, Messenger> messengers = new HashMap<>();

	public PrivateMessengerPool(Player player) {
		this.player = player;
	}

	public Messenger getMessenger(MessengerModuleType type, boolean create) {
		Messenger messenger = messengers.get(type);
		if (messenger != null) {
			return messenger;
		} else {
			return create ? createMessenger(type) : null;
		}
	}

	public Messenger createMessenger(MessengerModuleType type) {
		Messenger messenger = new PlayerStandardMessenger(player, type.getModule());
		messengers.put(type, messenger);
		return messenger;
	}

	public Collection<Messenger> getMessengers() {
		return messengers.values();
	}

	public Player getPlayer() {
		return player;
	}
}
