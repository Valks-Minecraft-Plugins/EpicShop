package com.ShopGUI;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ShopGUI.commands.CommandShop;
import com.ShopGUI.listeners.ListenerInvClose;
import com.ShopGUI.utils.Utils;

public class ShopGUI extends JavaPlugin {
	public static ShopGUI plugin;
	
	private File shopConfigFile = new File(getDataFolder(), "shops.yml");
	private File globalConfigFile = new File(getDataFolder(), "global.yml");
	private File messagesConfigFile = new File(getDataFolder(), "messages.yml");
	private File permissionsConfigFile = new File(getDataFolder(), "permissions.yml");
	private File signsConfigFile = new File(getDataFolder(), "signs.yml");

	private YamlConfiguration shopConfig = YamlConfiguration.loadConfiguration(shopConfigFile);
	private YamlConfiguration globalConfig = YamlConfiguration.loadConfiguration(globalConfigFile);
	private YamlConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);
	private YamlConfiguration permissionsConfig = YamlConfiguration.loadConfiguration(permissionsConfigFile);
	private YamlConfiguration signsConfig = YamlConfiguration.loadConfiguration(signsConfigFile);
	
	@Override
	public void onEnable() {
		plugin = this;
		registerListeners();
		registerCommands();
		registerConfigs();
	}
	
	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ListenerInvClose(), this);
	}
	
	private void registerCommands() {
		getCommand("shop").setExecutor(new CommandShop());
	}
	
	private void registerConfigs() {
		saveAllConfigs();
	}
	
	public void saveAllConfigs() {
		saveShopConfig();
		saveGlobalConfig();
		saveMessagesConfig();
		savePermissionsConfig();
		saveSignsConfig();
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
}
