package com.ShopGUI.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ShopGUI.ShopGUI;
import com.ShopGUI.utils.Utils;
//sender.sendMessage((String) config.getMapList("shop.shops").get(0).get("tester"));
public class CommandShop implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("shop")) {
			
			ShopGUI plugin = ShopGUI.plugin;
			//YamlConfiguration shopConfig = plugin.getShopConfig();
			YamlConfiguration messagesConfig = plugin.getMessagesConfig();
			
			boolean messageEnabledOpeningShop = messagesConfig.getBoolean("messages.opening_shop.enabled");
			boolean messageEnabledEditingShop = messagesConfig.getBoolean("messages.editing_shop.enabled");
			
			String messageOpeningShop = messagesConfig.getString("messages.opening_shop.message");
			String messageEditingShop = messagesConfig.getString("messages.editing_shop.message");
			
			String messageHelp = messagesConfig.getString("messages.help.message");
			
			Player p = Bukkit.getPlayer(sender.getName());
			
			/*
			 * Open the shop.
			 */
			if (args.length < 1) {
				if (messageEnabledOpeningShop)
					sender.sendMessage(Utils.color(messageOpeningShop));
				
				Inventory inv = Utils.getShopCategories(p);
				
				if (inv != null)
					p.openInventory(inv);
				
				return true;
			}
			
			/*
			 * Help message.
			 */
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(Utils.color(messageHelp));
				return true;
			}
			
			/*
			 * Edit the shop.
			 */
			if (args[0].equalsIgnoreCase("edit")) {
				if (messageEnabledEditingShop)
					sender.sendMessage(Utils.color(messageEditingShop));
				
				return true;
			}

			return true;
		}
		
		return false;
	}
}
