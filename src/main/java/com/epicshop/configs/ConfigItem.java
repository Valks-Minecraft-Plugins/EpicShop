package com.epicshop.configs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.epicshop.utils.ShopItem;
import com.epicshop.utils.Utils;

public class ConfigItem {
	private ConfigManager config;

	public ConfigItem(ConfigManager config) {
		this.config = config;
	}
	
	public void removeConfigItem(String path) {
		config.getConfig().set(path, null);
		config.saveConfig();
	}
	
	public void setConfigItem(ShopItem shopItem, int slot, String path) {
		ItemStack item = shopItem.getItemStack();
		ItemMeta im = item.getItemMeta();
		
		String itemMaterialName = item.getType().name();
		String itemDisplayName = im.getDisplayName();
		
		List<String> itemLore = im.getLore();
		List<String> itemFlags = new ArrayList<String>();
		List<String> itemEnchants = new ArrayList<String>();
		
		int quantity = item.getAmount();
		
		for (ItemFlag flag : im.getItemFlags())
			itemFlags.add(flag.name());
		
		Map<Enchantment, Integer> enchantments = im.getEnchants();
		Iterator<Enchantment> iterator = enchantments.keySet().iterator();
		while(iterator.hasNext()) {
			Enchantment enchant = EnchantmentWrapper.getByKey(iterator.next().getKey());
			itemEnchants.add(enchant.getKey().getKey());
		}
		
		YamlConfiguration yamlConfig = config.getConfig();
		
		yamlConfig.set(path + ".material", itemMaterialName);
		yamlConfig.set(path + ".slot", slot);
		
		config.saveConfig();
	}

	public ItemStack getItemStack(Player p, String path) {
		YamlConfiguration yamlConfig = config.getConfig();
		String materialName = yamlConfig.getString(path + ".material");
		String itemName = yamlConfig.getString(path + ".name");
		List<String> configLore = yamlConfig.getStringList(path + ".lore");
		List<String> formattedLore = new ArrayList<String>();
		List<String> itemFlags = yamlConfig.getStringList(path + ".item_flags");
		int quantity = yamlConfig.getInt(path + ".quantity");

		if (!yamlConfig.isSet(path + ".quantity"))
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

			ConfigurationSection sectionEnchants = yamlConfig.getConfigurationSection(path + ".enchants");
			if (sectionEnchants != null) {
				for (String element : sectionEnchants.getKeys(false)) {
					int level = yamlConfig.getInt(path + ".enchants." + element + ".level");
					Enchantment enchant = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(element.toLowerCase()));

					im.addEnchant(enchant, level, true);
				}
			}

			item.setItemMeta(im);
		}

		return item;
	}
}
