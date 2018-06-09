package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.global;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.GlobalPercentEvaluator;
import net.pixelatedd3v.bossmessenger.messenger.manager.MessageManager;
import org.bukkit.Bukkit;

public class ServerFullnessEvaluator implements GlobalPercentEvaluator {

	@Override
	public float evaluate(String text, String percent) {
		return ((float) MessageManager.getInstance().getVisiblePlayerCount()) / (float) Bukkit.getMaxPlayers() * 100;
	}
}
