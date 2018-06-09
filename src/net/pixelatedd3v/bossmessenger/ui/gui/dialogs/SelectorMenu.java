package net.pixelatedd3v.bossmessenger.ui.gui.dialogs;

import net.pixelatedd3v.bossmessenger.ui.gui.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectorMenu<T> extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private List<T> objects;
	private Runnable back;
	private String name;
	private int page;
	private boolean hasPrev;
	private boolean hasNext;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Click on an item to select"));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
	}

	public SelectorMenu(UISession session, String title, List<T> objects, Material display, Runnable back) {
		this(session, title, objects, display, null, back);
	}

	public SelectorMenu(UISession session, String title, List<T> objects, Material display, String name, Runnable back) {
		this(session, title, objects, display, name, back, 0);
	}

	public SelectorMenu(UISession session, String title, List<T> objects, Material display, String name, Runnable back, int page) {
		super(session, null, new ArrayList<GUIComponent>(COMPONENTS));
		this.page = page;
		this.objects = objects;
		this.name = name;
		this.back = back;
		List<GUIComponent> inv = getComponents();
		int totalPages = GUIUtils.getPages(objects, 36);
		setTitle(title + " §2(" + (page + 1) + "/" + (totalPages + 1) + ")");
		int slot = 18;
		int startIndex = page * 36;
		for (int time = startIndex; time < startIndex + 36; time++) {
			if (objects.size() > time) {
				T object = objects.get(time);
				if (name != null) {
					inv.add(new GUIComponent(ItemUtils.createStack(display, 1, name, parse(object)), slot++));
				} else {
					inv.add(new GUIComponent(ItemUtils.createStack(display, 1, parse(object)), slot++));
				}
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
		switch (slot) {
			case 0:
				back.run();
				break;
			case 3:
				if (hasPrev) {
					session.openUI(new SelectorMenu<T>(session, name, objects, null, name, back, page - 1) {

						@Override
						public void onSelect(T object, int slot) {
							SelectorMenu.this.onSelect(object, slot - 1);
						}

						@Override
						public String parse(T object) {
							return SelectorMenu.this.parse(object);
						}

					});
				}
				break;
			case 5:
				if (hasNext) {
					session.openUI(new SelectorMenu<T>(session, name, objects, null, name, back, page + 1) {

						@Override
						public void onSelect(T object, int slot) {
							SelectorMenu.this.onSelect(object, slot + 1);

						}

						@Override
						public String parse(T object) {
							return SelectorMenu.this.parse(object);
						}
					});
				}
				break;
			default:
				if (slot > 17 && slot < 45) {
					int id = page * 36 + slot - 18;
					if (objects.size() > id) {
						T object = objects.get(id);
						if (object != null) {
							onSelect(object, id);
						}
					}
				}
				break;
		}
	}

	public abstract void onSelect(T object, int slot);

	public abstract String parse(T object);
}
