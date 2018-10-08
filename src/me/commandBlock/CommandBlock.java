package me.commandBlock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class CommandBlock extends org.bukkit.plugin.java.JavaPlugin{
	public static Permission permission = null;
	private static CommandBlock INSTANCE;
	private static ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

	@Override
	public void onEnable(){
		INSTANCE = this;
		setupPermissions();
		ConfigFile.loadConfig();
		getServer().getPluginManager().registerEvents(new CommandListener(),this);
		Bukkit.getPluginCommand("cb").setExecutor(new CmdExecutor());
		Bukkit.getPluginCommand("cb").setTabCompleter(new CmdExecutor());
		ses.scheduleAtFixedRate(ConfigFile.getTask(), 5, 5, TimeUnit.SECONDS);
		getLogger().info("CommandBlock is enabled!");
	}
	
	@Override
	public void onDisable(){
		HandlerList.unregisterAll(this);
		ses.shutdownNow();
		getLogger().info("CommandBlock is disabled");
	}
	
	private void setupPermissions() { 
		try {
			RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
			if(permissionProvider != null) {
				permission = (Permission)permissionProvider.getProvider();
				getLogger().info("已找到 Vault!");
			}
		} catch (NoClassDefFoundError e) {
			getLogger().info("没有找到 Vault!");
		}
	}
	
	public static CommandBlock getIns(){
		return INSTANCE;
	}
}
