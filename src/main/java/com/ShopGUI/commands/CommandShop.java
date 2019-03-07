package com.ShopGUI.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ShopGUI.ShopGUI;
import com.ShopGUI.utils.Utils;
//sender.sendMessage((String) config.getMapList("shop.shops").get(0).get("tester"));
public class CommandShop implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("shop")) {
			
			ShopGUI plugin = ShopGUI.plugin;
			YamlConfiguration shopConfig = plugin.getShopConfig();
			YamlConfiguration messagesConfig = plugin.getMessagesConfig();
			
			if (args.length < 1) {
				if (messagesConfig.getBoolean("messages.opening_shop.enabled"))
					sender.sendMessage(Utils.color(messagesConfig.getString("messages.opening_shop.message")));
				return true;
			}
			
			//
			
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(Utils.color("EpicShop Help"
						+ "\n "
						+ "\n/shop"
						+ "\n/shop create [category]"));
				return true;
			}
			
			if (args[0].equalsIgnoreCase("edit")) {
				if (messagesConfig.getBoolean("messages.editing_shop.enabled"))
					sender.sendMessage(Utils.color(messagesConfig.getString("messages.editing_shop.message")));
				
				return true;
			}

			return true;
		}
		
		return false;
	}
}
