package com.epicshop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.epicshop.commands.CmdShop;
import com.epicshop.configs.ConfigManager;
import com.epicshop.configs.FromConfigShopItem;
import com.epicshop.inv.InvBuilder;
import com.epicshop.listeners.InvListener;
import com.epicshop.tabcomplete.TabShop;

import net.milkbowl.vault.economy.Economy;

public class EpicShop extends JavaPlugin {
	public static EpicShop plugin;
	
	public static Economy economy = null;

	public static Map<UUID, String> buySellInvItemPath = new HashMap<UUID, String>();
	public static Map<UUID, Boolean> shopEditMode = new HashMap<UUID, Boolean>();
	public static Map<UUID, FromConfigShopItem> shopMovingItem = new HashMap<UUID, FromConfigShopItem>();
	public static Map<UUID, Integer> materialPage = new HashMap<UUID, Integer>();
	public static Map<UUID, InvBuilder> invBuilders = new HashMap<UUID, InvBuilder>();

	public static ConfigManager shops, global, messages, permissions, signs, buttons;

	@Override
	public void onEnable() {
		plugin = this;

		registerListeners();
		registerCommands();
		registerConfigs();
		registerEconomy();
	}
	
	@SuppressWarnings("unchecked")
	private boolean registerEconomy()
    {
		Class<Economy> vaultEco = null;
		try {
			vaultEco = (Class<Economy>) Class.forName("net.milkbowl.vault.economy.Economy");
		} catch (ClassNotFoundException e) {
			getServer().getLogger().log(Level.WARNING, "[" + getName() + "] Economy plugin not found, ignoring..");
		}
		if (vaultEco != null) {
			RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(vaultEco);
		    if (economyProvider != null) {
		        economy = economyProvider.getProvider();
		        getServer().getLogger().log(Level.INFO, "[" + getName() + "] Economy plugin found!");
		    }
		
		    return (economy != null);
		}
		return false;
    }

	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InvListener(), this);
	}

	private void registerCommands() {
		getCommand("shop").setExecutor(new CmdShop());
		getCommand("shop").setTabCompleter(new TabShop());
	}

	private void registerConfigs() {
		shops = new ConfigManager("shops");
		global = new ConfigManager("global");
		messages = new ConfigManager("messages");
		permissions = new ConfigManager("permissions");
		signs = new ConfigManager("signs");
		buttons = new ConfigManager("buttons");

		saveAllConfigs();
		reloadAllConfigs();
	}

	public static void saveAllConfigs() {
		shops.saveConfig();
		global.saveConfig();
		messages.saveConfig();
		permissions.saveConfig();
		signs.saveConfig();
		buttons.saveConfig();
	}

	public static void reloadAllConfigs() {
		shops.reloadConfig();
		global.reloadConfig();
		messages.reloadConfig();
		permissions.reloadConfig();
		signs.reloadConfig();
		buttons.reloadConfig();
	}
}
