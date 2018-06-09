package net.pixelatedd3v.bossmessenger.ui.gui.dialogs;

import net.pixelatedd3v.bossmessenger.ui.gui.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class YesNoCancelDialog extends ChestGUI {
	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable yes;
	private Runnable no;
	private Runnable cancel;

	public YesNoCancelDialog(UISession session, Runnable yes, Runnable no, Runnable cancel, String yesTxt, String noTxt, String cancelTxt, String... message) {
		super(session, "§4Confirmation", new ArrayList<GUIComponent>(COMPONENTS));
		List<GUIComponent> inv = getComponents();
		ItemStack yesItem = ItemUtils.createStack(Material.EMERALD_BLOCK, 1, "§a" + yesTxt);
		inv.addAll(GUIUtils.duplicateComponents(yesItem, 27, 28, 29, 36, 37, 38, 45, 46, 47));
		ItemStack noItem = ItemUtils.createStack(Material.EMERALD_BLOCK, 1, "§e" + noTxt);
		inv.addAll(GUIUtils.duplicateComponents(noItem, 30, 31, 32, 39, 40, 41, 48, 49, 50));
		ItemStack cancelItem = ItemUtils.createStack(Material.REDSTONE_BLOCK, 1, "§4" + cancelTxt);
		inv.addAll(GUIUtils.duplicateComponents(cancelItem, 33, 34, 35, 42, 43, 44, 51, 52, 53));

		inv.add(new GUIComponent(ItemUtils.createStack(Material.BOOK, 1, "§aConfirm", GUIUtils.prefixArray("§b", message)), 13));
		this.yes = yes;
		this.no = no;
		this.cancel = cancel;
	}

	public YesNoCancelDialog(UISession session, Runnable yes, Runnable no, Runnable cancel, String... message) {
		this(session, yes, no, cancel, "YES", "NO", "CANCEL", message);
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
				yes.run();
				break;
			case 30:
			case 31:
			case 32:
			case 39:
			case 40:
			case 41:
			case 48:
			case 49:
			case 50:
				no.run();
				break;
			case 33:
			case 24:
			case 25:
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
