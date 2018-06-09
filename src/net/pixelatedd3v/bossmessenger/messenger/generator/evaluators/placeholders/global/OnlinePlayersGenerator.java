package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.global;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.GlobalPlaceholderEvaluator;
import net.pixelatedd3v.bossmessenger.messenger.manager.MessageManager;
import net.pixelatedd3v.bossmessenger.messenger.manager.VnpManager;
import org.bukkit.Bukkit;

public class OnlinePlayersGenerator implements GlobalPlaceholderEvaluator {
	
	@Override
	public String evaluate(String text) {
		return Integer.toString(MessageManager.getInstance().getVisiblePlayerCount());
	}
}
