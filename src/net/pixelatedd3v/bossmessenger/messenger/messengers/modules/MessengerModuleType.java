package net.pixelatedd3v.bossmessenger.messenger.messengers.modules;

import net.pixelatedd3v.bossmessenger.api.events.MessageLocation;

public enum MessengerModuleType {
	CHAT(new StandardChatModule(), MessageLocation.CHAT),
	BOSSBAR(new StandardBossbarModule(), MessageLocation.BOSSBAR),
	ACTIONBAR(new StandardActionbarModule(), MessageLocation.ACTIONBAR),
	TITLE(new StandardTitleModule(), MessageLocation.TITLE),
	TAB(new StandardTabModule(), MessageLocation.TAB);

	private MessengerModule module;
	private MessageLocation location;

	MessengerModuleType(MessengerModule module, MessageLocation location) {
		this.module = module;
		this.location = location;
	}

	public MessengerModule getModule() {
		return module;
	}

	public MessageLocation getLocation() {
		return location;
	}
}
