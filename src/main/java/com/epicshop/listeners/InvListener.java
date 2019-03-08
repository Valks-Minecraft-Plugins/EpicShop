package com.epicshop.listeners;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.epicshop.EpicShop;
import com.epicshop.configs.ConfigItem;
import com.epicshop.inv.InvBuySell;
import com.epicshop.inv.InvCategories;
import com.epicshop.inv.InvCategory;
import com.epicshop.utils.ShopItem;
import com.epicshop.utils.Utils;

public class InvListener implements Listener {
	@EventHandler
	private void invCloseEvent(InventoryCloseEvent e) {

	}

	@EventHandler
	private void invInteractEvent(InventoryClickEvent e) {
		Inventory inv = e.getInventory();

		String categoriesTitle = EpicShop.shops.getConfig().getString("catalog.name");

		String title = e.getView().getTitle();
		int slot = e.getSlot() + 1;

		// Replace that stupid § (which is copied as a º) symbol in console to a &.
		title = title.replaceAll("\u00a7", "&");

		Player p = (Player) e.getWhoClicked();

		ConfigurationSection categories = EpicShop.shops.getConfig().getConfigurationSection("catalog.categories");

		/*
		 * Handle Buy Sell Inventory Click Events
		 */
		YamlConfiguration buttonsConfig = EpicShop.buttons.getConfig();
		if (title.equals(buttonsConfig.getString("buttons.buy_sell_inventory.name"))) {
			e.setCancelled(true);

			int itemSlot = buttonsConfig.getInt("buttons.buy_sell_inventory.product.slot");

			String itemName = inv.getItem(itemSlot - 1).getItemMeta().getDisplayName();
			itemName = itemName.replaceAll("\u00a7", "&");

			String itemPath = EpicShop.buySellInvItemPath.get(p.getUniqueId());
			String categoryName = itemPath.substring(itemPath.indexOf("categories.") + "categories.".length())
					.split("\\.")[0];

			// The category page the player was previously browsing.
			Inventory prevInventory = new InvCategory(p, "catalog.categories." + categoryName + ".category").getInv();

			ShopItem item = new ShopItem(p, EpicShop.shops, itemPath, true);
			InvBuySell buySellInv = new InvBuySell(p, item);

			int slotAdd1 = buySellInv.getItemAdd1().getSlot();
			int slotAdd10 = buySellInv.getItemAdd10().getSlot();
			int slotSet64 = buySellInv.getItemSet64().getSlot();
			int slotRemove1 = buySellInv.getItemRemove1().getSlot();
			int slotRemove10 = buySellInv.getItemRemove10().getSlot();
			int slotSet1 = buySellInv.getItemSet1().getSlot();
			int slotCancel = buySellInv.getItemCancel().getSlot();
			int slotConfirm = buySellInv.getItemConfirm().getSlot();
			int slotSellAll = buySellInv.getItemSellAll().getSlot();
			int slotInfo = buySellInv.getItemInfo().getSlot();
			int slotProduct = buySellInv.getItemProductSlot();
			
			ItemStack product = inv.getItem(--slotProduct);
			ItemStack info = inv.getItem(--slotInfo);

			if (slot == slotProduct + 1) {
				if (e.getClick() == ClickType.LEFT) {
					product.setAmount(Math.min(product.getAmount() + 1, 64));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}
				
				if (e.getClick() == ClickType.RIGHT) {
					product.setAmount(Math.max(product.getAmount() - 1, 1));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}
			}

			if (e.getClick() == ClickType.LEFT) {
				if (slot == slotAdd1) {
					product.setAmount(Math.min(product.getAmount() + 1, 64));
					inv.setItem(slotProduct, product);
					Utils.updateProductLore(info, item, product);
				}

				if (slot == slotAdd10) {
					product.setAmount(Math.min(product.getAmount() + 10, 64));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == slotSet64) {
					product.setAmount(64);
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == slotRemove1) {
					product.setAmount(Math.max(product.getAmount() - 1, 1));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == slotRemove10) {
					product.setAmount(Math.max(product.getAmount() - 10, 1));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == slotSet1) {
					product.setAmount(1);
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == slotCancel) {
					p.openInventory(prevInventory);
				}

				if (slot == slotConfirm) {
					p.closeInventory();
				}

				if (slot == slotSellAll) {
					p.closeInventory();
				}
			}
		}

		/*
		 * Handle Per "Category" Click Events
		 */
		for (String categoryName : categories.getKeys(false)) {
			String pathToCategory = "catalog.categories." + categoryName + ".category";
			String categoryTitle = EpicShop.shops.getConfig().getString(pathToCategory + ".name");
			if (title.equals(categoryTitle)) {
				/*
				 * Handle Per "Items" in Per "Category" Click Events
				 */
				e.setCancelled(true);

				int backSlot = buttonsConfig.getInt("buttons.back.slots_from_bottom");
				if (slot == inv.getSize() - backSlot + 1) {
					p.openInventory(new InvCategories(p).getInv());
				}

				ConfigurationSection categoryItems = EpicShop.shops.getConfig().getConfigurationSection(pathToCategory + ".items");
				for (String itemName : categoryItems.getKeys(false)) {
					String pathToItem = pathToCategory + ".items." + itemName;
					int itemSlot = EpicShop.shops.getConfig().getInt(pathToItem + ".slot");
					if (slot == itemSlot) {
						// Open buy sell inventory.
						EpicShop.buySellInvItemPath.put(p.getUniqueId(), pathToItem);

						ShopItem item = new ShopItem(p, EpicShop.shops, pathToItem, true);
						InvBuySell buySellInv = new InvBuySell(p, item);
						p.openInventory(buySellInv.getInv());
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
			
			if (EpicShop.shopEditMode.get(p.getUniqueId())) {
				if (e.getClick() == ClickType.MIDDLE) {
					inv.setItem(e.getSlot(), new ItemStack(Material.PORKCHOP));
					ShopItem myItem = new ShopItem(p, EpicShop.shops, "catalog.categories.test.category_item", false);
					new ConfigItem(EpicShop.shops).setConfigItem(myItem, slot, myItem.getPath());
				}
				
				if (e.getClick() == ClickType.LEFT) {
					if (e.getAction() == InventoryAction.PICKUP_ALL) {
						String category = getClickedCategory(e);
						String pathToCategoryItem = "catalog.categories." + category + ".category_item";
						ShopItem item = new ShopItem(p, EpicShop.shops, pathToCategoryItem, true);
						EpicShop.shopMovingItem.put(p.getUniqueId(), item);
					}
					
					if (e.getAction() == InventoryAction.PLACE_ALL) {
						if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
							e.setCancelled(true);
						} else {
							ShopItem shopItem = EpicShop.shopMovingItem.get(p.getUniqueId());
							new ConfigItem(EpicShop.shops).setConfigItem(shopItem, slot, shopItem.getPath());
						}
					}
				}
				
				if (e.getClick() == ClickType.RIGHT) {
					openCategory(e);
				}
			} else {
				openCategory(e);
			}
		}
	}
	
	private void openCategory(InventoryClickEvent e) {
		e.setCancelled(true);
		
		Player p = (Player) e.getWhoClicked();
		
		ConfigurationSection categories = EpicShop.shops.getConfig().getConfigurationSection("catalog.categories");
		
		for (String element : categories.getKeys(false)) {

			int categorySlot = EpicShop.shops.getConfig().getInt("catalog.categories." + element + ".category_item.slot");

			if (e.getSlot() + 1 == categorySlot) {
				Inventory invCategory = new InvCategory(p, "catalog.categories." + element + ".category").getInv();
				p.openInventory(invCategory);
				break;
			}
		}
	}
	
	private String getClickedCategory(InventoryClickEvent e) {
		ConfigurationSection categories = EpicShop.shops.getConfig().getConfigurationSection("catalog.categories");
		
		for (String element : categories.getKeys(false)) {

			int categorySlot = EpicShop.shops.getConfig().getInt("catalog.categories." + element + ".category_item.slot");

			if (e.getSlot() + 1 == categorySlot) {
				return element;
			}
		}
		return null;
	}
}
