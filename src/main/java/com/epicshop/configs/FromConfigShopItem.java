package com.epicshop.configs;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class FromConfigShopItem {
	private final String PATH_XP_LEVELS = ".price.xp-levels";
	private final String PATH_PRICE_BUY = ".price.buy";
	private final String PATH_PRICE_SELL = ".price.sell";
	private final String PATH_SLOT = ".slot";

	private ConfigManager config;
	private YamlConfiguration yamlConfig;
	private String path;
	
	private int levels;
	private int price;
	private int sell;
	private int slot;

	public FromConfigShopItem(ConfigManager config, String path) {
		this.config = config;
		this.path = path;
		this.yamlConfig = config.getConfig();
		
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
		ItemStack item = new ConfigItem(config).getItemStack(path);
		
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
