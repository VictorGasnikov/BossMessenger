package net.pixelatedd3v.bossmessenger.messenger.generator;

import net.pixelatedd3v.bossmessenger.api.events.EventManager;
import net.pixelatedd3v.bossmessenger.api.events.GlobalMessageEvent;
import net.pixelatedd3v.bossmessenger.api.events.MessagePriority;
import net.pixelatedd3v.bossmessenger.api.events.PlayerMessageEvent;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.events.Event;
import net.pixelatedd3v.bossmessenger.messenger.message.*;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class MessageGenerator {
	public static final String REGEX_ALL_CASE = "(?i)";

	private static final List<PlayerPlaceholder> PLAYER_PLACEHOLDERS = new ArrayList<>();
	private static final List<GlobalPlaceholder> GLOBAL_PLACEHOLDERS = new ArrayList<>();

	private static final Map<String, GlobalPercentVariable> GLOBAL_PERCENT_EVALUATORS = new HashMap<>();
	private static final Map<String, PlayerPercentVariable> PLAYER_PERCENT_EVALUATORS = new HashMap<>();

	public static GlobalTimedMessage generateGlobalMessage(TimedMessage message, Messenger messenger, MessagePriority priority) {
		String initText = message.getText();
		Map<String, MessageAttribute> attributes = new HashMap<>(message.getAttributes());
		MessageAttribute pctAttr = attributes.get("Percent");
		Percent initPercent = null;
		if (pctAttr != null) {
			initPercent = (Percent) attributes.get("Percent").getValue();
		}
		int show = message.getShow();
		if (message.evaluate()) {
			String text = generateGlobalText(message, messenger, priority);
			Percent percent = initPercent != null ? generateGlobalPercent(text, initPercent) : null;

			attributes.put("Percent", new MessageAttribute("Percent", percent));

			// GlobalMessageGenerateEvent bevent = new
			// GlobalMessageGenerateEvent(text, percent.getStringPercent());
			// EventManager.fire(event);
			//			GlobalMessageEvent event = new GlobalMessageEvent(text, percent.getStringPercent(), messenger.getModule().getType().getLocation(), messenger.getName(), priority);
			//			EventManager.callGlobalListeners(event);
			return new GlobalTimedMessage(text, show, true, attributes.values());
		} else {
			return new GlobalTimedMessage(initText, show, false, attributes.values());
		}
	}

	public static String generateGlobalText(Message message, Messenger messenger, MessagePriority priority) {
		String output = ChatColor.translateAlternateColorCodes('&', message.getText());
		//		if (Settings.USE_PLACEHOLDER_API) {
		//			if (PlaceholderAPI.containsPlaceholders(text)) {
		//				output = PlaceholderAPI.setPlaceholders(null, text);
		//			}
		//		}
		for (GlobalPlaceholder var : GLOBAL_PLACEHOLDERS) {
			output = var.replace(output);
		}

		GlobalMessageEvent event = new GlobalMessageEvent(message.getText(), messenger.getName(), priority);
		EventManager.callGlobalListeners(event);
		for (GlobalPlaceholder var : GLOBAL_PLACEHOLDERS) {
			output = var.replace(output);
		}

		return output;
	}

	public static Percent generateGlobalPercent(String text, Percent percent) {
		if (!percent.isConverted()) {
			String strPercent = percent.getStringPercent();
			GlobalPercentVariable percentage = GLOBAL_PERCENT_EVALUATORS.get(strPercent);
			if (percentage != null) {
				float fp = percentage.evaluate(text, percent.getStringPercent());
				return new Percent(fp);
			}
		}
		return percent;
	}

	public static PlayerTimedMessage generatePlayerMessage(GlobalTimedMessage message, Messenger messenger, Player player, MessagePriority priority) {
		String initText = message.getText();
		Map<String, MessageAttribute> attributes = new HashMap<>(message.getAttributes());
		Percent initPercent = (Percent) attributes.get("Percent").getValue();
		int show = message.getShow();
		if (message.evaluate()) {
			String text = generatePlayerText(message, messenger, priority, player);
			Percent percent = initPercent != null ? generatePlayerPercent(text, initPercent, player) : null;
			attributes.put("Percent", new MessageAttribute("Percent", percent));
			//			PlayerMessageGenerateEvent event = new PlayerMessageGenerateEvent(player, text, percent.getStringPercent());
			//			EventManager.fire(event);
			//			PlayerMessageEvent event = new PlayerMessageEvent(player, text, percent.getStringPercent(), messenger.getModule().getType().getLocation(), messenger.getName(), priority);
			//			EventManager.callPlayerListeners(event);
			return new PlayerTimedMessage(player, text, show, true, attributes.values());
		} else {
			return new PlayerTimedMessage(player, initText, show, false, attributes.values());
		}
	}

	public static String generatePlayerText(Message message, Messenger messenger, MessagePriority priority, Player player) {
		PlayerMessageEvent event = new PlayerMessageEvent(player, message.getText(), messenger.getName(), priority);
		EventManager.callPlayerListeners(event);
		String output = event.getText();
		if (Settings.USE_PLACEHOLDER_API) {
			if (PlaceholderAPI.containsPlaceholders(output)) {
				output = PlaceholderAPI.setPlaceholders(player, output);
			}
		}
		if (Settings.USE_MVDW_PLACEHOLDERS) {
			output = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, output);
		}
		for (PlayerPlaceholder var : PLAYER_PLACEHOLDERS) {
			output = var.replace(output, player);
		}

		return output;
	}

	public static Percent generatePlayerPercent(String text, Percent percent, Player player) {
		if (!percent.isConverted()) {
			String strPercent = percent.getStringPercent();
			PlayerPercentVariable percentage = PLAYER_PERCENT_EVALUATORS.get(strPercent);
			if (percentage != null) {
				float fp = percentage.evaluate(text, percent.getStringPercent(), player);
				return new Percent(fp);
			}
		}
		return percent;
	}

	public static void addGlobalPlaceholder(GlobalPlaceholder var) {
		GLOBAL_PLACEHOLDERS.add(var);
	}

	public static void addPlayerPlaceholder(PlayerPlaceholder var) {
		PLAYER_PLACEHOLDERS.add(var);
	}

	public static void addGlobalPercentEvaluator(GlobalPercentVariable percentage) {
		GLOBAL_PERCENT_EVALUATORS.put(percentage.getPercent(), percentage);
	}

	public static void addPlayerPercentEvaluator(PlayerPercentVariable percentage) {
		PLAYER_PERCENT_EVALUATORS.put(percentage.getPercent(), percentage);
	}
}