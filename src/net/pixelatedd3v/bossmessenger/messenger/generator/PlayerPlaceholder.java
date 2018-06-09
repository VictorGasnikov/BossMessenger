package net.pixelatedd3v.bossmessenger.messenger.generator;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.PlayerPlaceholderEvaluator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerPlaceholder {
	private boolean enabled = true;
	private Plugin plugin;
	private String replace;
	private PlayerPlaceholderEvaluator evaluator;
	private PlaceholderMode mode;

	public PlayerPlaceholder(Plugin plugin, String replace, PlayerPlaceholderEvaluator evaluator, PlaceholderMode mode) {
		this.plugin = plugin;
		this.replace = replace.toLowerCase();
		this.evaluator = evaluator;
		this.mode = mode;
	}

	public String getReplace() {
		return replace;
	}

	public PlayerPlaceholderEvaluator getGenerator() {
		return evaluator;
	}

	public PlaceholderMode getMode() {
		return mode;
	}

	public String replace(String text, Player player) {
		if (enabled) {
			if (text.contains(replace)) {
				String evaluated = evaluator.evaluate(text, player);
				switch (mode) {
					case REPLACE_ALL:
						text = text.replaceAll(replace, evaluated);
					case REPLACE_EACH:
						while (text.toLowerCase().contains(replace)) {
							text = text.replaceFirst(replace, evaluated);
						}
						break;
					case REPLACE_FIRST:
						text = text.replaceFirst(replace, evaluated);
					default:
						break;
				}
			}
		}
		return text;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Plugin getPlugin() {
		return plugin;
	}
}
