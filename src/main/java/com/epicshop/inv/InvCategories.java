package com.epicshop.inv;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.epicshop.EpicShop;
import com.epicshop.configs.ConfigManager;
import com.epicshop.utils.ShopItem;
import com.epicshop.utils.Utils;

public class InvCategories {
	private Player p;

	public InvCategories(Player p) {
		this.p = p;
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
				ShopItem item = new ShopItem(p, shopConfigManager, "catalog.categories." + key + ".category_item", true);
				Utils.setInvItem(p, inv, size, item);
			}
		} catch (NullPointerException e) {
			Utils.sendError(p, e, "Looks like you made a type error somewhere in the shops.yml!");
		}

		return inv;
	}
}
