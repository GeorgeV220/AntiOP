package me.Artlie.AntiOP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiOP extends JavaPlugin implements Listener, CommandExecutor {

	public ConfigManager config = new ConfigManager(this, "config.yml", "config.yml");
	public static AntiOP instance;
	public Metrics metrics;

	@SuppressWarnings("unused")
	public void onEnable() {
		instance = this;
		if (config.getBoolean("Updater")) {
			new Updater(this, 10543, "https://www.spigotmc.org/resources/antiop.10543/");
		}
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("antiop").setExecutor(new Commands());

		int scheduleSyncRepeatingTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new Runnable() {
					@SuppressWarnings("unlikely-arg-type")
					public void run() {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if ((!config.getStringList("Ops").contains(p.getName()))
									&& (!config.getStringList("Ops").contains(p.getUniqueId())) && (p.isOp())) {
								p.setOp(false);
								getServer().dispatchCommand(getServer().getConsoleSender(),
										config.getString("Command").replace("{playername}", p.getName()));
							}
						}
					}
				}, 20L, 20L);
		metrics = new Metrics(this);
	}

	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = (Player) event.getPlayer();
		if ((!config.getStringList("Ops").contains(p.getName()))
				&& (!config.getStringList("Ops").contains(p.getUniqueId())) && (p.isOp())) {
			p.setOp(false);
			getServer().dispatchCommand(getServer().getConsoleSender(),
					config.getString("Command").replace("{playername}", p.getName()));
		}
	}

	public String color(String s) {
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
}
