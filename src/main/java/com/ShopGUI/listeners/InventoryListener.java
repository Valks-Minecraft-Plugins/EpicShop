package com.ShopGUI.listeners;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.ShopGUI.ShopGUI;
import com.ShopGUI.utils.Utils;

public class InventoryListener implements Listener {
	@EventHandler
	private void invCloseEvent(InventoryCloseEvent e) {
		
	}
	
	@EventHandler
	private void invInteractEvent(InventoryClickEvent e) {
		ShopGUI plugin = ShopGUI.plugin;
		YamlConfiguration shopConfig = plugin.getShopConfig();
		
		String categoriesTitle = shopConfig.getString("catalog.name");
		
		String title = e.getView().getTitle();
		int slot = e.getSlot() + 1;
		
		// Replace that stupid § (which is copied as a º) symbol in console to a &.
		title = title.replaceAll("\u00a7", "&");
		
		Player p = (Player) e.getWhoClicked();
		
		if (title.equals(categoriesTitle)) {
			e.setCancelled(true);
			ConfigurationSection categories = shopConfig.getConfigurationSection("catalog.categories");
			
			for (String element : categories.getKeys(false)) {
				
				int categorySlot = shopConfig.getInt("catalog.categories." + element + ".slot");
				
				if (slot == categorySlot) {
					Inventory inv = Utils.getShopCategory(p, "catalog.categories." + element + ".category");
					p.openInventory(inv);
					break;
				}
			}
		}
	}
}
