package net.pixelatedd3v.bossmessenger.ui.gui.dialogs;

import net.pixelatedd3v.bossmessenger.ui.gui.AnvilGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.UISession;
import org.bukkit.inventory.ItemStack;

public class ShortPrompt extends AnvilGUI {
	private AnvilEnterListener enter;
	private Runnable cancel;

	public ShortPrompt(UISession session, AnvilEnterListener enter, Runnable cancel, ItemStack item, String... messages) {
		super(session, item, messages);
		this.enter = enter;
		this.cancel = cancel;
	}

	@Override
	public void onEnter(UISession session, String input) {
		enter.onEnter(input);
	}

	@Override
	public void onCancel(UISession session) {
		cancel.run();
	}
}
