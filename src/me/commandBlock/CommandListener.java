package me.commandBlock;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (CheckCommand.check(e.getMessage(), e.getPlayer())) {
            e.setCancelled(true);
            Bukkit.getLogger().info(
                    ConfigFile.PREFIX_C + "玩家 " + e.getPlayer().getName() + " 试图执行:" + e.getMessage() + ", 已拦截操作！");
            if (ConfigFile.getDenyMessage().length() > 0) {
                e.getPlayer().sendMessage(ConfigFile.PREFIX + ConfigFile.getDenyMessage());
            }
        }
    }
    
}
