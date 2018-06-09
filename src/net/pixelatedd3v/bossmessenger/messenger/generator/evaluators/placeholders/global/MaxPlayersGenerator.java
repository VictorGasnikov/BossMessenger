package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.global;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.GlobalPlaceholderEvaluator;
import org.bukkit.Bukkit;

public class MaxPlayersGenerator implements GlobalPlaceholderEvaluator {

	@Override
	public String evaluate(String text) {
		return Integer.toString(Bukkit.getMaxPlayers());
	}
}
