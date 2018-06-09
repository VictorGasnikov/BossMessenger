package net.pixelatedd3v.bossmessenger.messenger.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.VanishPlugin;
/**
 * Created by Victor Gasnikov on 11/11/2017.
 * Project: bossmessenger
 */
public class VnpManager {
	private static VnpManager instance = new VnpManager();
	
	private boolean vnpEnabled = false;
	private VanishPlugin vnpPlugin;
	private VanishManager manager;
	
	private VnpManager() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("VanishNoPacket");
		if (plugin != null && plugin instanceof VanishPlugin) {
			vnpEnabled = true;
			vnpPlugin = (VanishPlugin) plugin;
			manager = vnpPlugin.getManager();
		}
	}
	
	public boolean isVisible(Player player) {
		return !vnpEnabled || !manager.isVanished(player);
	}
	
	public int getHidden() {
		return vnpEnabled ? manager.numVanished() : 0;
	}
	
	public static VnpManager getInstance() {
		return instance;
	}
}
