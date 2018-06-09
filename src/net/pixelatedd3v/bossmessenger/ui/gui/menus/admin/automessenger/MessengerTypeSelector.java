package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger;

import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public abstract class MessengerTypeSelector extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable back;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Select the new messaging location."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 1, "§f"), 18, 19, 25, 26));
	}

	public MessengerTypeSelector(UISession session, MessengerModuleType selected, Runnable back) {
		super(session, "BossMessenger Controller", (byte) 3, COMPONENTS);
		this.back = back;
		List<GUIComponent> inv = getComponents();
		inv.add(GUIUtils.getModuleSelectorComponent(MessengerModuleDescriptor.CHAT, 20, selected));
		inv.add(GUIUtils.getModuleSelectorComponent(MessengerModuleDescriptor.BOSSBAR, 21, selected));
		inv.add(GUIUtils.getModuleSelectorComponent(MessengerModuleDescriptor.ACTIONBAR, 22, selected));
		inv.add(GUIUtils.getModuleSelectorComponent(MessengerModuleDescriptor.TITLE, 23, selected));
		inv.add(GUIUtils.getModuleSelectorComponent(MessengerModuleDescriptor.TAB, 24, selected));
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		switch (slot) {
			case 0:
				back.run();
				break;
			case 20:
				onSelect(MessengerModuleType.CHAT);
				break;
			case 21:
				onSelect(MessengerModuleType.BOSSBAR);
				break;
			case 22:
				onSelect(MessengerModuleType.ACTIONBAR);
				break;
			case 23:
				onSelect(MessengerModuleType.TITLE);
				break;
			case 24:
				onSelect(MessengerModuleType.TAB);
				break;
		}
	}

	public abstract void onSelect(MessengerModuleType type);
}
