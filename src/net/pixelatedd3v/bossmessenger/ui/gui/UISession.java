package net.pixelatedd3v.bossmessenger.ui.gui;

import org.bukkit.entity.Player;

public class UISession {
	private Player player;
	private UI current;
	private GUIHolder holder;
	private boolean isPlayerClose = true;

	public UISession(Player player) {
		this.player = player;
		this.holder = new GUIHolder(this);
	}

	public Player getPlayer() {
		return player;
	}

	public UI getCurrent() {
		return current;
	}

	public void openUI(UI ui) {
		if (current != null) {
			current.onLeave(false);
		}
		this.current = ui;
		isPlayerClose = false;
		current.open();
	}

	public GUIHolder getHolder() {
		return holder;
	}

	public void exit() {
		UIManager.stopSession(player, true);
	}

	public boolean isPlayerClose() {
		boolean output = isPlayerClose;
		isPlayerClose = true;
		return output;
	}
}
