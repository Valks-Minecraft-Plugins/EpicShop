package com.ShopGUI;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ShopGUI.commands.CommandShop;
import com.ShopGUI.listeners.InventoryListener;
import com.ShopGUI.tabcomplete.TabCompleteShopHelp;
import com.ShopGUI.utils.Utils;

public class ShopGUI extends JavaPlugin {
	public static ShopGUI plugin;
	
	public static Map<UUID, String> buySellInvItemPath = new HashMap<UUID, String>();
	
	private File shopConfigFile = new File(getDataFolder(), "shops.yml");
	private File globalConfigFile = new File(getDataFolder(), "global.yml");
	private File messagesConfigFile = new File(getDataFolder(), "messages.yml");
	private File permissionsConfigFile = new File(getDataFolder(), "permissions.yml");
	private File signsConfigFile = new File(getDataFolder(), "signs.yml");
	private File buttonsConfigFile = new File(getDataFolder(), "buttons.yml");

	private YamlConfiguration shopConfig = YamlConfiguration.loadConfiguration(shopConfigFile);
	private YamlConfiguration globalConfig = YamlConfiguration.loadConfiguration(globalConfigFile);
	private YamlConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);
	private YamlConfiguration permissionsConfig = YamlConfiguration.loadConfiguration(permissionsConfigFile);
	private YamlConfiguration signsConfig = YamlConfiguration.loadConfiguration(signsConfigFile);
	private YamlConfiguration buttonsConfig = YamlConfiguration.loadConfiguration(buttonsConfigFile);
	
	@Override
	public void onEnable() {
		plugin = this;
		registerListeners();
		registerCommands();
		registerConfigs();
	}
	
	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InventoryListener(), this);
	}
	
	private void registerCommands() {
		getCommand("shop").setExecutor(new CommandShop());
		getCommand("shop").setTabCompleter(new TabCompleteShopHelp());
	}
	
	private void registerConfigs() {
		saveAllConfigs();
		reloadAllConfigs();
	}
	
	public void saveAllConfigs() {
		saveShopConfig();
		saveGlobalConfig();
		saveMessagesConfig();
		savePermissionsConfig();
		saveSignsConfig();
		saveButtonsConfig();
	}
	
	public void reloadAllConfigs() {
		reloadShopConfig();
		reloadGlobalConfig();
		reloadMessagesConfig();
		reloadPermissionsConfig();
		reloadSignsConfig();
		reloadButtonsConfig();
	}
	
	public void reloadShopConfig() {
		shopConfig = YamlConfiguration.loadConfiguration(shopConfigFile);
	}
	
	public void reloadGlobalConfig() {
		globalConfig = YamlConfiguration.loadConfiguration(globalConfigFile);
	}
	
	public void reloadMessagesConfig() {
		messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);
	}
	
	public void reloadPermissionsConfig() {
		permissionsConfig = YamlConfiguration.loadConfiguration(permissionsConfigFile);
	}
	
	public void reloadSignsConfig() {
		signsConfig = YamlConfiguration.loadConfiguration(signsConfigFile);
	}
	
	public void reloadButtonsConfig() {
		buttonsConfig = YamlConfiguration.loadConfiguration(buttonsConfigFile);
	}
	
	public void saveShopConfig() {
		Utils.saveConfig(shopConfigFile, shopConfig);
	}
	
	public void saveGlobalConfig() {
		Utils.saveConfig(globalConfigFile, globalConfig);
	}
	
	public void saveMessagesConfig() {
		Utils.saveConfig(messagesConfigFile, messagesConfig);
	}
	
	public void savePermissionsConfig() {
		Utils.saveConfig(permissionsConfigFile, permissionsConfig);
	}
	
	public void saveSignsConfig() {
		Utils.saveConfig(signsConfigFile, signsConfig);
	}
	
	public void saveButtonsConfig() {
		Utils.saveConfig(buttonsConfigFile, buttonsConfig);
	}
	
	public YamlConfiguration getShopConfig() {
		return shopConfig;
	}
	
	public YamlConfiguration getGlobalConfig() {
		return globalConfig;
	}
	
	public YamlConfiguration getMessagesConfig() {
		return messagesConfig;
	}
	
	public YamlConfiguration getPermissionsConfig() {
		return permissionsConfig;
	}
	
	public YamlConfiguration getSignsConfig() {
		return signsConfig;
	}
	
	public YamlConfiguration getButtonsConfig() {
		return buttonsConfig;
	}
}
