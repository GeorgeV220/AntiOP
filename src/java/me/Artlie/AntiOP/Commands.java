package me.Artlie.AntiOP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

	AntiOP m = AntiOP.instance;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("antiop")) {
			if (!sender.hasPermission("antiop.use")) {
				sender.sendMessage(m.color("&c[&4AntiOP&c] &eNo Permissions"));
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage(m.color("&e======&c[&4AntiOP&c]&e======"));
				sender.sendMessage(m.color("&eCommands: /antiop reload &8Reload Plugin"));
				sender.sendMessage(m.color("&e======&c[&4AntiOP&c]&e======"));
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				m.config.reload();
				sender.sendMessage(m.color("&c[&4AntiOP&c] &ePlugin reloaded"));
				Bukkit.getPluginManager().disablePlugin(m);
				Bukkit.getPluginManager().enablePlugin(m);
				return true;
			}
		}
		return true;
	}

}
