package net.pixelatedd3v.bossmessenger.ui.gui;

import org.bukkit.inventory.ItemStack;

public class GUIComponent {
	private ItemStack item;
	private int slot;

	public GUIComponent(ItemStack item, int slot) {
		this.item = item;
		this.slot = slot;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

}
