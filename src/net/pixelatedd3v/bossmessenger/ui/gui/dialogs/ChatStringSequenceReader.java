package net.pixelatedd3v.bossmessenger.ui.gui.dialogs;

import net.pixelatedd3v.bossmessenger.ui.gui.UISession;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ChatStringSequenceReader extends ChatUI {
	private Iterator<String[]> iter;
	private List<String> values = new ArrayList<String>();

	public ChatStringSequenceReader(UISession session, List<String[]> messages) {
		super(session);
		this.iter = messages.iterator();
		queryNext();

		ChatUtils.chatUIMessage(session.getPlayer(), "Enter your message");
	}

	@Override
	public void onChat(String input) {
		values.add(input);
		queryNext();
	}

	@Override
	public void onCancel() {
		onSequenceComplete(values, false);
	}

	public void queryNext() {
		if (iter.hasNext()) {
			ChatUtils.chatUIMessage(getSession().getPlayer(), iter.next());
		} else {
			onSequenceComplete(values, true);
		}
	}

	public abstract void onSequenceComplete(List<String> values, boolean completed);
}
