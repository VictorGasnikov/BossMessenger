package net.pixelatedd3v.bossmessenger.ui.gui;

import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.MainGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.user.UserGUI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UIManager {
	public static final Map<UUID, UISession> SESSIONS = new HashMap<>();
	
	public static UISession startAdminSession(Player player) {
		UISession session = new UISession(player);
		session.openUI(new MainGUI(session));
		return SESSIONS.put(player.getUniqueId(), session);
	}
	
	public static UISession startUserSession(Player player) {
		UISession session = new UISession(player);
		session.openUI(new UserGUI(session));
		return SESSIONS.put(player.getUniqueId(), session);
	}
	
	public static void stopSession(Player player, boolean close) {
		SESSIONS.remove(player.getUniqueId());
		if (close) {
			if (player.isOnline()) {
				player.closeInventory();
			}
		}
	}
	
	public static void onGUIClose(Player player) {
		UUID uuid = player.getUniqueId();
		UISession session = SESSIONS.get(uuid);
		if (session != null && session.isPlayerClose()) {
			UI ui = session.getCurrent();
			ui.onLeave(true);
			if (ui.getType() == UIType.GUI) {
				if (((GUI) session.getCurrent()).onClose()) {
					SESSIONS.remove(uuid);
				}
			}
		}
	}
	
	public static boolean onChat(Player player, String message) {
		UUID uuid = player.getUniqueId();
		UISession session = UIManager.SESSIONS.get(uuid);
		if (session != null) {
			UI ui = session.getCurrent();
			if (ui.getType() == UIType.CHAT) {
				ChatUI chatUI = (ChatUI) ui;
				chatUI.onChat(message);
				return true;
			}
		}
		return false;
	}
	
	public static boolean chatCancel(Player player) {
		UISession session = SESSIONS.get(player.getUniqueId());
		if (session != null) {
			UI ui = session.getCurrent();
			if (ui.getType() == UIType.CHAT) {
				ChatUI cui = (ChatUI) ui;
				cui.onCancel();
				return true;
			}
		}
		return false;
	}
}
