package com.epicshop.inv;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import com.epicshop.EpicShop;
import com.epicshop.configs.ConfigManager;
import com.epicshop.configs.FromConfigShopItem;
import com.epicshop.utils.Utils;

public class InvCategory {
	private String path;

	public InvCategory(String path) {
		this.path = path;
	}

	public Inventory getInv() {
		ConfigManager shopsConfigManager = EpicShop.shops;
		YamlConfiguration shopConfig = shopsConfigManager.getConfig();
		
		ConfigManager buttonsConfigManager = EpicShop.buttons;
		YamlConfiguration buttonsConfig = buttonsConfigManager.getConfig();

		String name = shopConfig.getString(path + ".name");
		int rows = shopConfig.getInt(path + ".rows");

		ConfigurationSection items = shopConfig.getConfigurationSection(path + ".items");

		int size = rows * 9;

		Inventory inv = Bukkit.createInventory(null, rows * 9, Utils.color(name));

		for (String element : items.getKeys(false)) {
			FromConfigShopItem item = new FromConfigShopItem(shopsConfigManager, path + ".items." + element);
			Utils.setInvItem(inv, size, item);
		}

		
		FromConfigShopItem itemBack = new FromConfigShopItem(buttonsConfigManager, "buttons.back");
		int slotsFromBottom = buttonsConfig.getInt("buttons.back.slots_from_bottom");
		inv.setItem(inv.getSize() - slotsFromBottom, itemBack.getItemStack());

		return inv;
	}
}
