package net.pixelatedd3v.bossmessenger.messenger.task;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("Task")
public class Task implements ConfigurationSerializable {
	private String name;
	private String message;
	private List<String> commands;

	public Task(String name, String message, List<String> commands) {
		this.name = name;
		this.message = message;
		this.commands = commands;
	}

	@SuppressWarnings("unchecked")
	public Task(Map<String, Object> from) {
		name = (String) from.get("Name");
		message = (String) from.get("Text");
		commands = (List<String>) from.get("Commands");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void addCommand(String cmd) {
		commands.add(cmd);
	}

	public void removeCommand(int id) {
		commands.remove(id);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("Name", name);
		output.put("Text", message);
		output.put("Commands", commands);

		return output;
	}
}
