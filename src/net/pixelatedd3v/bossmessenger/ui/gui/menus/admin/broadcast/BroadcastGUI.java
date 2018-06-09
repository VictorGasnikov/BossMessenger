package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.broadcast;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUtils;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.*;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.MainGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BroadcastGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<>();

	private Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new BroadcastGUI(session));
		}
	};
	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new MainGUI(session));
		}
	};

	static {
		ItemStack out = ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 11, "§f");
		COMPONENTS.addAll(GUIUtils.duplicateComponents(out, 1, 2, 3, 5, 6, 7, 8, 9, 18, 27, 36, 17, 26, 35, 44, 45, 53));

		ItemStack in = ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 14, "§f");
		COMPONENTS.addAll(GUIUtils.duplicateComponents(in, 10, 11, 12, 13, 14, 15, 16, 19, 28, 37, 25, 34, 43, 46, 47, 51, 52));

		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.SIGN, 1, "§bGlobal Broadcast", "§aBroadcast a message to everyone", "§eInput: Message, Time"), 30));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.WATCH, 1, "§bQuick Broadcast", "§aBroadcast a message to everyone for a default time", "§eInput: Message"), 31));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.MONSTER_EGG, 1, (short) 91, "§bGroup Broadcast", "§aBroadcast a message in a specified message list", "§eInput: Message list, Message, Time"), 32));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.SKULL_ITEM, 1, (short) 3, "§bPlayer Broadcast", "§aBroadcast a message to a specific player", "§eInput: Message, Time"), 40));

		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can broadcast messages globally,", "or for certain people."));
	}

	public BroadcastGUI(UISession session) {
		super(session, "Broadcast", COMPONENTS);
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		final Player player = session.getPlayer();

		switch (slot) {
			case 0:
				back.run();
				break;
			case 30:
				List<String[]> broadcastMessages = new ArrayList<>();
				broadcastMessages.add(new String[]{"Enter your message"});
				broadcastMessages.add(new String[]{"Enter your display time (in seconds)"});
				ChatStringSequenceReader broadcastUI = new ChatStringSequenceReader(session, broadcastMessages) {

					@Override
					public void onSequenceComplete(List<String> values, boolean completed) {
						if (completed) {
							if (!Actions.broadcast(values.get(0), values.get(1))) {
								session.openUI(new AlertDialog(session, "Message was not broadcasted", GUIUtils.getAlertMessage("Display time is not a valid positive integer"), cancel));
								return;
							}
							session.exit();
						} else {
							cancel.run();
						}
					}
				};
				session.openUI(broadcastUI);
				break;
			case 31:
				ChatUI quickBroadcastUI = new ChatUI(session) {

					@Override
					public void onChat(String input) {
						Actions.quickBroadcast(input);
						session.exit();
					}

					@Override
					public void onCancel() {
						cancel.run();
					}
				};
				ChatUtils.chatUIMessage(player, "Enter your message");
				session.openUI(quickBroadcastUI);
				break;
			case 32:
				SelectorMenu<Messenger> menu = new SelectorMenu<Messenger>(session, "Choose a Message list", new ArrayList<>(BossMessenger.MESSENGERS.values()), Material.PAPER, cancel) {

					@Override
					public void onSelect(final Messenger messenger, int id) {
						List<String[]> gbMessages = new ArrayList<>();
						gbMessages.add(new String[]{"Enter your message"});
						gbMessages.add(new String[]{"Enter your display time (in seconds)"});
						ChatStringSequenceReader reader = new ChatStringSequenceReader(session, gbMessages) {

							@Override
							public void onSequenceComplete(List<String> values, boolean completed) {
								if (completed) {
									if (!Actions.groupBroadcast(values.get(0), values.get(1), messenger)) {
										session.openUI(new AlertDialog(session, "Message was not broadcasted", GUIUtils.getAlertMessage("Display time is not a valid positive integer"), cancel));
										return;
									}
									session.exit();
								} else {
									cancel.run();
								}
							}
						};
						session.openUI(reader);
					}

					@Override
					public String parse(Messenger messenger) {
						return "§e" + messenger.getName();
					}
				};
				session.openUI(menu);
				break;
			case 40:
				session.openUI(new ShortPrompt(session, new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						final Player targetPlayer = Bukkit.getPlayer(input);
						if (targetPlayer != null && targetPlayer.isOnline()) {
							List<String[]> playerMessages = new ArrayList<>();
							playerMessages.add(new String[]{"Enter your message"});
							playerMessages.add(new String[]{"Enter your display time (in seconds)"});
							ChatStringSequenceReader playerUI = new ChatStringSequenceReader(session, playerMessages) {

								@Override
								public void onSequenceComplete(List<String> values, boolean completed) {
									if (completed) {
										int show = Actions.convertPositiveInteger(values.get(1));
										if (show != -1) {
											Actions.sendMessageToPlayer(targetPlayer, new TimedMessage(values.get(0), show * 20, new MessageAttribute("Percent", Settings.BROADCAST_PERCENT)));
											session.exit();
										} else {
											session.openUI(new AlertDialog(session, "Message was not broadcasted", GUIUtils.getAlertMessage("Display time is not a valid positive integer"), cancel));
										}
									} else {
										cancel.run();
									}
								}
							};
							session.openUI(playerUI);
						} else {
							session.openUI(new AlertDialog(session, "Player was not found", GUIUtils.getAlertMessage(Lang.PLAYER_NOT_FOUND), cancel));
						}
					}
				}, cancel, GUIUtils.PLAYER, Lang.ENTER_PLAYER_NAME));
				break;
			default:
				break;
		}
	}
}
