package net.pixelatedd3v.bossmessenger.ui.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class ChestGUI extends GUI {
	private List<GUIComponent> components;
	private byte rows;
	private String title;
	private Inventory inventory;

	public ChestGUI(UISession session, String title, byte rows, List<GUIComponent> components) {
		super(session);
		this.title = title;
		this.rows = rows;
		this.components = new ArrayList<>(components);
	}

	public ChestGUI(UISession session, String title, List<GUIComponent> components) {
		this(session, title, (byte) 6, components);
	}

	public List<GUIComponent> getComponents() {
		return components;
	}

	public byte getRows() {
		return rows;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void open() {
		UISession session = getSession();
		Player player = session.getPlayer();
		inventory = Bukkit.createInventory(new GUIHolder(session), rows * 9, "§1" + getTitle());
		updateInventory();
		player.openInventory(inventory);
	}

	public void updateInventory() {
		for (GUIComponent component : getComponents()) {
			inventory.setItem(component.getSlot(), component.getItem());
		}
	}

	@Override
	public boolean onClose() {
		return true;
	}
}
