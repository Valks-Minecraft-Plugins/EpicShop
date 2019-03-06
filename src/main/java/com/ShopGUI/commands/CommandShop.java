package com.ShopGUI.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ShopGUI.ShopGUI;

public class CommandShop implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("shop")) {
			
			ShopGUI plugin = ShopGUI.plugin;
			YamlConfiguration config = plugin.getShopConfig();
			
			if (args.length < 1) {
				sender.sendMessage("Opening shop...");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage("ShopGUIReborn Help"
						+ "\n"
						+ "\n/shop"
						+ "\n/shop create [category]");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("create")) {
				if (args.length < 2) {
					sender.sendMessage("/shop create [category]");
					return true;
				}
				
				sender.sendMessage("Created shop category named " + args[1] + "!");
				
				//sender.sendMessage((String) config.getMapList("shop.shops").get(0).get("tester"));
				
				config.set("shops.food.test", true);
				
				plugin.saveShopConfig();
				
				return true;
			}

			return true;
		}
		
		return false;
	}
}
