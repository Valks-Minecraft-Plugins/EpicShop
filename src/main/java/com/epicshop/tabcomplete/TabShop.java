package com.epicshop.tabcomplete;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabShop implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("shop")) {
			if (sender instanceof Player) {

				if (args.length == 1 && args[0].length() == 0) {
					List<String> list = new ArrayList<String>();
					list.add("help");
					list.add("edit");
					list.add("reload");

					return list;
				}

				if (args.length > 1 && args[0].equalsIgnoreCase("reload") && args[1].length() == 0) {
					List<String> list = new ArrayList<String>();
					list.add("all");
					list.add("buttons");
					list.add("global");
					list.add("messages");
					list.add("permissions");
					list.add("shops");
					list.add("signs");

					return list;
				}
			}
		}
		return null;
	}
}
