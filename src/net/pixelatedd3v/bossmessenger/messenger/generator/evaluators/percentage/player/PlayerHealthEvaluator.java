package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.player;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.PlayerPercentEvaluator;
import org.bukkit.entity.Player;

public class PlayerHealthEvaluator implements PlayerPercentEvaluator {

	@Override
	public float evaluate(String text, String percent, Player player) {
		return (float) (player.getHealth() * 5);
	}
}
