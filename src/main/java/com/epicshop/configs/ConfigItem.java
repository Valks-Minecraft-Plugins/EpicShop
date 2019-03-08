package com.epicshop.configs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.epicshop.utils.Utils;

public class ConfigItem {
	private Player p;
	private YamlConfiguration config;
	private String path;

	public ConfigItem(Player p, YamlConfiguration config, String path) {
		this.p = p;
		this.config = config;
		this.path = path;
	}

	public ItemStack getItemStack() {
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
			Utils.sendError(p, e, "You made a type error! " + e.getMessage());
			material = Material.AIR;
		}

		if (itemName == null) {
			itemName = "";
			String[] words = material.name().toLowerCase().split("_");
			for (int i = 0; i < words.length; i++) {
				itemName += words[i].substring(0, 1).toUpperCase() + words[i].substring(1)
						+ (i == words.length - 1 ? "" : " ");
			}
		}

		ItemStack item = new ItemStack(material, quantity);
		ItemMeta im = item.getItemMeta();

		if (im != null) {
			im.setDisplayName(Utils.color(itemName));
			if (configLore != null) {
				for (String element : configLore)
					formattedLore.add(Utils.color(element));
				im.setLore(formattedLore);
			}

			if (itemFlags != null) {
				for (String element : itemFlags) {
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
}
