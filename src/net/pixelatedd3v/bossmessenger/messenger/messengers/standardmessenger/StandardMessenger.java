package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.api.events.MessagePriority;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.generator.MessageGenerator;
import net.pixelatedd3v.bossmessenger.messenger.message.*;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerScope;
import net.pixelatedd3v.bossmessenger.messenger.messengers.PrivateMessengerPool;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModule;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.messenger.task.ScheduledTask;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class StandardMessenger implements Messenger {

	//	public static final TemplateMessage NO_MESSAGE = new TemplateMessage("&cTo add messages to this list, use &b/bm add", 100, 0, Arrays.asList(new MessageAttribute("Percent", new Percent(100))));

	Map<UUID, Player> players = new HashMap<>();
	MessengerScope scope = MessengerScope.GROUP;
	MessengerModule module;
	MessageList list;
	GlobalTimedMessage auto;
	BukkitTask autoProcess;
	BukkitTask autoAutoProcess;
	boolean isShowing;
	GlobalTimedMessage schedule;
	List<String> scheduleCommands;
	BukkitTask scheduleProcess;
	BukkitTask scheduleAutoProcess;
	ScheduledTask scheduledTask;
	boolean isScheduling;
	GlobalTimedMessage event;
	BukkitTask eventProcess;
	BukkitTask eventAutoProcess;
	boolean hasEvent;
	GlobalTimedMessage broadcast;
	BukkitTask broadcastProcess;
	BukkitTask broadcastAutoProcess;
	boolean isBroadcasting;
	GlobalTimedMessage display;
	BukkitTask displayProcess;
	BukkitTask displayAutoProcess;
	boolean isDisplaying;

	public StandardMessenger(MessageList list, MessengerModule module) {
		this.list = list;
		this.module = module;
		if (list != null) {
			this.players = MessengerManager.getPlayersForMessenger(list.getName());
			TemplateMessage message = list.nextMessage();
			//			if (message == null) {
			//				message = NO_MESSAGE;
			//			}
			if (message != null && message.isAutoUpdated()) {
				autoAutoProcess = new DelayedMessageUpdater(this, message, MessagePriority.AUTO).getTask();
			} else {
				autoProcess = new MessageShow(this, message, true).runTask(BossMessenger.INSTANCE);
			}
		}
	}

	public MessageShow nextMessage() {
		return new MessageShow(this, list.nextMessage(), true);
	}

	public void setMessage(GlobalTimedMessage message) {
		setMessageFor(players.values(), message);
	}

	public void removeMessage() {
		removeMessageFor(players.values());
	}

	public void setMessageFor(Collection<Player> players, GlobalTimedMessage message) {
		MessagePriority priority = getPriority();
		for (Player player : players) {
			PlayerTimedMessage pMessage = MessageGenerator.generatePlayerMessage(message, this, player, priority);
			setMessageForPlayer(pMessage, priority);
		}
	}

	public MessagePriority getPriority() {
		if (isDisplaying) {
			return MessagePriority.DISPLAY;
		} else if (isBroadcasting) {
			return MessagePriority.BROADCAST;
		} else if (hasEvent) {
			return MessagePriority.EVENT;
		} else if (isScheduling) {
			return MessagePriority.SCHEDULE;
		} else if (isShowing) {
			return MessagePriority.AUTO;
		}
		return null;
	}

	public void setMessageForPlayer(PlayerTimedMessage message, MessagePriority priority) {
		Player player = message.getPlayer();
		if (!(Settings.WORLD_BLACKLIST_ENABLED && Settings.WORLD_BLACKLIST.contains(player.getWorld().getName().toLowerCase())) && !Settings.PLAYER_BLACKLIST.contains(player.getUniqueId())) {
			Messenger messenger = null;
			if (scope == MessengerScope.GROUP) {
				PrivateMessengerPool pool = MessengerManager.getPrivateMessengerPool(player, false);
				if (pool != null) {
					messenger = pool.getMessenger(module.getType(), false);
				}
			}

			if (messenger != null) {
				MessagePriority messengerPriority = messenger.getPriority();
				if (messengerPriority == null || messenger.getModule().getType() != module.getType()) {
					sendMessagePacket(message);
				}
			} else {
				MessagePriority messagePriority = getPriority();
				if (messagePriority != null && priority.isHigherOrEqual(messagePriority)) {
					sendMessagePacket(message);
				}
			}
		}
	}

	private void sendMessagePacket(PlayerTimedMessage message) {
		module.setMessage(message, this);
	}

	public void removeMessageFor(Collection<Player> collection) {
		for (Player player : collection) {
			removeMessageForPlayer(player);
		}
	}

	public void stopAll() {
		if (autoProcess != null) {
			autoProcess.cancel();
		}
		if (autoAutoProcess != null) {
			autoAutoProcess.cancel();
		}
		if (eventProcess != null) {
			eventProcess.cancel();
		}
		if (eventAutoProcess != null) {
			eventAutoProcess.cancel();
		}
		if (scheduleProcess != null) {
			scheduleProcess.cancel();
		}
		if (scheduleAutoProcess != null) {
			scheduleAutoProcess.cancel();
		}
		if (broadcastProcess != null) {
			broadcastProcess.cancel();
		}
		if (broadcastAutoProcess != null) {
			broadcastAutoProcess.cancel();
		}
	}

	public void removeMessageForPlayer(Player player) {
		Messenger messenger = null;
		PrivateMessengerPool pool = null;
		if (scope == MessengerScope.GROUP) {
			pool = MessengerManager.getPrivateMessengerPool(player, false);
			if (pool != null) {
				messenger = pool.getMessenger(module.getType(), false);
			}
		}
		if (messenger != null && messenger.getModule() == module && messenger.getPriority() != null) {
			messenger.update(player);
		} else {
			module.removeMessage(player, this);
		}
	}

	@Override
	public void restart() {
		if (autoAutoProcess != null) {
			autoAutoProcess.cancel();
		}
		if (autoProcess != null) {
			autoProcess.cancel();
			autoProcess = nextMessage().runTask(BossMessenger.INSTANCE);
		}
	}

	public void fullRestart() {
		autoProcess.cancel();
		if (autoAutoProcess != null) {
			autoAutoProcess.cancel();
		}
		autoProcess = nextMessage().runTask(BossMessenger.INSTANCE);
	}

	@Override
	public void dispose() {
		stopAll();
		removeMessage();
	}

	@Override
	public void broadcast(TimedMessage message) {
		if (message.isAutoUpdated()) {
			if (broadcastAutoProcess != null) {
				broadcastAutoProcess.cancel();
			}
			broadcastAutoProcess = new DelayedMessageUpdater(this, message, MessagePriority.BROADCAST).getTask();
		} else {
			if (broadcastProcess != null) {
				broadcastProcess.cancel();
			}
			broadcast(message, true);
		}
	}

	public void broadcast(TimedMessage message, boolean remove) {
		broadcast = MessageGenerator.generateGlobalMessage(message, this, MessagePriority.BROADCAST);
		isBroadcasting = true;
		if (MessagePriority.BROADCAST.isHigherOrEqual(getPriority())) {
			setMessage(broadcast);
		}
		if (remove) {
			broadcastProcess = new RemoveBroadcast(this).runTaskLater(BossMessenger.INSTANCE, message.getShow());
		}
	}

	@Override
	public void display(TimedMessage message) {
		if (message.isAutoUpdated()) {
			if (displayAutoProcess != null) {
				displayAutoProcess.cancel();
			}
			displayAutoProcess = new DelayedMessageUpdater(this, message, MessagePriority.DISPLAY).getTask();
		} else {
			if (displayProcess != null) {
				displayProcess.cancel();
			}
			display(message, true);
		}
	}

	public void display(TimedMessage message, boolean remove) {
		display = MessageGenerator.generateGlobalMessage(message, this, MessagePriority.DISPLAY);
		isDisplaying = true;
		setMessage(display);
		if (remove) {
			displayProcess = new RemoveDisplay(this).runTaskLater(BossMessenger.INSTANCE, message.getShow());
		}
	}

	@Override
	public void event(TimedMessage message) {
		if (message.isAutoUpdated()) {
			if (eventAutoProcess != null) {
				eventAutoProcess.cancel();
			}
			eventAutoProcess = new DelayedMessageUpdater(this, message, MessagePriority.EVENT).getTask();
		} else {
			if (eventProcess != null) {
				eventProcess.cancel();
			}
			event(message, true);
		}
	}

	public void event(TimedMessage message, boolean remove) {
		event = MessageGenerator.generateGlobalMessage(message, this, MessagePriority.EVENT);
		hasEvent = true;
		if (MessagePriority.EVENT.isHigherOrEqual(getPriority())) {
			setMessage(event);
		}
		if (remove) {
			eventProcess = new RemoveEvent(this).runTaskLater(BossMessenger.INSTANCE, message.getShow());
		}
	}

	public void schedule(TimedMessage message) {
		schedule = MessageGenerator.generateGlobalMessage(message, this, MessagePriority.SCHEDULE);
		isScheduling = true;
		if (MessagePriority.SCHEDULE.isHigherOrEqual(getPriority())) {
			setMessage(schedule);
		}
	}

	@Override
	public void schedule(TimedMessage message, List<String> cmds) {
		if (message.isAutoUpdated()) {
			scheduleCommands = cmds;
			if (scheduleAutoProcess != null) {
				scheduleAutoProcess.cancel();
			}
			scheduledTask = new ScheduledTask(this, message);
			scheduleAutoProcess = new DelayedMessageUpdater(this, message, MessagePriority.SCHEDULE).getTask();
		} else {
			if (scheduleProcess != null) {
				scheduleProcess.cancel();
			}
			scheduledTask = new ScheduledTask(this, message);
			scheduleProcess = new FinishSchedule(this, cmds).runTaskLater(BossMessenger.INSTANCE, message.getShow());
			schedule(message);
		}
	}

	public void updateForAll() {
		if (module.update()) {
			MessagePriority priority = getPriority();
			if (priority != null) {
				switch (getPriority()) {
					case DISPLAY:
						setMessage(display);
						break;
					case BROADCAST:
						setMessage(broadcast);
						break;
					case EVENT:
						setMessage(event);
						break;
					case SCHEDULE:
						setMessage(schedule);
						break;
					case AUTO:
						setMessage(auto);
						break;
					default:
						break;
				}
			} else {
				removeMessage();
			}
		}
	}

	@Override
	public void update(Player player) {
		if (module.update()) {
			MessagePriority priority = getPriority();
			if (priority != null) {
				switch (priority) {
					case DISPLAY:
						setMessageForPlayer(MessageGenerator.generatePlayerMessage(display, this, player, priority), priority);
						break;
					case BROADCAST:
						setMessageForPlayer(MessageGenerator.generatePlayerMessage(broadcast, this, player, priority), priority);
						break;
					case EVENT:
						setMessageForPlayer(MessageGenerator.generatePlayerMessage(event, this, player, priority), priority);
						break;
					case SCHEDULE:
						setMessageForPlayer(MessageGenerator.generatePlayerMessage(schedule, this, player, priority), priority);
						break;
					case AUTO:
						setMessageForPlayer(MessageGenerator.generatePlayerMessage(auto, this, player, priority), priority);
						break;
					default:
						break;
				}
			} else {
				removeMessageForPlayer(player);
			}
		}
	}

	@Override
	public MessengerScope getScope() {
		return scope;
	}

	@Override
	public String getName() {
		return list.getName();
	}

	@Override
	public MessageList getMessages() {
		return list;
	}

	@Override
	public void addPlayer(Player player) {
		players.put(player.getUniqueId(), player);
		update(player);
	}

	@Override
	public Collection<Player> getPlayers() {
		return players.values();
	}

	@Override
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void setMessages(MessageList list) {
		this.list = list;
		updatePlayersList();
	}

	public void updatePlayersList() {
		removeMessage();
		if (list != null) {
			players = MessengerManager.getPlayersForMessenger(list.getName());
		}
		updateForAll();
	}

	@Override
	public boolean isDisplaying() {
		return isDisplaying;
	}

	@Override
	public GlobalTimedMessage getDisplayMessage() {
		return display;
	}

	@Override
	public boolean isBroadcasting() {
		return isBroadcasting;
	}

	@Override
	public GlobalTimedMessage getBroadcastMessage() {
		return broadcast;
	}

	@Override
	public boolean hasEvent() {
		return hasEvent;
	}

	@Override
	public GlobalTimedMessage getEventMessage() {
		return event;
	}

	@Override
	public boolean isScheduling() {
		return isScheduling;
	}

	@Override
	public GlobalTimedMessage getScheduleMessage() {
		return schedule;
	}

	@Override
	public ScheduledTask getScheduledTask() {
		return scheduledTask;
	}

	@Override
	public void cancelScheduledTask() {
		if (scheduleProcess != null) {
			scheduleProcess.cancel();
		}
		if (scheduleAutoProcess != null) {
			scheduleAutoProcess.cancel();
		}
		scheduledTask = null;
	}

	@Override
	public List<String> getTaskCommands() {
		return scheduleCommands;
	}

	@Override
	public void setModule(MessengerModuleType type) {
		removeMessage();
		if (list != null) {
			list.setModule(type);
		}
		this.module = type.getModule();
		updateForAll();
	}

	@Override
	public MessengerModule getModule() {
		return module;
	}
}
