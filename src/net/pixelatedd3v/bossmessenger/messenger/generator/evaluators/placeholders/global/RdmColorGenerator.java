package net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.global;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.generator.evaluators.placeholders.GlobalPlaceholderEvaluator;
import net.pixelatedd3v.bossmessenger.utils.RandomUtils;
import org.bukkit.ChatColor;

public class RdmColorGenerator implements GlobalPlaceholderEvaluator {

	@Override
	public String evaluate(String text) {
		int random = RandomUtils.randInt(Settings.RDM_COLORS.length());
		return ChatColor.COLOR_CHAR + Character.toString(Settings.RDM_COLORS.charAt(random));
	}
}
