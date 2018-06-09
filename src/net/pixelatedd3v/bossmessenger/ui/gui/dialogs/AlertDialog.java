package net.pixelatedd3v.bossmessenger.ui.gui.dialogs;

import net.pixelatedd3v.bossmessenger.ui.gui.ChestGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.GUIComponent;
import net.pixelatedd3v.bossmessenger.ui.gui.UISession;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AlertDialog extends ChestGUI {
	private ItemStack item;
	private Runnable click;

	public AlertDialog(UISession session, String title, ItemStack item, Runnable click) {
		super(session, title, new ArrayList<GUIComponent>());
		this.item = item;
		this.click = click;
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		click.run();
	}

	@Override
	public void open() {
		UISession session = getSession();
		Inventory inv = Bukkit.createInventory(session.getHolder(), 54, getTitle());
		for (int slot = 0; slot < 54; slot++) {
			inv.setItem(slot, item);
		}
		session.getPlayer().openInventory(inv);
	}
}
