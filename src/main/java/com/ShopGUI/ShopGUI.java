package com.ShopGUI;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ShopGUI.commands.CommandShop;
import com.ShopGUI.listeners.ListenerInvClose;

public class ShopGUI extends JavaPlugin {
	private File shopConfigFile = new File(getDataFolder(), "shopgui.yml");
	private YamlConfiguration shopConfig = YamlConfiguration.loadConfiguration(shopConfigFile);
	
	@Override
	public void onEnable() {
		registerListeners();
		registerCommands();
		registerShopConfig();
	}
	
	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ListenerInvClose(), this);
	}
	
	private void registerCommands() {
		getCommand("shop").setExecutor(new CommandShop());
	}
	
	private void registerShopConfig() {
		
	}
	
	public void saveShopConfig() {
		try {
			shopConfig.save(shopConfigFile);
		} catch (IOException e) {
			getServer().getLogger().warning("Could not save shop config! " + e.getStackTrace());
		}
	}
	
	public YamlConfiguration getShopConfig() {
		return shopConfig;
	}
}
