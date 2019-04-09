package me.commandBlock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFile {
    static final String PREFIX = "§e[CommandBlock]§r ";
    static final String PREFIX_C = "[CommandBlock] ";
    private static CommandBlock ins = CommandBlock.getIns();
    private static File file;
    private static FileConfiguration config;
    private static String md5;
    private static int oldInterval = 0;
    public static ScheduledFuture<?> futureUpdateConfig;
    private static Runnable task = new Runnable() {
        public void run() {
            updateConfig();
        }
    };

    public static void loadConfig() {
        if (!ins.getDataFolder().exists()) {
            ins.getDataFolder().mkdirs();
        }
        file = new File(ins.getDataFolder(), "config.yml");
        if (!file.exists()) {
            ins.getLogger().info("配置文件不存在,载入默认配置文件!");
            ins.saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(file);
        md5 = getConfigMd5();
        oldInterval = getInterval();
        updateConfigTask();
        ins.getLogger().info("配置文件已载入!");
    }

    private static void updateConfig() {
        String var = getConfigMd5();
        if (!var.equals(md5)) {
            config = YamlConfiguration.loadConfiguration(file);
            if (getInterval() != oldInterval) {
                oldInterval = getInterval();
                updateConfigTask();
            }
            md5 = var;
            ins.getLogger().info("配置文件已更改!");
        }
    }

    /**异步自动更新配置文件*/
    private static void updateConfigTask() {
        if(!(futureUpdateConfig == null)) {
            futureUpdateConfig.cancel(true);
            ins.getLogger().info("重新设置自动更新配置文件的间隔为:" + oldInterval + "秒!");
        }
        futureUpdateConfig = CommandBlock.ses.scheduleAtFixedRate(task, oldInterval, oldInterval, TimeUnit.SECONDS);
    }
    
    public static List<String> getCommandList() {
        return config.getStringList("list");
    }

    public static List<String> getPlayerWhiteList() {
        return config.getStringList("playerWhiteList");
    }

    public static int getInterval() {
        int interval = config.getInt("configUpdateInterval");
        if (interval < 1) interval = 1;
        if (interval > 60) interval = 60;
        return interval;
    }

    public static String getDenyMessage() {
        if (config.getString("denyMessage") == null) {
            return "";
        } else {
            return config.getString("denyMessage").trim();
        }
    }
    
    private static String getConfigMd5() {
        String var = "";
        try {
            var = DigestUtils.md5Hex(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return var;
    }
    
}
