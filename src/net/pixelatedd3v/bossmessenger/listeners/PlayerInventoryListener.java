package net.pixelatedd3v.bossmessenger.listeners;

import net.pixelatedd3v.bossmessenger.ui.gui.GUI;
import net.pixelatedd3v.bossmessenger.ui.gui.GUIHolder;
import net.pixelatedd3v.bossmessenger.ui.gui.UIManager;
import net.pixelatedd3v.bossmessenger.ui.gui.UISession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PlayerInventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();
		if (inv != null) {
			InventoryHolder holder = inv.getHolder();
			if (holder instanceof GUIHolder) {
				event.setCancelled(true);
				ClickType type = event.getClick();
				GUIHolder guiHolder = (GUIHolder) holder;
				UISession session = guiHolder.getSession();
				int slot = event.getSlot();

				if (inv.getType() == InventoryType.CHEST && slot >= 0 && slot < 54) {
					((GUI) session.getCurrent()).onClick(slot, type);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		UIManager.onGUIClose((Player) event.getPlayer());
	}
}
