package com.ShopGUI.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopItem {
	private final String PATH_XP_LEVELS = ".price.xp-levels";
	private final String PATH_PRICE_BUY = ".price.buy";
	private final String PATH_PRICE_SELL = ".price.sell";
	private final String PATH_SLOT = ".slot";
	
	private Player p;
	private YamlConfiguration config;
	private String path;
	
	public ShopItem(Player p, YamlConfiguration config, String path) {
		this.p = p;
		this.config = config;
		this.path = path;
	}
	
	public ItemStack getItemStack() {
		ItemStack item = Utils.getConfigItem(p, config, path);
		if (xpLevelsSet() || buyPriceSet() || sellPriceSet()) {
			ItemMeta im = item.getItemMeta();
			List<String> lore = im.getLore();
			if (lore == null)
				lore = new ArrayList<String>();
			lore.add(" ");
			if (buyPriceSet())
				lore.add(Utils.color("Buy: &f" + getBuyPrice()));
			if (sellPriceSet())
				lore.add(Utils.color("Sell: &f" + getSellPrice()));
			if (xpLevelsSet())
				lore.add(Utils.color("Lvls: &f" + getXpLevelsPrice()));
			im.setLore(lore);
			item.setItemMeta(im);
		}
		return item;
	}
	
	public boolean xpLevelsSet() {
		return config.isSet(path + PATH_XP_LEVELS);
	}
	
	public boolean buyPriceSet() {
		return config.isSet(path + PATH_PRICE_BUY);
	}
	
	public boolean sellPriceSet() {
		return config.isSet(path + PATH_PRICE_SELL);
	}
	
	public boolean slotSet() {
		return config.isSet(path + PATH_SLOT);
	}
	
	public int getXpLevelsPrice() {
		return config.getInt(path + PATH_XP_LEVELS);
	}
	
	public int getBuyPrice() {
		return config.getInt(path + PATH_PRICE_BUY);
	}
	
	public int getSellPrice() {
		return config.getInt(path + PATH_PRICE_SELL);
	}
	
	public int getSlot() {
		return config.getInt(path + PATH_SLOT);
	}
}
