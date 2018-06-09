package net.pixelatedd3v.bossmessenger.ui.gui;

import net.pixelatedd3v.bossmessenger.protocol.impl.Reflection;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUtils;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.RawAnvilControl;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.RawAnvilControl.AnvilClickEvent;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.RawAnvilControl.AnvilClickEventHandler;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.RawAnvilControl.AnvilSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class AnvilGUI extends GUI {
	
	//	private static final boolean USE_ANVIL = Reflection.version.equals("v1_12_R1.");
	private static final boolean USE_ANVIL = false;

	private ItemStack item;
	private String[] messages;

	public AnvilGUI(UISession session, ItemStack item, String... messages) {
		super(session);
		this.item = item;
		this.messages = messages;
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {

	}

	public abstract void onEnter(UISession session, String input);

	public abstract void onCancel(UISession session);

	public ItemStack getItem() {
		return item;
	}

	@Override
	public void open() {
		final UISession session = getSession();
		Player player = session.getPlayer();
		if (USE_ANVIL) {
			RawAnvilControl gui = new RawAnvilControl(player, new AnvilClickEventHandler() {

				@Override
				public void onAnvilClick(AnvilClickEvent event) {
					event.setWillClose(false);
					AnvilSlot slot = event.getSlot();
					if (slot == AnvilSlot.OUTPUT) {
						onEnter(session, event.getName());
					} else {
						onCancel(session);
					}
				}
			});
			gui.setSlot(AnvilSlot.INPUT_LEFT, item);
			gui.open();
		} else {
			ChatUI cui = new ChatUI(session) {

				@Override
				public void onChat(String message) {
					onEnter(session, message);
				}

				@Override
				public void onCancel() {
					AnvilGUI.this.onCancel(session);
				}
			};
			session.openUI(cui);
			ChatUtils.chatUIMessage(player, messages);
		}
	}

	@Override
	public boolean onClose() {
		return true;
	}
}
