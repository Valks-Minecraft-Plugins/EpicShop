package com.ShopGUI.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ShopGUI.ShopGUI;

public class Utils {
	public static Inventory getShopCategories(Player p) {
		YamlConfiguration shopConfig = ShopGUI.plugin.getShopConfig();
		
		int rows = shopConfig.getInt("catalog.rows");
		String name = shopConfig.getString("catalog.name");
		
		int size = rows * 9;
		
		ConfigurationSection cs = shopConfig.getConfigurationSection("catalog.categories");
		
		Inventory inv = null;
		
		try {
			inv = Bukkit.createInventory(null, rows * 9, Utils.color(name));
			for (String key : cs.getKeys(false)) {
				String path = "catalog.categories." + key;
				int slot = shopConfig.getInt(path + ".slot");
				setInvItem(p, inv, size, slot, getConfigItem(p, shopConfig, path + ".category_item"));
			}
		} catch (NullPointerException e) {
			sendError(p, e, "Looks like you made a type error somewhere in the shops.yml!");
		}
		
		return inv;
	}
	
	public static ItemStack getConfigItem(Player p, YamlConfiguration config, String path) {
		String materialName = config.getString(path + ".material");
		String itemName = config.getString(path + ".name");
		List<String> configLore = config.getStringList(path + ".lore");
		List<String> formattedLore = new ArrayList<String>();
		List<String> itemFlags = config.getStringList(path + ".item_flags");
		int quantity = config.getInt(path + ".quantity");
		
		if (!config.isSet(path + ".quantity"))
			quantity = 1;
		
		Material material = null;
		
		try {
			material = Material.valueOf(materialName);
		} catch (IllegalArgumentException e) {
			sendError(p, e, "You made a type error! " + e.getMessage());
			material = Material.AIR;
		}
		
		if (itemName == null) {
			itemName = "";
			String[] words = material.name().toLowerCase().split("_");
			for (int i = 0; i < words.length; i++) {
				itemName += words[i].substring(0, 1).toUpperCase() + words[i].substring(1) + (i == words.length - 1 ? "" : " ");
			}
		}
		
		ItemStack item = new ItemStack(material, quantity);
		ItemMeta im = item.getItemMeta();
		
		if (im != null) {
			im.setDisplayName(color(itemName));
			if (configLore != null) {
				for (String element : configLore)
					formattedLore.add(color(element));
				im.setLore(formattedLore);
			}
			
			if (itemFlags != null) {
				for (String element: itemFlags) {
					im.addItemFlags(ItemFlag.valueOf(element.toUpperCase()));
				}
			}
			
			ConfigurationSection sectionEnchants = config.getConfigurationSection(path + ".enchants");
			if (sectionEnchants != null) {
				for (String element : sectionEnchants.getKeys(false)) {
					int level = config.getInt(path + ".enchants." + element + ".level");
					Enchantment enchant = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(element.toLowerCase()));
		
					im.addEnchant(enchant, level, true);
				}
			}
		
			item.setItemMeta(im);
		}
		
		return item;
	}
	
	public static Inventory getShopCategory(Player p, String path) {
		YamlConfiguration shopConfig = ShopGUI.plugin.getShopConfig();
		
		String name = shopConfig.getString(path + ".name");
		int rows = shopConfig.getInt(path + ".rows");
		
		ConfigurationSection items = shopConfig.getConfigurationSection(path + ".items");
		
		int size = rows * 9;
		
		Inventory inv = Bukkit.createInventory(null, rows * 9, color(name));
		
		for (String element : items.getKeys(false)) {
			String root = path + ".items." + element;
			int slot = shopConfig.getInt(root + ".slot");
			ItemStack item = getConfigItem(p, shopConfig, root);
			
			setInvItem(p, inv, size, slot, item);
		}
		
		return inv;
	}
	
	public static void setInvItem(Player p, Inventory inv, int size, int slot, ItemStack item) {
		try {
			slot--;
			if (slot == -1)
				throw new NumberFormatException();
			inv.setItem(slot, item);
		} catch (NumberFormatException e) {
			sendError(p, e, "Slot and row values must be valid numbers!");
		} catch (ArrayIndexOutOfBoundsException e) {
			sendError(p, e, "Inventory only has a size of " + size + " but " + item.getType().name().toLowerCase() 
					+ " item is trying to be set in slot " + slot + "!");
		}
	}
	
	public static void sendError(Player p, Exception e, String message) {
		ConsoleCommandSender console = ShopGUI.plugin.getServer().getConsoleSender();
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
		YamlConfiguration globalConfig = ShopGUI.plugin.getGlobalConfig();
		message = message.replaceAll("&q", globalConfig.getString("color.primary"));
		message = message.replaceAll("&w", globalConfig.getString("color.secondary"));
		message = message.replaceAll("&t", globalConfig.getString("color.tertiary"));
		String startColor = globalConfig.getString("color.start_color");
		return ChatColor.translateAlternateColorCodes('&', startColor + message);
	}
	
	public static void saveConfig(File file, YamlConfiguration config) {
		ShopGUI plugin = ShopGUI.plugin;
		File checkFile = new File(plugin.getDataFolder() + "/" + file.getName());
		if (checkFile.exists()) {
			saveFile(file, config);
		} else {
			plugin.saveResource(file.getName(), false);
		}
	}
	
	private static void saveFile(File file, YamlConfiguration config) {
		try {
			config.save(file);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
