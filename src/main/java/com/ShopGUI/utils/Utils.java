package com.ShopGUI.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ShopGUI.ShopGUI;

public class Utils {
	public static String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void saveConfig(File file, YamlConfiguration config) {
		ShopGUI plugin = ShopGUI.plugin;
		File checkFile = new File(plugin.getDataFolder() + "/" + file.getName());
		if (checkFile.exists()) {
			try {
				config.save(file);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			plugin.saveResource(file.getName(), false);
		}
	}
}
