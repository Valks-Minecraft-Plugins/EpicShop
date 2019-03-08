package com.epicshop.inv;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.epicshop.EpicShop;
import com.epicshop.utils.ShopItem;
import com.epicshop.utils.Utils;

public class InvCategory {
	private Player p;
	private String path;

	public InvCategory(Player p, String path) {
		this.p = p;
		this.path = path;
	}

	public Inventory getInv() {
		YamlConfiguration shopConfig = EpicShop.shops.getConfig();

		String name = shopConfig.getString(path + ".name");
		int rows = shopConfig.getInt(path + ".rows");

		ConfigurationSection items = shopConfig.getConfigurationSection(path + ".items");

		int size = rows * 9;

		Inventory inv = Bukkit.createInventory(null, rows * 9, Utils.color(name));

		for (String element : items.getKeys(false)) {
			ShopItem item = new ShopItem(p, shopConfig, path + ".items." + element);
			Utils.setInvItem(p, inv, size, item);
		}

		YamlConfiguration buttonsConfig = EpicShop.buttons.getConfig();
		ShopItem itemBack = new ShopItem(p, buttonsConfig, "buttons.back");
		int slotsFromBottom = buttonsConfig.getInt("buttons.back.slots_from_bottom");
		inv.setItem(inv.getSize() - slotsFromBottom, itemBack.getItemStack());

		return inv;
	}
}
