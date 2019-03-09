package com.epicshop.inv;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.epicshop.EpicShop;
import com.epicshop.configs.ConfigManager;
import com.epicshop.configs.FromConfigShopItem;
import com.epicshop.utils.Utils;

public class InvBuySell {
	private ConfigManager buttonsConfigManager;
	private YamlConfiguration buttonsConfig;
	private FromConfigShopItem item;

	public InvBuySell(FromConfigShopItem item) {
		this.buttonsConfigManager = EpicShop.buttons;
		this.buttonsConfig = EpicShop.buttons.getConfig();
		this.item = item;
	}

	public Inventory getInv() {
		int rows = buttonsConfig.getInt("buttons.buy_sell_inventory.rows");
		int size = rows * 9;
		Inventory inv = Bukkit.createInventory(null, size,
				Utils.color(buttonsConfig.getString("buttons.buy_sell_inventory.name")));
		inv.setItem(getItemRemove1().getSlot() - 1, getItemRemove1().getItemStack());
		inv.setItem(getItemRemove10().getSlot() - 1, getItemRemove10().getItemStack());
		inv.setItem(getItemSet1().getSlot() - 1, getItemSet1().getItemStack());
		inv.setItem(getItemProductSlot() - 1, item.getItemStack());
		inv.setItem(getItemSet64().getSlot() - 1, getItemSet64().getItemStack());
		inv.setItem(getItemAdd10().getSlot() - 1, getItemAdd10().getItemStack());
		inv.setItem(getItemAdd1().getSlot() - 1, getItemAdd1().getItemStack());
		
		inv.setItem(getItemInfo().getSlot() - 1, getItemInfo().getItemStack());

		inv.setItem(getItemCancel().getSlot() - 1, getItemCancel().getItemStack());
		inv.setItem(getItemConfirm().getSlot() - 1, getItemConfirm().getItemStack());
		inv.setItem(getItemSellAll().getSlot() - 1, getItemSellAll().getItemStack());
		return inv;
	}
	
	public int getItemProductSlot() {
		return buttonsConfig.getInt("buttons.buy_sell_inventory.product.slot");
	}
	
	public ItemStack getItemProductItemStack() {
		return item.getItemStack();
	}

	public FromConfigShopItem getItemSellAll() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.sellall");
	}

	public FromConfigShopItem getItemConfirm() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.confirm");
	}

	public FromConfigShopItem getItemCancel() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.cancel");
	}

	public FromConfigShopItem getItemRemove1() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.remove1");
	}

	public FromConfigShopItem getItemRemove10() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.remove10");
	}

	public FromConfigShopItem getItemSet1() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.set1");
	}

	public FromConfigShopItem getItemAdd1() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.add1");
	}

	public FromConfigShopItem getItemAdd10() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.add10");
	}

	public FromConfigShopItem getItemSet64() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.set64");
	}
	
	public FromConfigShopItem getItemInfo() {
		return new FromConfigShopItem(buttonsConfigManager, "buttons.buy_sell_info");
	}
}
