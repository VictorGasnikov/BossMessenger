package net.pixelatedd3v.bossmessenger.api.events;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
	private static List<GlobalMessageListener> globalListeners = new ArrayList<>();
	private static List<PlayerMessageListener> playerListeners = new ArrayList<>();

	public static void callGlobalListeners(GlobalMessageEvent event) {
		for (GlobalMessageListener listener : globalListeners) {
			listener.onMessageEvaluation(event);
		}
	}

	public static void callPlayerListeners(PlayerMessageEvent event) {
		for (PlayerMessageListener listener : playerListeners) {
			listener.onMessageEvaluation(event);
		}
	}

	public static void registerGlobalListener(GlobalMessageListener listener) {
		globalListeners.add(listener);
	}

	public static void registerPlayerListener(PlayerMessageListener listener) {
		playerListeners.add(listener);
	}
}
