package me.Artlie.AntiOP;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.plugin.java.JavaPlugin;

public class Updater {

	public Updater(final JavaPlugin paramPlugin, final int paramPluginID, final String paramPluginURL) {

		paramPlugin.getServer().getScheduler().runTaskTimerAsynchronously(paramPlugin, new Runnable() {

			public void run() {

				final JavaPlugin PLUGIN = paramPlugin;

				final int PLUGIN_ID = paramPluginID;

				final String PLUGIN_URL = paramPluginURL;

				final String BASE_URL = "https://api.spigotmc.org/legacy/update.php?resource=" + PLUGIN_ID;

				final String localVersion = PLUGIN.getDescription().getVersion();

				String onlineVersion;

				PLUGIN.getLogger().info("Checking for Updates ... ");

				try {

					HttpsURLConnection con = (HttpsURLConnection) new URL(BASE_URL).openConnection();

					con.setDoOutput(true);

					con.setRequestMethod("GET");

					onlineVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

				} catch (Exception ex) {

					PLUGIN.getLogger().warning("Failed to check for an update on spigot.");

					PLUGIN.getLogger().warning("Either spigot or you are offline or are slow to respond.");

					return;

				}

				switch (versionCompare(localVersion, onlineVersion)) {

				case 0:

					PLUGIN.getLogger().info("You are running the newest stable build.");

					break;

				case 1:

					PLUGIN.getLogger().info("Your version is newer than the last stable build.");

					PLUGIN.getLogger()

							.info("Stable Version: " + onlineVersion + ". You are running version: " + localVersion);

					PLUGIN.getLogger().info("If you are experiencing issues please fall back to last stable build.");

					break;

				case -1:

					PLUGIN.getLogger().warning("New stable version availiable!");

					PLUGIN.getLogger()

							.warning("Stable Version: " + onlineVersion + ". You are running version: " + localVersion);

					PLUGIN.getLogger().warning("Update at: " + PLUGIN_URL);

					break;

				default:

					PLUGIN.getLogger().warning("Failed to check for an update on spigot.");

					PLUGIN.getLogger().warning("The versions are misbehaving.");

				}

			}

		}, 0L, 432000L);

	}

	public static int versionCompare(String paramVersion1, String paramVersion2) {

		String[] vals1 = paramVersion1.split("\\.");

		String[] vals2 = paramVersion2.split("\\.");

		int i = 0;

		while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {

			i++;

		}

		if (i < vals1.length && i < vals2.length) {

			int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));

			return Integer.signum(diff);

		}

		return Integer.signum(vals1.length - vals2.length);

	}

}