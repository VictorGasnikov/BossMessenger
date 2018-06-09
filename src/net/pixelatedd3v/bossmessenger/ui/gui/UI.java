package net.pixelatedd3v.bossmessenger.ui.gui;

public interface UI {
	public UIType getType();

	public void open();

	public UISession getSession();

	public void onLeave(boolean isPlayerClose);
}
