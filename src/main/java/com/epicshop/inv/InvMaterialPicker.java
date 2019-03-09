package com.epicshop.inv;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.epicshop.EpicShop;

public class InvMaterialPicker {
	private Player p;
	
	public InvMaterialPicker(Player p) {
		if (!EpicShop.materialPage.containsKey(p.getUniqueId())) {
			EpicShop.materialPage.put(p.getUniqueId(), 1);
		}
		
		this.p = p;
	}
	
	public Inventory getInv() {
		Inventory inv = Bukkit.createInventory(null, 54, "Materials");
		inv.setItem(inv.getSize() - 1, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		inv.setItem(inv.getSize() - 5, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		inv.setItem(inv.getSize() - 9, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		
		int page = EpicShop.materialPage.get(p.getUniqueId());
		final int PAGE_SIZE = inv.getSize() - 9;
		
		int n = PAGE_SIZE * (page - 1);
		
		Material[] materials = Material.values();
		List<Material> items = new ArrayList<Material>();
		
		for (int i = 0; i < materials.length; i++) {
			if (materials[i].isItem()) {
				if (materials[i] != Material.AIR)
					items.add(materials[i]);
			}
		}
		
		for (int i = n; i < n + PAGE_SIZE; i++) {
			if (i < items.size()) {
				inv.setItem(i - n, new ItemStack(items.get(Math.min(items.size() - 1, i))));
			}
		}
		
		return inv;
	}
}
