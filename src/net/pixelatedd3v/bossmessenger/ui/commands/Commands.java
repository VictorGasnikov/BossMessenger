package net.pixelatedd3v.bossmessenger.ui.commands;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.GroupManager;
import net.pixelatedd3v.bossmessenger.config.ConfigManager;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.lang.LangUtils;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TimedMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.pixelatedd3v.bossmessenger.protocol.BossBar;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.UIManager;
import net.pixelatedd3v.bossmessenger.utils.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length > 0) {
			String cmd = args[0];

			switch (cmd.toLowerCase()) {
				case "cancel":
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (UIManager.chatCancel(player)) {
							break;
						}
					}
					LangUtils.sendError(sender, Lang.UNKNOWN_COMMAND);
					break;
				case "createlist":
					if (GroupManager.hasPermission(sender, CommandInfo.CREATELIST)) {
						if (args.length == 2) {
							String listname = args[1].toLowerCase();
							if (listname.length() <= 15) {
								if (!Settings.hasList(listname)) {
									Actions.createList(listname);
									LangUtils.sendMessage(sender, Lang.LIST_CREATED);
								} else {
									LangUtils.sendError(sender, Lang.LIST_EXISTS);
								}
							} else {
								LangUtils.sendError(sender, Lang.OVER_15_CHARACTERS);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.CREATELIST);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "deletelist":
					if (GroupManager.hasPermission(sender, CommandInfo.DELETELIST)) {
						if (args.length == 2) {
							String listname = args[1].toLowerCase();

							if (!Settings.hasList(listname)) {
								LangUtils.sendError(sender, Lang.LIST_DOES_NOT_EXIST);
							} else {
								Actions.deleteList(listname);
								LangUtils.sendMessage(sender, Lang.LIST_DELETED);
							}

						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.DELETELIST);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "add":
					if (GroupManager.hasPermission(sender, CommandInfo.ADD)) {
						if (args.length >= 5) {
							String listname = args[1].toLowerCase();

							if (Settings.hasList(listname)) {
								String percent = args[2].toLowerCase();
								int show = 0;
								int interval = 0;
								try {
									show = Integer.parseInt(args[3]);
									Validate.isTrue(show > 0);
								} catch (Exception e) {
									LangUtils.sendError(sender, Lang.NOT_A_VALID_POSITIVE_INTEGER);
									break;
								}
								try {
									interval = Integer.parseInt(args[4]);
								} catch (Exception e) {
									LangUtils.sendError(sender, Lang.NOT_A_VALID_INTEGER);
									break;
								}

								String text = StringUtils.join(Arrays.copyOfRange(args, 5, args.length), " ");
								Actions.addMessage(listname, text, new Percent(percent), show, interval);
								LangUtils.sendMessage(sender, Lang.MESSAGE_ADDED);
							} else {
								LangUtils.sendError(sender, Lang.LIST_DOES_NOT_EXIST);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.ADD);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "delete":
					if (GroupManager.hasPermission(sender, CommandInfo.DELETE)) {
						if (args.length == 3) {
							String listname = args[1].toLowerCase();

							if (Settings.hasList(listname)) {
								int id = 0;
								try {
									id = Integer.parseInt(args[2]) - 1;
								} catch (Exception e) {
									LangUtils.sendError(sender, Lang.NOT_A_VALID_POSITIVE_INTEGER);
									break;
								}
								if (Settings.hasMessage(listname, id)) {
									Actions.deleteMessage(listname, id);
									LangUtils.sendMessage(sender, Lang.MESSAGE_DELETED);
								} else {
									LangUtils.sendError(sender, Lang.MESSAGE_DOES_NOT_EXIST);
								}
							} else {
								LangUtils.sendError(sender, Lang.LIST_DOES_NOT_EXIST);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.DELETE);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "list":
					if (GroupManager.hasPermission(sender, CommandInfo.LIST)) {
						if (args.length == 2) {
							String listname = args[1].toLowerCase();

							if (!Settings.hasList(listname)) {
								LangUtils.sendError(sender, Lang.LIST_DOES_NOT_EXIST);
							} else {
								LangUtils.listMessages(sender, Settings.MESSAGES.get(listname));
							}
						} else if (args.length == 1) {
							LangUtils.listLists(sender);
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.DELETE);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "broadcast":
					if (GroupManager.hasPermission(sender, CommandInfo.BROADCAST)) {
						if (args.length > 2) {
							String text = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
							if (!Actions.broadcast(text, args[1])) {
								LangUtils.sendError(sender, Lang.NOT_A_VALID_POSITIVE_INTEGER);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.BROADCAST);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "qb":
					if (GroupManager.hasPermission(sender, CommandInfo.QB)) {
						if (args.length > 1) {
							String text = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
							TimedMessage message = new TimedMessage(text, Settings.BROADCAST_DEFAULT_TIME * 20, new MessageAttribute("Percent", Settings.BROADCAST_PERCENT));
							for (Messenger messenger : BossMessenger.MESSENGERS.values()) {
								messenger.broadcast(message);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.QB);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "gb":
					if (GroupManager.hasPermission(sender, CommandInfo.GB)) {
						if (args.length > 3) {
							String listname = args[1].toLowerCase();
							Messenger messenger = BossMessenger.MESSENGERS.get(listname);
							if (messenger != null) {
								int show = 0;
								try {
									show = Integer.parseInt(args[2]) * 20;
									Validate.isTrue(show > 0);
								} catch (Exception e) {
									LangUtils.sendError(sender, Lang.NOT_A_VALID_POSITIVE_INTEGER);
									break;
								}
								String text = StringUtils.join(Arrays.copyOfRange(args, 3, args.length), " ");
								TimedMessage message = new TimedMessage(text,  show, new MessageAttribute("Percent", Settings.BROADCAST_PERCENT));
								messenger.broadcast(message);
							} else {
								LangUtils.sendError(sender, Lang.LIST_DOES_NOT_EXIST);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.GB);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "createtask":
					if (GroupManager.hasPermission(sender, CommandInfo.CREATETASK)) {
						if (args.length > 2) {
							String taskname = args[1].toLowerCase();
							if (taskname.length() <= 15) {
								if (!Settings.hasTask(taskname)) {
									String text = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
									Actions.createTask(taskname, text);
									LangUtils.sendMessage(sender, Lang.TASK_CREATED);
								} else {
									LangUtils.sendError(sender, Lang.TASK_EXISTS);
								}
							} else {
								LangUtils.sendError(sender, Lang.OVER_15_CHARACTERS);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.CREATETASK);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "deletetask":
					if (GroupManager.hasPermission(sender, CommandInfo.DELETETASK)) {
						if (args.length == 2) {
							String taskname = args[1].toLowerCase();
							if (Settings.hasTask(taskname)) {
								Actions.deleteTask(taskname);
								LangUtils.sendMessage(sender, Lang.TASK_DELETED);
							} else {
								LangUtils.sendError(sender, Lang.TASK_DOES_NOT_EXISTS);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.DELETETASK);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "addtaskcmd":
					if (GroupManager.hasPermission(sender, CommandInfo.ADDTASKCMD)) {
						if (args.length > 2) {
							String taskname = args[1].toLowerCase();
							Task task = Settings.TASKS.get(taskname);
							if (task != null) {
								String taskcmd = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
								Actions.addTaskCmd(task, taskcmd);
								LangUtils.sendMessage(sender, Lang.TASK_COMMAND_ADDED);
							} else {
								LangUtils.sendError(sender, Lang.TASK_DOES_NOT_EXISTS);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.ADDTASKCMD);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "tasklist":
					if (GroupManager.hasPermission(sender, CommandInfo.TASKLIST)) {
						LangUtils.listTasks(sender);
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "taskinfo":
					if (GroupManager.hasPermission(sender, CommandInfo.TASKINFO)) {
						if (args.length == 2) {
							String taskname = args[1].toLowerCase();
							if (Settings.hasTask(taskname)) {
								Task task = Settings.TASKS.get(taskname);
								LangUtils.showTaskInfo(sender, task);
							} else {
								LangUtils.sendError(sender, Lang.TASK_DOES_NOT_EXISTS);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.TASKINFO);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "schedule":
					if (GroupManager.hasPermission(sender, CommandInfo.SCHEDULE)) {
						if (args.length == 3) {
							String taskname = args[1].toLowerCase();
							if (Settings.hasTask(taskname)) {
								int time = 0;
								try {
									time = Integer.parseInt(args[2]);
									Validate.isTrue(time > 0);
								} catch (Exception e) {
									LangUtils.sendError(sender, Lang.NOT_A_VALID_POSITIVE_INTEGER);
									break;
								}
								Task task = Settings.TASKS.get(taskname);
								Actions.schedule(task, time);
							} else {
								LangUtils.sendError(sender, Lang.TASK_DOES_NOT_EXISTS);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.SCHEDULE);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "qs":
					if (GroupManager.hasPermission(sender, CommandInfo.QS)) {
						if (args.length == 2) {
							String taskname = args[1].toLowerCase();
							if (Settings.hasTask(taskname)) {
								Task task = Settings.TASKS.get(taskname);
								TimedMessage message = new TimedMessage(task.getMessage(), Settings.SCHEDULE_DEFAULT_TIME * 20, new MessageAttribute("Percent", Settings.SCHEDULE_PERCENT));
								List<String> cmds = task.getCommands();
								MessengerManager.scheduleForAll(message, cmds);
							} else {
								LangUtils.sendError(sender, Lang.TASK_DOES_NOT_EXISTS);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.QS);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "gs":
					if (GroupManager.hasPermission(sender, CommandInfo.GS)) {
						if (args.length == 4) {
							String listname = args[1].toLowerCase();
							if (Settings.hasList(listname)) {
								String taskname = args[2].toLowerCase();
								if (Settings.hasTask(taskname)) {
									int time = 0;
									try {
										time = Integer.parseInt(args[3]);
										Validate.isTrue(time > 0);
									} catch (Exception e) {
										LangUtils.sendError(sender, Lang.NOT_A_VALID_POSITIVE_INTEGER);
										break;
									}
									Task task = Settings.TASKS.get(taskname);
									Messenger messenger = BossMessenger.MESSENGERS.get(listname);
									Actions.groupSchedule(messenger, task, time);
								} else {
									LangUtils.sendError(sender, Lang.TASK_DOES_NOT_EXISTS);
								}
							} else {
								LangUtils.sendError(sender, Lang.LIST_DOES_NOT_EXIST);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.GS);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "disableworld":
					if (GroupManager.hasPermission(sender, CommandInfo.DISABLEWORLD)) {
						if (args.length == 2) {
							String worldname = args[1].toLowerCase();
							if (!Settings.WORLD_BLACKLIST.contains(worldname)) {
								World world = Bukkit.getWorld(worldname);
								if (world != null) {
									Settings.WORLD_BLACKLIST.add(worldname);
									Settings.WORLD_BLACKLIST_ENABLED = true;
									ConfigManager.saveBlacklistWorlds();
									ConfigManager.saveBlacklistInConfig();
									for (Player player : world.getPlayers()) {
										BossBar.removeAll(player);
									}
									LangUtils.sendMessage(sender, Lang.WORLD_DISABLED);
								} else {
									LangUtils.sendError(sender, Lang.WORLD_DOES_NOT_EXIST);
								}
							} else {
								LangUtils.sendError(sender, Lang.WORLD_ALREADY_DISABLED);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.DISABLEWORLD);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "admingui":
					if (GroupManager.hasPermission(sender, CommandInfo.ADMINGUI)) {
						if (sender instanceof Player) {
							Player player = (Player) sender;
							UIManager.startAdminSession(player);
						} else {
							LangUtils.sendError(sender, Lang.PLAYER_ONLY_COMMAND);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "gui":
					if (GroupManager.hasPermission(sender, CommandInfo.GUI)) {
						if (sender instanceof Player) {
							Player player = (Player) sender;
							UIManager.startUserSession(player);
						} else {
							LangUtils.sendError(sender, Lang.PLAYER_ONLY_COMMAND);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "toggle":
					if (GroupManager.hasPermission(sender, CommandInfo.TOGGLE)) {
						if (sender instanceof Player) {
							Player player = (Player) sender;
							Actions.toggleMessages(player);
							LangUtils.sendMessage(sender, Lang.PLAYER_MESSAGES_TOGGLED);
						} else {
							LangUtils.sendError(sender, Lang.PLAYER_ONLY_COMMAND);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "enableworld":
					if (GroupManager.hasPermission(sender, CommandInfo.ENABLEWORLD)) {
						if (args.length == 2) {
							String worldname = args[1].toLowerCase();
							if (Settings.WORLD_BLACKLIST.contains(worldname)) {
								World world = Bukkit.getWorld(worldname);
								Settings.WORLD_BLACKLIST.remove(worldname);
								Settings.WORLD_BLACKLIST_ENABLED = !Settings.WORLD_BLACKLIST.isEmpty();
								ConfigManager.saveBlacklistWorlds();
								ConfigManager.saveBlacklistInConfig();
								if (world != null) {
									for (Player player : world.getPlayers()) {
										MessageUtils.update(player);
									}
								}
								LangUtils.sendMessage(sender, Lang.WORLD_ENABLED);
							} else {
								LangUtils.sendError(sender, Lang.WORLD_ALREADY_ENABLED);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.ENABLEWORLD);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "reload":
					if (GroupManager.hasPermission(sender, CommandInfo.RELOAD)) {
						Actions.reload(sender);
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "pm":
					if (GroupManager.hasPermission(sender, CommandInfo.PM)) {
						if (args.length > 2) {
							String playername = args[1].toLowerCase();
							Player player = Bukkit.getPlayer(playername);
							if (player != null && player.isOnline()) {
								String text = StringUtils.join(Arrays.copyOfRange(args, 2, args.length), " ");
								Actions.privateMessage(sender.getName(), player, text);
								LangUtils.sendMessage(sender, Lang.MESSAGE_SENT);
							} else {
								LangUtils.sendError(sender, Lang.PLAYER_NOT_FOUND);
							}
						} else {
							CommandInfo.sendUsage(sender, label, CommandInfo.PM);
						}
					} else {
						LangUtils.noPerm(sender);
					}
					break;
				case "info":
					LangUtils.sendInfo(sender);
					break;
				case "help":
					CommandInfo.listCommands(sender, label);
					break;
				default:
					LangUtils.sendError(sender, Lang.UNKNOWN_COMMAND);
					break;
			}
		} else {
			CommandInfo.listCommands(sender, label);
		}
		return true;
	}
}
