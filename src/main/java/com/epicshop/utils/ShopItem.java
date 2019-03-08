package com.epicshop.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.epicshop.configs.ConfigItem;
import com.epicshop.configs.ConfigManager;

public class ShopItem {
	private final String PATH_XP_LEVELS = ".price.xp-levels";
	private final String PATH_PRICE_BUY = ".price.buy";
	private final String PATH_PRICE_SELL = ".price.sell";
	private final String PATH_SLOT = ".slot";

	private Player p;
	private ConfigManager config;
	private YamlConfiguration yamlConfig;
	private String path;
	
	private int levels;
	private int price;
	private int sell;
	private int slot;
	
	private boolean getFromConfig;

	public ShopItem(Player p, ConfigManager config, String path, boolean getFromConfig) {
		this.p = p;
		this.config = config;
		this.path = path;
		this.yamlConfig = config.getConfig();
		this.getFromConfig = getFromConfig;
		
		if (yamlConfig.isSet(path + PATH_XP_LEVELS)) {
			levels = yamlConfig.getInt(path + PATH_XP_LEVELS);
		}
		
		if (yamlConfig.isSet(path + PATH_PRICE_BUY)) {
			price = yamlConfig.getInt(path + PATH_PRICE_BUY);
		}
		
		if (yamlConfig.isSet(path + PATH_PRICE_SELL)) {
			sell = yamlConfig.getInt(path + PATH_PRICE_SELL);
		}
		
		if (yamlConfig.isSet(path + PATH_SLOT)) {
			slot = yamlConfig.getInt(path + PATH_SLOT);
		}
	}
	
	public String getPath() {
		return path;
	}

	public ItemStack getItemStack() {
		ItemStack item;
		if (getFromConfig) {
			item = new ConfigItem(config).getItemStack(p, path);
		} else {
			item = new ItemStack(Material.BAKED_POTATO);
		}
		
		/*if (xpLevelsSet() || buyPriceSet() || sellPriceSet()) {
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
		}*/
		return item;
	}

	public int getXpLevelsPrice() {
		return levels;
	}

	public int getBuyPrice() {
		return price;
	}

	public int getSellPrice() {
		return sell;
	}

	public int getSlot() {
		return slot;
	}
}
