package com.epicshop.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.epicshop.EpicShop;
import com.epicshop.configs.ConfigItem;
import com.epicshop.inv.InvCategories;
import com.epicshop.utils.Utils;

//sender.sendMessage((String) config.getMapList("shop.shops").get(0).get("tester"));
public class CmdShop implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("shop")) {

			// YamlConfiguration shopConfig = plugin.getShopConfig();
			YamlConfiguration messagesConfig = EpicShop.messages.getConfig();

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
				
				EpicShop.shopEditMode.put(p.getUniqueId(), false);

				Inventory inv = new InvCategories(p).getInv();

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
				
				EpicShop.shopEditMode.put(p.getUniqueId(), true);
				
				Inventory inv = new InvCategories(p).getInv();

				if (inv != null)
					p.openInventory(inv);

				return true;
			}

			/*
			 * Reload plugin configs.
			 */
			if (args[0].equalsIgnoreCase("reload")) {
				if (args.length < 2) {
					EpicShop.reloadAllConfigs();
					sender.sendMessage(Utils.color("&tReloaded all EpicShop configs."));
					return true;
				}

				switch (args[1].toLowerCase()) {
				case "buttons":
					EpicShop.buttons.reloadConfig();
					sender.sendMessage(Utils.color("&tReloaded buttons.yml config."));
					break;
				case "global":
					EpicShop.global.reloadConfig();
					sender.sendMessage(Utils.color("&tReloaded global.yml config."));
					break;
				case "messages":
					EpicShop.messages.reloadConfig();
					sender.sendMessage(Utils.color("&tReloaded messages.yml config."));
					break;
				case "permissions":
					EpicShop.permissions.reloadConfig();
					sender.sendMessage(Utils.color("&tReloaded permissions.yml config."));
					break;
				case "shop":
					EpicShop.shops.reloadConfig();
					sender.sendMessage(Utils.color("&tReloaded shop.yml config."));
					break;
				case "signs":
					EpicShop.signs.reloadConfig();
					sender.sendMessage(Utils.color("&tReloaded signs.yml config."));
					break;
				default:
					EpicShop.reloadAllConfigs();
					sender.sendMessage(Utils.color("&tReloaded all EpicShop configs."));
					break;
				}
				return true;
			}

			return true;
		}

		return false;
	}
}
