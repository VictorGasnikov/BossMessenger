package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage;

import org.bukkit.entity.Player;

public interface PlayerPercentEvaluator {
	public float evaluate(String text, String percent, Player player);
}
