package net.pixelatedd3v.bossmessenger.ui.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GUIHolder implements InventoryHolder {
	private UISession session;

	public GUIHolder(UISession session) {
		this.session = session;
	}

	@Override
	public Inventory getInventory() {
		return null;
	}

	public UISession getSession() {
		return session;
	}
}
