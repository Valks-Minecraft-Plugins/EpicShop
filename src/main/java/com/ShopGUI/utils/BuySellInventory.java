package com.ShopGUI.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ShopGUI.ShopGUI;

public class BuySellInventory {
	private YamlConfiguration buttonsConfig;
	private ShopItem item;
	private Player p;
	
	public BuySellInventory(Player p, ShopItem item) {
		this.buttonsConfig = ShopGUI.plugin.getButtonsConfig();
		this.item = item;
		this.p = p;
	}
	
	public Inventory getInv() {
		int rows = buttonsConfig.getInt("buttons.buy_sell_inventory.rows");
		int size = rows * 9;
		Inventory inv = Bukkit.createInventory(null, size, Utils.color(buttonsConfig.getString("buttons.buy_sell_inventory.name")));
		inv.setItem(getItemRemove1().getSlot() - 1, getItemRemove1().getItemStack());
		inv.setItem(getItemRemove10().getSlot() - 1, getItemRemove10().getItemStack());
		inv.setItem(getItemSet1().getSlot() - 1, getItemSet1().getItemStack());
		inv.setItem(buttonsConfig.getInt("buttons.buy_sell_inventory.product.slot") - 1, item.getItemStack());
		inv.setItem(getItemSet64().getSlot() - 1, getItemSet64().getItemStack());
		inv.setItem(getItemAdd10().getSlot() - 1, getItemAdd10().getItemStack());
		inv.setItem(getItemAdd1().getSlot() - 1, getItemAdd1().getItemStack());
		
		inv.setItem(getItemCancel().getSlot() - 1, getItemCancel().getItemStack());
		inv.setItem(getItemConfirm().getSlot() - 1, getItemConfirm().getItemStack());
		inv.setItem(getItemSellAll().getSlot() - 1, getItemSellAll().getItemStack());
		return inv;
	}
	
	public ShopItem getItemSellAll() {
		return new ShopItem(p, buttonsConfig, "buttons.sellall");
	}
	
	public ShopItem getItemConfirm() {
		return new ShopItem(p, buttonsConfig, "buttons.confirm");
	}
	
	public ShopItem getItemCancel() {
		return new ShopItem(p, buttonsConfig, "buttons.cancel");
	}
	
	public ShopItem getItemRemove1() {
		return new ShopItem(p, buttonsConfig, "buttons.remove1");
	}
	
	public ShopItem getItemRemove10() {
		return new ShopItem(p, buttonsConfig, "buttons.remove10");
	}
	
	public ShopItem getItemSet1() {
		return new ShopItem(p, buttonsConfig, "buttons.set1");
	}
	
	public ShopItem getItemAdd1() {
		return new ShopItem(p, buttonsConfig, "buttons.add1");
	}
	
	public ShopItem getItemAdd10() {
		return new ShopItem(p, buttonsConfig, "buttons.add10");
	}
	
	public ShopItem getItemSet64() {
		return new ShopItem(p, buttonsConfig, "buttons.set64");
	}
}
