package com.epicshop.inv;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.epicshop.utils.Utils;

public class InvBuilder {
	private ItemStack item;
	private Material material;
	
	public InvBuilder() {
		material = Material.BEEF;
		item = new ItemStack(material);
	}
	
	public Inventory getInv() {
		Inventory inv = Bukkit.createInventory(null, 54, "Item Builder");
		inv.setItem(0, Utils.item("&fSelect Material", "&fLeft Click &7to Browse Materials\n&fRight Click &7to Select ItemStack", Material.FEATHER));
		
		inv.setItem(22, item);
		return inv;
	}
	
	public void setMaterial(Material material) {
		item.setType(material);
	}
	
	public void addFlag(ItemFlag flag) {
		item.getItemMeta().addItemFlags(flag);
	}
	
	public void removeFlag(ItemFlag flag) {
		item.getItemMeta().removeItemFlags(flag);
	}
}
