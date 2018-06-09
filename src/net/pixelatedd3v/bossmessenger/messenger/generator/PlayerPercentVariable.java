package net.pixelatedd3v.bossmessenger.messenger.generator;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.PlayerPercentEvaluator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerPercentVariable {
	private boolean enabled = true;
	private Plugin plugin;
	private String percent;
	private PlayerPercentEvaluator evaluator;

	public PlayerPercentVariable(Plugin plugin, String percent, PlayerPercentEvaluator evaluator) {
		this.plugin = plugin;
		this.percent = percent;
		this.evaluator = evaluator;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public String getPercent() {
		return percent;
	}

	public PlayerPercentEvaluator getEvaluator() {
		return evaluator;
	}

	public float evaluate(String text, String percent, Player player) {
		return evaluator.evaluate(text, percent, player);
	}
}
