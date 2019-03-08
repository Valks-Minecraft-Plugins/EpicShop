package com.epicshop.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.epicshop.EpicShop;

public class ConfigManager {
	private File file;
	private YamlConfiguration config;

	public ConfigManager(String name) {
		file = new File(EpicShop.plugin.getDataFolder(), name + ".yml");
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void saveConfig() {
		EpicShop plugin = EpicShop.plugin;
		File checkFile = new File(plugin.getDataFolder() + "/" + file.getName());
		if (checkFile.exists()) {
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			plugin.saveResource(file.getName(), false);
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}

	public YamlConfiguration getConfig() {
		return config;
	}
}
