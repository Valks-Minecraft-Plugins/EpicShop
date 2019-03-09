package com.epicshop.configs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopItem {
	private ItemStack item;
	
	public ShopItem(Material material) {
		item = new ItemStack(material);
	}
}
