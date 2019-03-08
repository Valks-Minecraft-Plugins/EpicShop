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
import com.ShopGUI.utils.BuySellInventory;
import com.ShopGUI.utils.ShopItem;
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
		
		ConfigurationSection categories = shopConfig.getConfigurationSection("catalog.categories");
		
		/*
		 * Handle Buy Sell Inventory Click Events
		 */
		YamlConfiguration buttonsConfig = plugin.getButtonsConfig();
		if (title.equals(buttonsConfig.getString("buttons.buy_sell_inventory.name"))) {
			e.setCancelled(true);
			
			int itemSlot = buttonsConfig.getInt("buttons.buy_sell_inventory.product.slot");
			
			String itemName = e.getInventory().getItem(itemSlot - 1).getItemMeta().getDisplayName();
			itemName = itemName.replaceAll("\u00a7", "&");
			
			String itemPath = ShopGUI.buySellInvItemPath.get(p.getUniqueId());
			String categoryName = itemPath.substring(itemPath.indexOf("categories.") + "categories.".length()).split("\\.")[0];
			
			// The category page the player was previously browsing.
			Inventory prevInventory = Utils.getShopCategory(p, ("catalog.categories." + categoryName + ".category"));
			
			ShopItem item = new ShopItem(p, shopConfig, itemPath);
			BuySellInventory inv = new BuySellInventory(p, item);
			
			int slotAdd1 = inv.getItemAdd1().getSlot();
			int slotAdd10 = inv.getItemAdd10().getSlot();
			int slotSet64 = inv.getItemSet64().getSlot();
			int slotRemove1 = inv.getItemRemove1().getSlot();
			int slotRemove10 = inv.getItemRemove10().getSlot();
			int slotSet1 = inv.getItemSet1().getSlot();
			int slotCancel = inv.getItemCancel().getSlot();
			int slotConfirm = inv.getItemConfirm().getSlot();
			int slotSellAll = inv.getItemSellAll().getSlot();
			
			if (slot == slotAdd1) {
				
			}
			
			if (slot == slotAdd10) {
				
			}
			
			if (slot == slotSet64) {
				
			}
			
			if (slot == slotRemove1) {
				
			}
			
			if (slot == slotRemove10) {
				
			}
			
			if (slot == slotSet1) {
				
			}
			
			if (slot == slotCancel) {
				p.openInventory(prevInventory);
			}
			
			if (slot == slotConfirm) {
				
			}
			
			if (slot == slotSellAll) {
				
			}
		}
		
		/*
		 * Handle Per "Category" Click Events
		 */
		for (String categoryName : categories.getKeys(false)) {
			String pathToCategory = "catalog.categories." + categoryName + ".category";
			String categoryTitle = shopConfig.getString(pathToCategory + ".name");
			if (title.equals(categoryTitle)) {
				/*
				 * Handle Per "Items" in Per "Category" Click Events
				 */
				e.setCancelled(true);
				
				int backSlot = buttonsConfig.getInt("buttons.back.slots_from_bottom");
				if (slot == e.getInventory().getSize() - backSlot + 1) {
					p.openInventory(Utils.getShopCategories(p));
				}
				
				ConfigurationSection categoryItems = shopConfig.getConfigurationSection(pathToCategory + ".items");
				for (String itemName : categoryItems.getKeys(false)) {
					String pathToItem = pathToCategory + ".items." + itemName;
					int itemSlot = shopConfig.getInt(pathToItem + ".slot");
					if (slot == itemSlot) {
						// Open buy sell inventory.
						ShopGUI.buySellInvItemPath.put(p.getUniqueId(), pathToItem);
						
						ShopItem item = new ShopItem(p, shopConfig, pathToItem);
						BuySellInventory inv = new BuySellInventory(p, item);
						p.openInventory(inv.getInv());
						break;
					}
				}
				break;
			}
		}
		
		/*
		 * Handle "Categories" Click Events
		 */
		if (title.equals(categoriesTitle)) {
			e.setCancelled(true);
			
			for (String element : categories.getKeys(false)) {
				
				int categorySlot = shopConfig.getInt("catalog.categories." + element + ".category_item.slot");
				
				if (slot == categorySlot) {
					Inventory inv = Utils.getShopCategory(p, "catalog.categories." + element + ".category");
					p.openInventory(inv);
					break;
				}
			}
		}
	}
}
