package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.ui.gui.UISession;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.SelectorMenu;
import org.bukkit.Material;

import java.util.ArrayList;

public class ListSelector extends SelectorMenu<Messenger> {
	private ListSelectListener listener;

	public ListSelector(UISession session, Runnable back, ListSelectListener listener) {
		super(session, "Select a message list", new ArrayList<>(BossMessenger.MESSENGERS.values()), Material.PAPER, back);
		this.listener = listener;
	}

	@Override
	public void onSelect(Messenger messenger, int slot) {
		listener.onSelect(messenger, slot);
	}

	@Override
	public String parse(Messenger messenger) {
		return messenger.getName();
	}
}
