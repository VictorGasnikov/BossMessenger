package net.pixelatedd3v.bossmessenger.messenger.generator;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.GlobalPlaceholderEvaluator;

public class GlobalPlaceholder {
	private boolean enabled = true;
	private String replace;
	private GlobalPlaceholderEvaluator evaluator;
	private PlaceholderMode mode;

	public GlobalPlaceholder(String replace, GlobalPlaceholderEvaluator evaluator, PlaceholderMode mode) {
		this.replace = replace.toLowerCase();
		this.evaluator = evaluator;
		this.mode = mode;
	}

	public String getReplace() {
		return replace;
	}

	public GlobalPlaceholderEvaluator getGenerator() {
		return evaluator;
	}

	public PlaceholderMode getMode() {
		return mode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String replace(String text) {
		if (enabled) {
			if (text.contains(replace)) {
				String evaluated = evaluator.evaluate(text);
				switch (mode) {
					case REPLACE_ALL:
						return text.replaceAll(replace, evaluated);
					case REPLACE_EACH:
						while (text.toLowerCase().contains(replace)) {
							text = text.replaceFirst(replace, evaluated);
						}
						break;
					case REPLACE_FIRST:
						return text.replaceFirst(replace, evaluated);
					default:
						break;
				}
			}
		}
		return text;
	}
}
