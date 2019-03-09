package com.epicshop.inv;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import com.epicshop.EpicShop;
import com.epicshop.configs.ConfigManager;
import com.epicshop.configs.FromConfigShopItem;
import com.epicshop.utils.Utils;

public class InvCategories {
	public InvCategories() {
		
	}

	public Inventory getInv() {
		ConfigManager shopConfigManager = EpicShop.shops;
		YamlConfiguration shopConfig = shopConfigManager.getConfig();

		int rows = shopConfig.getInt("catalog.rows");
		String name = shopConfig.getString("catalog.name");

		int size = rows * 9;

		ConfigurationSection cs = shopConfig.getConfigurationSection("catalog.categories");

		Inventory inv = null;

		try {
			inv = Bukkit.createInventory(null, rows * 9, Utils.color(name));
			for (String key : cs.getKeys(false)) {
				FromConfigShopItem item = new FromConfigShopItem(shopConfigManager, "catalog.categories." + key + ".category_item");
				Utils.setInvItem(inv, size, item);
			}
		} catch (NullPointerException e) {
			Utils.sendError(e, "Looks like you made a type error somewhere in the shops.yml!");
		}

		return inv;
	}
}
