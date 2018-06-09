package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.global;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.GlobalPlaceholderEvaluator;
import org.bukkit.Bukkit;

public class ServerNameGenerator implements GlobalPlaceholderEvaluator {

	@Override
	public String evaluate(String text) {
		return Bukkit.getServerName();
	}

}
