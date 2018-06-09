package net.pixelatedd3v.bossmessenger.messenger.generator;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.GlobalPercentEvaluator;
import org.bukkit.plugin.Plugin;

public class GlobalPercentVariable {
	private boolean enabled = true;
	private Plugin plugin;
	private String percent;
	private GlobalPercentEvaluator evaluator;

	public GlobalPercentVariable(Plugin plugin, String percent, GlobalPercentEvaluator evaluator) {
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

	public GlobalPercentEvaluator getEvaluator() {
		return evaluator;
	}

	public float evaluate(String text, String percent) {
		return evaluator.evaluate(text, percent);
	}
}
