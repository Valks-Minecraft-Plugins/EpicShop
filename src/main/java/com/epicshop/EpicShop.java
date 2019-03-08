package com.epicshop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.epicshop.commands.CmdShop;
import com.epicshop.configs.ConfigManager;
import com.epicshop.listeners.InvListener;
import com.epicshop.tabcomplete.TabShop;

public class EpicShop extends JavaPlugin {
	public static EpicShop plugin;

	public static Map<UUID, String> buySellInvItemPath = new HashMap<UUID, String>();

	public static ConfigManager shops, global, messages, permissions, signs, buttons;

	@Override
	public void onEnable() {
		plugin = this;

		registerListeners();
		registerCommands();
		registerConfigs();
	}

	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InvListener(), this);
	}

	private void registerCommands() {
		getCommand("shop").setExecutor(new CmdShop());
		getCommand("shop").setTabCompleter(new TabShop());
	}

	private static void registerConfigs() {
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
