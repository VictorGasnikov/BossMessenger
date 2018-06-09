package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.global;

import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.percentage.GlobalPercentEvaluator;
import net.pixelatedd3v.bossmessenger.utils.RandomUtils;

public class RandomPercentEvaluator implements GlobalPercentEvaluator {

	@Override
	public float evaluate(String text, String percent) {
		return RandomUtils.randInt(100);
	}
}
