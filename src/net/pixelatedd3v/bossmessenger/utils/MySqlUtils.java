package net.pixelatedd3v.bossmessenger.utils;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.database.MySQL;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.message.MessageAttribute;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySqlUtils {
	public static MySQL sql = new MySQL(Settings.MYSQL_HOST, Settings.MYSQL_PORT, Settings.MYSQL_DATABASE, Settings.MYSQL_USERNAME, Settings.MYSQL_PASSWORD);
	public static final String TABLE_PREFIX = "bm_";

	public static List<TemplateMessage> loadMessagesFromMySql(String listname) {
		List<TemplateMessage> messages = new ArrayList<>();
		try {
			ResultSet result = sql.querySQL("SELECT * FROM " + TABLE_PREFIX + listname);
			while (result.next()) {
				List<MessageAttribute> attributes = new ArrayList<>();
				for (String str : result.getString("Percent").split(";")) {
					String[] ma = str.split(":");
					attributes.add(new MessageAttribute(ma[0], ma[1]));
				}

				messages.add(new TemplateMessage(result.getString("Text"), result.getInt("Show"), result.getInt("Interval"), attributes));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

	public static boolean tableExists(String tablename) {
		try {
			ResultSet exists = sql.querySQL("SHOW TABLES LIKE '" + tablename + "'");
			return exists.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean createTableForList(String listname) {
		try {
			String tablename = TABLE_PREFIX + listname;
			if (!tableExists(tablename)) {
				sql.updateSQL("CREATE TABLE " + tablename + " (Id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, Text VARCHAR(1000) NOT NULL, Percent VARCHAR(40) NOT NULL, `Show` INT NOT NULL, `Interval` INT NOT NULL);");
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public static boolean fillMySqlTable(MessageList list) {
		String tablename = TABLE_PREFIX + list.getName();
		List<TemplateMessage> messages = list.getMessages();
		if (messages.size() == 0) {
			return false;
		}
		try {
			sql.updateSQL("DELETE FROM " + tablename);
			for (TemplateMessage message : messages) {
				sql.updateSQL("INSERT INTO " + tablename + " (Text, Percent, Show, Interval) VALUES " + String.format("('%s', '%s', %n, %n)", message.getText(), message.getPercent().toString(), message.getShow(), message.getText()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public static boolean addMessage(String listname, TemplateMessage message) {
		String tablename = TABLE_PREFIX + listname;
		try {
			sql.updateSQL(String.format("INSERT INTO " + tablename + " (Text, Percent, Show, Interval) VALUES (`%s`, `%s`, %d, %d)", message.getText(), message.getPercent().toString(), message.getShow(), message.getInterval()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean dropTable(String listname) {
		String tablename = TABLE_PREFIX + listname;
		try {
			sql.updateSQL("DROP TABLE " + tablename);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
