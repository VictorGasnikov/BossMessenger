package net.pixelatedd3v.bossmessenger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater {
	public static String getVersion(int resource) {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource).getBytes("UTF-8"));
			String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
			con.disconnect();
			return version;
		} catch (Exception ex) {
			return null;
		}
	}
}
