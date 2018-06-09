package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders;

import org.bukkit.entity.Player;

public interface PlayerPlaceholderEvaluator {
	public String evaluate(String text, Player player);
}
