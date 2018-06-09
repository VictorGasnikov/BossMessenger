package net.pixelatedd3v.bossmessenger.ui.gui;

import org.bukkit.event.inventory.ClickType;

public abstract class GUI implements UI {
	private UISession session;

	public GUI(UISession session) {
		this.session = session;
	}

	@Override
	public UIType getType() {
		return UIType.GUI;
	}

	@Override
	public UISession getSession() {
		return session;
	}

	public abstract void onClick(int slot, ClickType type, Object... params);

	public abstract boolean onClose();

	@Override
	public void onLeave(boolean isPlayerClose) {

	}
}
