package me.commandBlock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class CommandBlock extends org.bukkit.plugin.java.JavaPlugin {
    public static Permission permission = null;
    private static CommandBlock INSTANCE;
    public static ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

    /**插件启用时执行的代码*/
    @Override
    public void onEnable() {
        INSTANCE = this;
        setupPermissions();
        ConfigFile.loadConfig();
        regListeners();
        regCmds();
        getLogger().info("CommandBlock is enabled!");
    }

    /**插件禁用时执行的代码*/
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        ConfigFile.futureUpdateConfig.cancel(true);
        getLogger().info("CommandBlock is disabled!");
    }

    /**注册事件监听器*/
    private void regListeners() {
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
    }
    
    /**注册命令,并设置执行者和Tab补全*/
    private void regCmds() {
        CmdExecutor cmdExecutor = new CmdExecutor();
        Bukkit.getPluginCommand("cb").setExecutor(cmdExecutor);
        Bukkit.getPluginCommand("cb").setTabCompleter(cmdExecutor);
    }
       
    /**设置Vault权限*/
    private void setupPermissions() {
        try {
            RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
                    .getRegistration(Permission.class);
            if (permissionProvider != null) {
                permission = (Permission) permissionProvider.getProvider();
                getLogger().info("已找到 Vault!");
            }
        } catch (NoClassDefFoundError e) {
            getLogger().info("没有找到 Vault!");
        }
    }

    /**返回这个插件的实例*/
    public static CommandBlock getIns() {
        return INSTANCE;
    }
}
