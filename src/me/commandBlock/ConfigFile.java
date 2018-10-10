package me.commandBlock;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFile {
    static final String PREFIX = "§c[CommandBlock] ";
    static final String PREFIX_C = "[CommandBlock] ";
    private static CommandBlock ins = CommandBlock.getIns();
    private static File file;
    private static FileConfiguration config;
    private static Runnable task = new Runnable() {
        public void run() {
            updateConfig();
        }
    };

    public static void loadConfig() {
        if (!ins.getDataFolder().exists()) {
            ins.getDataFolder().mkdirs();
        }
        file = new File(CommandBlock.getIns().getDataFolder(), "config.yml");
        if (!file.exists()) {
            ins.getLogger().info("配置文件不存在,载入默认配置文件!");
            ins.saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(file);
        ins.getLogger().info("配置文件已载入!");
    }

    public static void updateConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static Runnable getTask() {
        return task;
    }

    public static List<String> getCommandList() {
        return config.getStringList("list");
    }

    public static List<String> getPlayerWhiteList() {
        return config.getStringList("playerWhiteList");
    }

}
