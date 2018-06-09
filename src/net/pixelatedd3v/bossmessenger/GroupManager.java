package net.pixelatedd3v.bossmessenger;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.ui.commands.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class GroupManager {

	public static boolean hasPermission(CommandSender sender, String permission) {
		if ((sender.hasPermission("*")) || (sender.hasPermission(permission))) {
			return true;
		}
		for (int i = 0; i > -1; i++) {
			i = permission.indexOf(".", i);
			if (i <= -1) {
				break;
			}
			if (sender.hasPermission(permission.substring(0, i) + ".*")) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasAssignedPermission(CommandSender sender, String permission) {
		return !Settings.USE_MESSAGE_LIST_PERMISSIONS || sender.hasPermission(new Permission(permission, PermissionDefault.FALSE));
	}

	public static boolean hasPermission(CommandSender sender, CommandInfo command) {
		return hasPermission(sender, Reference.PERMISSION_COMMAND + command.getCommand());
	}
}
