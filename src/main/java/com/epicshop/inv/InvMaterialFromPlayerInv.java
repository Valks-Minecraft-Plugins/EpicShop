package com.epicshop.inv;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvMaterialFromPlayerInv {
	public InvMaterialFromPlayerInv() {
		
	}
	
	public Inventory getInv() {
		Inventory inv = Bukkit.createInventory(null, 9, "Material Player Chooser");
		for (int i = 0; i < inv.getSize(); i++) {
			if (i == 4) continue;
			inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		}
		return inv;
	}
}
