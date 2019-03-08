package com.epicshop.inv;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.epicshop.EpicShop;
import com.epicshop.configs.ConfigManager;
import com.epicshop.utils.ShopItem;
import com.epicshop.utils.Utils;

public class InvBuySell {
	private ConfigManager buttonsConfigManager;
	private YamlConfiguration buttonsConfig;
	private ShopItem item;
	private Player p;

	public InvBuySell(Player p, ShopItem item) {
		this.buttonsConfigManager = EpicShop.buttons;
		this.buttonsConfig = EpicShop.buttons.getConfig();
		this.item = item;
		this.p = p;
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

	public ShopItem getItemSellAll() {
		return new ShopItem(p, buttonsConfigManager, "buttons.sellall", true);
	}

	public ShopItem getItemConfirm() {
		return new ShopItem(p, buttonsConfigManager, "buttons.confirm", true);
	}

	public ShopItem getItemCancel() {
		return new ShopItem(p, buttonsConfigManager, "buttons.cancel", true);
	}

	public ShopItem getItemRemove1() {
		return new ShopItem(p, buttonsConfigManager, "buttons.remove1", true);
	}

	public ShopItem getItemRemove10() {
		return new ShopItem(p, buttonsConfigManager, "buttons.remove10", true);
	}

	public ShopItem getItemSet1() {
		return new ShopItem(p, buttonsConfigManager, "buttons.set1", true);
	}

	public ShopItem getItemAdd1() {
		return new ShopItem(p, buttonsConfigManager, "buttons.add1", true);
	}

	public ShopItem getItemAdd10() {
		return new ShopItem(p, buttonsConfigManager, "buttons.add10", true);
	}

	public ShopItem getItemSet64() {
		return new ShopItem(p, buttonsConfigManager, "buttons.set64", true);
	}
	
	public ShopItem getItemInfo() {
		return new ShopItem(p, buttonsConfigManager, "buttons.buy_sell_info", true);
	}
}
