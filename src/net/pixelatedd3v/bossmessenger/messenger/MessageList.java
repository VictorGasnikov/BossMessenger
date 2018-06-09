package net.pixelatedd3v.bossmessenger.messenger;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.Reference;
import net.pixelatedd3v.bossmessenger.config.ConfigManager;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.utils.MySqlUtils;
import net.pixelatedd3v.bossmessenger.utils.RandomUtils;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("MessageList")
public class MessageList implements ConfigurationSerializable {
	private List<TemplateMessage> messages = new ArrayList<TemplateMessage>();
	private String name;
	private boolean random;
	private MessengerModuleType module;
	private MessageListStorage storage;
	private int current = 0;

	public MessageList(List<TemplateMessage> messages, String name) {
		this(messages, name, false);
	}

	public MessageList(List<TemplateMessage> messages, String name, MessengerModuleType module, MessageListStorage storage, boolean random) {
		this.name = name;
		this.random = random;
		this.module = module;
		this.storage = storage;
		this.messages = messages;
	}

	public MessageList(List<TemplateMessage> messages, String name, boolean random) {
		this(messages, name, Reference.DEFAULT_MODULE, MessageListStorage.CONFIG, random);
	}

	@SuppressWarnings("unchecked")
	public MessageList(Map<String, Object> from) {
		this.name = (String) from.get("Name");
		this.random = (boolean) from.get("Random");
		Object st = from.get("Storage");
		this.storage = st == null ? MessageListStorage.CONFIG : MessageListStorage.valueOf((String) st);
		this.module = MessengerModuleType.valueOf((String) from.get("Type"));
		this.messages = (List<TemplateMessage>) (storage == MessageListStorage.CONFIG ? from.get("Messages") : MySqlUtils.loadMessagesFromMySql(name));

		// Fix formating
		if (st == null) {
			new BukkitRunnable() {

				@Override
				public void run() {
					ConfigManager.saveMessageList(MessageList.this);
					ConfigManager.saveMessages();
				}
			}.runTask(BossMessenger.INSTANCE);
		}
	}

	public String getName() {
		return name;
	}

	public TemplateMessage nextMessage() {
		int size = messages.size();
		if (size > 0) {
			if (random) {
				int c = RandomUtils.randInt(size);
				if (size == 1 || c != current) {
					current = c;
					return messages.get(current);
				} else {
					return nextMessage();
				}
			} else {
				if (current >= size) {
					current = 0;
				}
				return messages.get(current++);
			}
		} else {
			return null;
		}
	}

	public TemplateMessage getMessage(int id) {
		return messages.get(id);
	}

	public int size() {
		return messages.size();
	}

	public void addMessage(TemplateMessage msg) {
		messages.add(msg);
		current = 0;
	}

	public TemplateMessage removeMessage(int id) {
		current = 0;
		return messages.remove(id);
	}

	public boolean hasMessage(int id) {
		return id >= 0 && messages.size() > id;
	}

	public List<TemplateMessage> getMessages() {
		return messages;
	}

	public void setCurrent(int c) {
		current = c;
	}

	public int getCurrentCount() {
		return current;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public MessengerModuleType getModule() {
		return module;
	}

	public void setModule(MessengerModuleType module) {
		this.module = module;
	}

	public MessageListStorage getStorage() {
		return storage;
	}

	public void setStorage(MessageListStorage storage) {
		this.storage = storage;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("Name", name);
		output.put("Random", random);
		output.put("Messages", storage == MessageListStorage.CONFIG ? messages : new ArrayList<TemplateMessage>());
		output.put("Storage", storage.toString());
		output.put("Type", module.toString());

		return output;
	}
}
