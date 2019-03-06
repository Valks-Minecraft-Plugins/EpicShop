package com.ShopGUI;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ShopGUI.listeners.ListenerInvClose;

public class ShopGUI extends JavaPlugin {
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
		
	}
	
	private void registerShopConfig() {
		
	}
}
