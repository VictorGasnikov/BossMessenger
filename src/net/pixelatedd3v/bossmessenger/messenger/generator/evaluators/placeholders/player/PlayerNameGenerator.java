package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.player;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.PlayerPlaceholderEvaluator;
import org.bukkit.entity.Player;

public class PlayerNameGenerator implements PlayerPlaceholderEvaluator {

	@Override
	public String evaluate(String text, Player player) {
		return player.getName();
	}

}
