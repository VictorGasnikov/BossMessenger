package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.config.ConfigManager;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.MessageListStorage;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModule;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.utils.MySqlUtils;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.*;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MessageListGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new AutoMessengerGUI(session));
		}
	};
	private Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new MessageListGUI(session, messenger));
		}
	};
	private int page;
	private boolean hasPrev;
	private boolean hasNext;
	private Messenger messenger;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can manage this message list", "and it's messages"));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 48, 49, 50));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.NETHER_STAR, 1, "§aAdd message"), 45));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.NAME_TAG, 1, "§eRename list"), 46));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.BARRIER, 1, "§cDelete list"), 47));
	}

	public MessageListGUI(UISession session, Messenger messenger) {
		this(session, messenger, 0);
	}

	public MessageListGUI(UISession session, Messenger messenger, int page) {
		super(session, null, new ArrayList<GUIComponent>(COMPONENTS));
		this.page = page;
		this.messenger = messenger;
		List<GUIComponent> inv = getComponents();
		MessageList list = messenger.getMessages();

		ItemStack random = null;
		if (list.isRandom()) {
			random = ItemUtils.createStack(Material.INK_SACK, 1, (short) 10, "§eRandom Message Order", "§aEnabled");
		} else {
			random = ItemUtils.createStack(Material.INK_SACK, 1, (short) 8, "§eRandom Message Order", "§cDisabled");
		}
		inv.add(new GUIComponent(random, 53));

		MessengerModule module = messenger.getModule();
		inv.add(new GUIComponent(ItemUtils.createStack(Material.MAP, 1, "§aMessenger Type", MessengerModuleDescriptor.valueOf(module.getType()).getName(), "", "§6[Click to change]"), 52));

		inv.add(new GUIComponent(list.getStorage() == MessageListStorage.CONFIG ? ItemUtils.createStack(Material.CHEST, 1, "§bStorage Location", "§eConfig", "", "§6[Click to switch to MySql]") : ItemUtils.createStack(Material.ENDER_CHEST, 1, "§bStorage Location", "§aMySql", "", "§6[Click to switch to config]"), 51));
		List<TemplateMessage> messages = list.getMessages();
		int totalPages = GUIUtils.getPages(messages, 27);
		setTitle("List §0" + messenger.getName() + " §2(" + (page + 1) + "/" + (totalPages + 1) + ")");
		int slot = 18;
		int startIndex = page * 27;
		for (int time = startIndex; time < startIndex + 27; time++) {
			if (messages.size() > time) {
				inv.add(new GUIComponent(ItemUtils.createStack(Material.PAPER, 1, ChatColor.translateAlternateColorCodes('&', messages.get(time).getText())), slot++));
			} else {
				break;
			}
		}
		if (totalPages > page) {
			inv.add(GUIUtils.ARROW_NEXT);
			hasNext = true;
		}
		if (page > 0) {
			inv.add(GUIUtils.ARROW_PREVIOUS);
			hasPrev = true;
		}
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		final String listname = messenger.getName();
		switch (slot) {
			case 0:
				back.run();
				break;
			case 47:
				Runnable deleteOk = new Runnable() {

					@Override
					public void run() {
						Actions.deleteList(listname);
						session.openUI(new AutoMessengerGUI(session));
					}
				};
				session.openUI(new ConfirmDialog(session, deleteOk, cancel, "Delete message list §e" + listname + "§b?"));
				break;
			case 46:
				AnvilEnterListener enter = new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						if (GUIUtils.isProperName(input)) {
							if (GUIUtils.isProperNameLength(input)) {
								if (!BossMessenger.MESSENGERS.containsKey(input)) {
									Actions.renameList(messenger.getMessages(), input);
									cancel.run();
								} else {
									session.openUI(new AlertDialog(session, "List was not created!", GUIUtils.getAlertMessage("§c§lThat list already exists!"), cancel));
								}
							} else {
								session.openUI(new AlertDialog(session, "List was not created!", GUIUtils.getAlertMessage("§c§lList name can not be longer than 15 characters!"), cancel));
							}
						} else {
							session.openUI(new AlertDialog(session, "List was not created!", GUIUtils.getAlertMessage("§c§lList name can only contain letters and numbers!"), cancel));
						}
					}
				};
				session.openUI(new ShortPrompt(session, enter, cancel, ItemUtils.createStack(Material.PAPER, 1, listname), "Enter your list name"));
				break;
			case 45:
				// session.openUI(new MessageBuilder(session, messenger,
				// session.getPlayer()));
				List<String[]> messageSequence = new ArrayList<>();
				messageSequence.add(new String[]{"Enter your message"});
				messageSequence.add(new String[]{"Enter your bossbar percent"});
				messageSequence.add(new String[]{"Enter your message's display time (in ticks)", "20 ticks = 1 second"});
				messageSequence.add(new String[]{"Enter the interval between this and next message (in ticks)", "20 ticks = 1 second"});
				session.openUI(new ChatStringSequenceReader(session, messageSequence) {

					@Override
					public void onSequenceComplete(List<String> values, boolean completed) {
						if (completed) {
							String text = values.get(0);
							Percent percent = new Percent(values.get(1));
							int show;
							try {
								show = Integer.parseInt(values.get(2));
								Validate.isTrue(show > 0);
							} catch (Exception e) {
								session.openUI(new AlertDialog(session, "§4Message was not created", GUIUtils.getAlertMessage("§cDisplay time is not a valid positive integer"), cancel));
								return;
							}
							int interval;
							try {
								interval = Integer.parseInt(values.get(3));
								Validate.isTrue(interval >= 0);
							} catch (Exception e) {
								session.openUI(new AlertDialog(session, "§4Message was not created", GUIUtils.getAlertMessage("§cInterval is not a valid integer"), cancel));
								return;
							}
							Actions.addMessage(messenger.getName(), text, percent, show, interval);

						}
						cancel.run();
					}
				});
				break;
			case 3:
				if (hasPrev) {
					session.openUI(new MessageListGUI(session, messenger, page - 1));
				}
				break;
			case 5:
				if (hasNext) {
					session.openUI(new MessageListGUI(session, messenger, page + 1));
				}
				break;
			case 51:
				final MessageList list = messenger.getMessages();
				if (list.getStorage() == MessageListStorage.CONFIG) {
					session.openUI(new ConfirmDialog(session, new Runnable() {

						@Override
						public void run() {
							list.setStorage(MessageListStorage.MYSQL);
							MySqlUtils.createTableForList(listname);
							MySqlUtils.fillMySqlTable(list);
							session.openUI(new AlertDialog(session, "Success", GUIUtils.getAlertSuccessMessage("List " + listname + " was transfered to MySQL"), cancel));
						}
					}, cancel, "Transfer the message list " + listname, " to the MySQL database? ", new String[]{"§o * Make sure that you have MySQL configured properly!"}));
				}
				break;
			case 52:
				session.openUI(new MessengerTypeSelector(session, messenger.getModule().getType(), cancel) {

					@Override
					public void onSelect(MessengerModuleType type) {
						messenger.setModule(type);
						ConfigManager.saveMessageList(messenger.getMessages());
						ConfigManager.saveMessages();
						cancel.run();
					}
				});
				break;
			case 53:
				Actions.toggleRandom(messenger.getMessages());
				cancel.run();
				break;
			default:
				if (slot > 17 && slot < 45) {
					int id = page * 27 + slot - 18;
					if (messenger.getMessages().hasMessage(id)) {
						session.openUI(new FullMessageGUI(session, messenger, id));
					}
				}
				break;
		}
	}
}
