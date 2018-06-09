package net.pixelatedd3v.bossmessenger.ui.gui.chat;

import net.pixelatedd3v.bossmessenger.ui.gui.UI;
import net.pixelatedd3v.bossmessenger.ui.gui.UISession;
import net.pixelatedd3v.bossmessenger.ui.gui.UIType;

public abstract class ChatUI implements UI {
	private UISession session;

	public ChatUI(UISession session) {
		this.session = session;
	}

	public abstract void onChat(String input);

	public abstract void onCancel();

	@Override
	public void open() {
		session.getPlayer().closeInventory();
	}

	@Override
	public UIType getType() {
		return UIType.CHAT;
	}

	@Override
	public UISession getSession() {
		return session;
	}

	@Override
	public void onLeave(boolean isPlayerClose) {

	}
}
