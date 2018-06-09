package net.pixelatedd3v.bossmessenger.messenger.manager;

import org.bukkit.Bukkit;
/**
 * Created by Victor Gasnikov on 11/11/2017.
 * Project: bossmessenger
 */
public class MessageManager {
	private static MessageManager instance = new MessageManager();
	
	private MessageManager() {
	
	}
	
	public int getVisiblePlayerCount() {
		return Bukkit.getOnlinePlayers().size() - VnpManager.getInstance().getHidden();
	}
	
	public static MessageManager getInstance() {
		return instance;
	}
}
