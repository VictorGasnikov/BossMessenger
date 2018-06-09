package net.pixelatedd3v.bossmessenger.messenger.messengers;

import net.pixelatedd3v.bossmessenger.api.events.MessagePriority;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.message.GlobalTimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModule;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.messenger.task.ScheduledTask;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public interface Messenger {

	public void restart();

	public void dispose();

	public void display(TimedMessage message);

	public boolean isDisplaying();

	public GlobalTimedMessage getDisplayMessage();

	public void broadcast(TimedMessage message);

	public boolean isBroadcasting();

	public GlobalTimedMessage getBroadcastMessage();

	public void event(TimedMessage message);

	public boolean hasEvent();

	public GlobalTimedMessage getEventMessage();

	public void schedule(TimedMessage message, List<String> cmds);

	public boolean isScheduling();

	public GlobalTimedMessage getScheduleMessage();

	public List<String> getTaskCommands();

	public ScheduledTask getScheduledTask();

	public void cancelScheduledTask();

	public MessagePriority getPriority();

	public MessengerScope getScope();

	public String getName();

	public MessageList getMessages();

	public void setMessages(MessageList list);

	public void update(Player player);

	public void addPlayer(Player player);

	public void removePlayer(Player player);

	public Collection<Player> getPlayers();

	public void setModule(MessengerModuleType module);

	public MessengerModule getModule();
}
