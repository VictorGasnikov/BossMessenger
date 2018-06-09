package net.pixelatedd3v.bossmessenger.ui.gui.menus.user;

import net.pixelatedd3v.bossmessenger.GroupManager;
import net.pixelatedd3v.bossmessenger.Reference;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.lang.LangUtils;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUtils;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AlertDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.ShortPrompt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new UserGUI(session));
		}
	};

	static {
		COMPONENTS.add(GUIUtils.getInfo("Click on an item to perform an action"));

		ItemStack out = ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 11, "§f");
		COMPONENTS.addAll(GUIUtils.duplicateComponents(out, 0, 1, 2, 3, 5, 6, 7, 8, 9, 18, 27, 36, 17, 26, 35, 44, 45, 46, 52, 53));

		ItemStack in = ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f");
		COMPONENTS.addAll(GUIUtils.duplicateComponents(in, 10, 11, 12, 13, 14, 15, 16, 19, 28, 37, 25, 34, 43, 47, 51));
	}

	public UserGUI(UISession session) {
		super(session, "BossMessenger", COMPONENTS);
		List<GUIComponent> inv = getComponents();
		Player player = session.getPlayer();
		UUID uuid = player.getUniqueId();
		boolean enabled = !Settings.PLAYER_BLACKLIST.contains(uuid);

		if (GroupManager.hasPermission(player, Reference.PERMISSION_PLAYER_TOGGLE)) {
			inv.add(new GUIComponent(ItemUtils.createStack(Material.INK_SACK, 1, (short) (enabled ? 10 : 8), "§bMessages", enabled ? "§aEnabled" : "§cDisabled", "", Lang.CLICK_TO_TOGGLE), 30));
		}
		if (GroupManager.hasPermission(player, Reference.PERMISSION_PLAYER_PM)) {
			inv.add(new GUIComponent(ItemUtils.createStack(Material.MAP, 1, "§bPrivate Message", "", "§e[Click to send]"), 32));
		}
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		final Player player = session.getPlayer();
		switch (slot) {
			case 30:
				if (GroupManager.hasPermission(player, Reference.PERMISSION_PLAYER_TOGGLE)) {
					Actions.toggleMessages(player);
					cancel.run();
				}
				break;
			case 32:
				if (GroupManager.hasPermission(player, Reference.PERMISSION_PLAYER_PM)) {
					session.openUI(new ShortPrompt(session, new AnvilEnterListener() {

						@Override
						public void onEnter(String input) {
							final Player targetPlayer = Bukkit.getPlayer(input);
							if (targetPlayer != null && targetPlayer.isOnline()) {
								ChatUtils.chatUIMessage(player, Lang.ENTER_YOUR_MESSAGE);
								session.openUI(new ChatUI(session) {

									@Override
									public void onChat(String input) {
										Actions.privateMessage(player.getName(), targetPlayer, input);
										session.exit();
										LangUtils.sendMessage(player, Lang.MESSAGE_SENT);
									}

									@Override
									public void onCancel() {
										cancel.run();
									}
								});
							} else {
								session.openUI(new AlertDialog(session, "Player was not found", GUIUtils.getAlertMessage(Lang.PLAYER_NOT_FOUND), cancel));
							}
						}
					}, cancel, GUIUtils.PLAYER, Lang.ENTER_PLAYER_NAME));
				}
				break;
			default:
				break;
		}
	}
}
