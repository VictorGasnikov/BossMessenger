package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger;

import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public enum MessengerModuleDescriptor {
	CHAT("§fChat", new String[]{"§6Sends messages in chat"}, Material.PAPER, MessengerModuleType.CHAT),
	BOSSBAR("§bBossBar", new String[]{"§6Displays messages on the boss health bar"}, Material.DRAGON_EGG, MessengerModuleType.BOSSBAR),
	ACTIONBAR("§eActionBar", new String[]{"§6Displays messages on the actionbar (the text", "§6message bar, at the lower part of", "§6the screen, above the hotbar.", "§6§o* Note: Since the ActionBar can not represent", "§6§othe message's percentages, the percent values", "§6§owill be ignored."}, Material.NAME_TAG, MessengerModuleType.ACTIONBAR),
	TITLE("§dTitle", new String[]{"§6Displays messages on the titles"}, Material.SIGN, MessengerModuleType.TITLE),
	TAB("§aTab", new String[]{"§6Displays player list's header and footer"}, Material.MAP, MessengerModuleType.TAB);

	private static final Map<MessengerModuleType, MessengerModuleDescriptor> DESCRIPTORS = new HashMap<>();

	private String name;
	private String[] description;
	private Material material;
	private MessengerModuleType type;

	static {
		for (MessengerModuleDescriptor descriptor : values()) {
			DESCRIPTORS.put(descriptor.getType(), descriptor);
		}
	}

	private MessengerModuleDescriptor(String name, String[] description, Material material, MessengerModuleType type) {
		this.name = name;
		this.description = description;
		this.material = material;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String[] getDescription() {
		return description;
	}

	public Material getMaterial() {
		return material;
	}

	public MessengerModuleType getType() {
		return type;
	}

	public static MessengerModuleDescriptor valueOf(MessengerModuleType type) {
		return DESCRIPTORS.get(type);
	}
}
