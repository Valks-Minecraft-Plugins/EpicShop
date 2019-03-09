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
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.epicshop.EpicShop;
import com.epicshop.configs.ConfigItem;
import com.epicshop.configs.FromConfigShopItem;
import com.epicshop.inv.InvBuilder;
import com.epicshop.inv.InvBuySell;
import com.epicshop.inv.InvCategories;
import com.epicshop.inv.InvCategory;
import com.epicshop.inv.InvMaterialFromPlayerInv;
import com.epicshop.inv.InvMaterialPicker;
import com.epicshop.utils.Utils;

public class InvListener implements Listener {
	private String title;
	private int slot;
	private Player p;
	private Inventory inv;
	private ClickType click;
	private InventoryClickEvent e;
	
	@EventHandler
	private void invCloseEvent(InventoryCloseEvent e) {
		/*if (title.equals("Materials")) {
			if (EpicShop.materialPage.containsKey(p.getUniqueId()))
				EpicShop.materialPage.put(p.getUniqueId(), 1);
		}*/
	}

	@EventHandler
	private void invInteractEvent(InventoryClickEvent e) {
		// Replace that stupid § (which is copied as a º) symbol in console to a &.
		title = e.getView().getTitle().replaceAll("\u00a7", "&");
		slot = e.getSlot() + 1;
		p = (Player) e.getWhoClicked();
		inv = e.getInventory();
		click = e.getClick();
		this.e = e;
		
		handleBuySellInvClickEvents();
		handleCategoryClickEvents();
		handleCategoriesClickEvents();
		handleBuilderInvClickEvents();
		handleMaterialPickerInvClickEvents();
		handleMaterialPickerFromPlayerInvClickEvents();
	}
	
	private void handleMaterialPickerFromPlayerInvClickEvents() {
		if (title.equals("Material Player Chooser")) {
			if (e.getClickedInventory().getType() != InventoryType.PLAYER && e.getAction() != InventoryAction.PLACE_ALL) {
				e.setCancelled(true);
			}
			
			if (slot == 5) {
				if (e.getAction() == InventoryAction.PLACE_ALL) {
					if (e.getCursor().getType() != Material.AIR) {
						InvBuilder invBuilder = EpicShop.invBuilders.get(p.getUniqueId());
						invBuilder.setMaterial(e.getCursor().getType());
						p.openInventory(invBuilder.getInv());
					}
				}
			}
		}
	}
	
	private void handleMaterialPickerInvClickEvents() {
		if (title.equals("Materials")) {
			e.setCancelled(true);
			
			int curPage = EpicShop.materialPage.get(p.getUniqueId());
			
			InvBuilder invBuilder = EpicShop.invBuilders.get(p.getUniqueId());
			
			if (slot == 46) {
				EpicShop.materialPage.put(p.getUniqueId(), Math.max(1, curPage - 1));
				p.openInventory(new InvMaterialPicker(p).getInv());
			} else if (slot == 50) {
				p.openInventory(invBuilder.getInv());
			} else if (slot == 54) {
				EpicShop.materialPage.put(p.getUniqueId(), Math.min(1000, curPage + 1));
				p.openInventory(new InvMaterialPicker(p).getInv());
			} else {
				invBuilder.setMaterial(e.getCurrentItem().getType());
				p.openInventory(invBuilder.getInv());
			}
		}
	}
	
	private void handleBuilderInvClickEvents() {
		if (title.equals("Item Builder")) {
			e.setCancelled(true);
			if (slot == 1) {
				if (click == ClickType.LEFT) {
					p.openInventory(new InvMaterialPicker(p).getInv());
				}
				
				if (click == ClickType.RIGHT) {
					p.openInventory(new InvMaterialFromPlayerInv().getInv());
					new BukkitRunnable() {
						@Override
						public void run() {
							InventoryView openInv = p.getOpenInventory();
							if (openInv != null) {
								if (openInv.getTitle().equals("Material Player Chooser")) {
									p.openInventory(EpicShop.invBuilders.get(p.getUniqueId()).getInv());
									return;
								}
							}
							
							cancel();
						}
					}.runTaskLater(EpicShop.plugin, 20 * 5);
				}
			}
		}
	}
	
	private void handleBuySellInvClickEvents() {
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
			Inventory prevInventory = new InvCategory("catalog.categories." + categoryName + ".category").getInv();

			FromConfigShopItem item = new FromConfigShopItem(EpicShop.shops, itemPath);
			InvBuySell buySellInv = new InvBuySell(item);

			int slotInfo = buySellInv.getItemInfo().getSlot();
			int slotProduct = buySellInv.getItemProductSlot();
			
			ItemStack product = inv.getItem(--slotProduct);
			ItemStack info = inv.getItem(--slotInfo);

			if (slot == slotProduct + 1) {
				if (click == ClickType.LEFT) {
					product.setAmount(Math.min(product.getAmount() + 1, 64));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}
				
				if (click == ClickType.RIGHT) {
					product.setAmount(Math.max(product.getAmount() - 1, 1));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}
			}

			if (click == ClickType.LEFT) {
				if (slot == buySellInv.getItemAdd1().getSlot()) {
					product.setAmount(Math.min(product.getAmount() + 1, 64));
					inv.setItem(slotProduct, product);
					Utils.updateProductLore(info, item, product);
				}

				if (slot == buySellInv.getItemAdd10().getSlot()) {
					product.setAmount(Math.min(product.getAmount() + 10, 64));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == buySellInv.getItemSet64().getSlot()) {
					product.setAmount(64);
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == buySellInv.getItemRemove1().getSlot()) {
					product.setAmount(Math.max(product.getAmount() - 1, 1));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == buySellInv.getItemRemove10().getSlot()) {
					product.setAmount(Math.max(product.getAmount() - 10, 1));
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == buySellInv.getItemSet1().getSlot()) {
					product.setAmount(1);
					Utils.updateProductLore(info, item, product);
					inv.setItem(slotProduct, product);
				}

				if (slot == buySellInv.getItemCancel().getSlot()) {
					p.openInventory(prevInventory);
				}

				if (slot == buySellInv.getItemConfirm().getSlot()) {
					p.closeInventory();
				}

				if (slot == buySellInv.getItemSellAll().getSlot()) {
					p.closeInventory();
				}
			}
		}
	}
	
	private void handleCategoryClickEvents() {
		ConfigurationSection categories = EpicShop.shops.getConfig().getConfigurationSection("catalog.categories");
		YamlConfiguration buttonsConfig = EpicShop.buttons.getConfig();
		
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
					p.openInventory(new InvCategories().getInv());
				}

				ConfigurationSection categoryItems = EpicShop.shops.getConfig().getConfigurationSection(pathToCategory + ".items");
				for (String itemName : categoryItems.getKeys(false)) {
					String pathToItem = pathToCategory + ".items." + itemName;
					int itemSlot = EpicShop.shops.getConfig().getInt(pathToItem + ".slot");
					if (slot == itemSlot) {
						// Store the path to the item so we can go back to where we left off later.
						EpicShop.buySellInvItemPath.put(p.getUniqueId(), pathToItem);

						FromConfigShopItem item = new FromConfigShopItem(EpicShop.shops, pathToItem);
						InvBuySell buySellInv = new InvBuySell(item);
						
						// Open buy sell inventory.
						p.openInventory(buySellInv.getInv());
						break;
					}
				}
				break;
			}
		}
	}
	
	private void handleCategoriesClickEvents() {
		String categoriesTitle = EpicShop.shops.getConfig().getString("catalog.name");
		
		if (title.equals(categoriesTitle)) {
			
			if (EpicShop.shopEditMode.get(p.getUniqueId())) {
				if (click == ClickType.MIDDLE) {
					InvBuilder invBuilder = new InvBuilder();
					p.openInventory(invBuilder.getInv());
					EpicShop.invBuilders.put(p.getUniqueId(), invBuilder);
				}
				
				if (click == ClickType.LEFT) {
					if (e.getAction() == InventoryAction.PICKUP_ALL) {
						String category = getClickedCategory();
						String pathToCategoryItem = "catalog.categories." + category + ".category_item";
						FromConfigShopItem item = new FromConfigShopItem(EpicShop.shops, pathToCategoryItem);
						EpicShop.shopMovingItem.put(p.getUniqueId(), item);
					}
					
					if (e.getAction() == InventoryAction.PLACE_ALL) {
						if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
							e.setCancelled(true);
						} else {
							FromConfigShopItem shopItem = EpicShop.shopMovingItem.get(p.getUniqueId());
							new ConfigItem(EpicShop.shops).setConfigItem(shopItem, slot, shopItem.getPath());
						}
					}
				}
				
				if (e.getClick() == ClickType.RIGHT) {
					openCategory();
				}
			} else {
				openCategory();
			}
		}
	}
	
	/*
	 * Opens the category the user clicks on from the categories page.
	 */
	private void openCategory() {
		ConfigurationSection categories = EpicShop.shops.getConfig().getConfigurationSection("catalog.categories");
		
		e.setCancelled(true);
		
		for (String element : categories.getKeys(false)) {
			int categorySlot = EpicShop.shops.getConfig().getInt("catalog.categories." + element + ".category_item.slot");

			if (e.getSlot() + 1 == categorySlot) {
				Inventory invCategory = new InvCategory("catalog.categories." + element + ".category").getInv();
				p.openInventory(invCategory);
				break;
			}
		}
	}
	
	/*
	 * Gets the category the user clicks from the categories page.
	 */
	private String getClickedCategory() {
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
