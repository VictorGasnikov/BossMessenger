package net.pixelatedd3v.bossmessenger.messenger.messengers.modules;

import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.PlayerTimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.protocol.BossBar;
import org.bukkit.entity.Player;

public class StandardBossbarModule implements MessengerModule {

	@Override
	public void setMessage(PlayerTimedMessage message, Messenger messenger) {
		MessageAttribute pctAttr = message.getAttribute("Percent");
		Percent percent = pctAttr != null ? ((Percent) pctAttr.getValue()) : null;
		BossBar.setMessage(message.getPlayer(), message.getText(), percent != null ? percent.getFloatPercent() : 100F, messenger);
	}

	@Override
	public void removeMessage(Player player, Messenger messenger) {
		BossBar.removeBar(player, messenger);
	}

	@Override
	public String getName() {
		return "BossBar";
	}

	@Override
	public MessengerModuleType getType() {
		return MessengerModuleType.BOSSBAR;
	}

	@Override
	public boolean update() {
		return true;
	}
}
