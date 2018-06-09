package net.pixelatedd3v.bossmessenger.ui.gui.dialogs;

import net.pixelatedd3v.bossmessenger.ui.gui.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ConfirmDialog extends ChestGUI {
	private static final List<GUIComponent> COMPONENTS = new ArrayList<>();

	private Runnable ok;
	private Runnable cancel;

	public ConfirmDialog(UISession session, Runnable ok, Runnable cancel, String okTxt, String cancelTxt, String... message) {
		super(session, "§4Confirmation", new ArrayList<>(COMPONENTS));
		List<GUIComponent> inv = getComponents();
		inv.add(new GUIComponent(ItemUtils.createStack(Material.BOOK, 1, "§aConfirm", GUIUtils.prefixArray("§b", message)), 13));
		this.ok = ok;
		this.cancel = cancel;

		ItemStack okItem = ItemUtils.createStack(Material.EMERALD_BLOCK, 1, "§a" + okTxt);
		inv.addAll(GUIUtils.duplicateComponents(okItem, 27, 28, 29, 36, 37, 38, 45, 46, 47));

		ItemStack cancelItem = ItemUtils.createStack(Material.REDSTONE_BLOCK, 1, "§4" + cancelTxt);
		inv.addAll(GUIUtils.duplicateComponents(cancelItem, 33, 34, 35, 42, 43, 44, 51, 52, 53));
	}

	public ConfirmDialog(UISession session, Runnable ok, Runnable cancel, String... message) {
		this(session, ok, cancel, "OK", "CANCEL", message);
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		switch (slot) {
			case 27:
			case 28:
			case 29:
			case 36:
			case 37:
			case 38:
			case 45:
			case 46:
			case 47:
				ok.run();
				break;
			case 33:
			case 34:
			case 35:
			case 42:
			case 43:
			case 44:
			case 51:
			case 52:
			case 53:
				cancel.run();
				break;
		}
	}

}
