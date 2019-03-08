package com.epicshop.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.epicshop.EpicShop;

public class Utils {
	public static void updateProductLore(ItemStack info, ShopItem item, ItemStack product) {
		ItemMeta infoMeta = info.getItemMeta();
		List<String> infoLore = new ArrayList<String>();
		infoLore.add(Utils.color("&tPrice: &q" + item.getBuyPrice() * product.getAmount()));
		infoMeta.setLore(infoLore);
		info.setItemMeta(infoMeta);	
	}
	
	public static void setInvItem(Player p, Inventory inv, int size, ShopItem item) {
		int slot = item.getSlot();

		try {
			slot--;
			if (slot == -1)
				throw new NumberFormatException();
			inv.setItem(slot, item.getItemStack());
		} catch (NumberFormatException e) {
			sendError(p, e, "Slot and row values must be valid numbers!");
		} catch (ArrayIndexOutOfBoundsException e) {
			sendError(p, e,
					"Inventory only has a size of " + size + " but "
							+ item.getItemStack().getType().name().toLowerCase() + " item is trying to be set in slot "
							+ slot + "!");
		}
	}

	public static void sendError(Player p, Exception e, String message) {
		ConsoleCommandSender console = EpicShop.plugin.getServer().getConsoleSender();
		console.sendMessage(color("&4Error: &c" + message));

		console.sendMessage(color("&c" + e + " (Send this error to valk#3277 over discord for additional help!)"));
		StackTraceElement[] stackTraceElement = e.getStackTrace();
		for (int i = 0; i < stackTraceElement.length; i++) {
			if (stackTraceElement[i].toString().startsWith("com"))
				console.sendMessage(color("&c\t" + stackTraceElement[i]));
		}

		p.sendMessage(color("&4Error: &c" + message + " Check the console for more details."));
		// str.replaceAll("[\u00a7 c]", "");
	}

	public static String color(String message) {
		YamlConfiguration globalConfig = EpicShop.global.getConfig();
		message = message.replaceAll("&q", globalConfig.getString("color.primary"));
		message = message.replaceAll("&w", globalConfig.getString("color.secondary"));
		message = message.replaceAll("&t", globalConfig.getString("color.tertiary"));
		String startColor = globalConfig.getString("color.start_color");
		return ChatColor.translateAlternateColorCodes('&', startColor + message);
	}
}
