package me.commandBlock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdCb {
    @SuppressWarnings("serial")
    private static final List<String> args1 = new ArrayList<String>() {
        {
            add("reload");
        }
    };

    public static boolean exec(CommandSender sender, String[] args) {
        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
                ConfigFile.loadConfig();
                return true;
            }
        }

        if (sender instanceof Player) {
            // Player player = (Player)sender;

            if (sender.isOp()) {
                if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
                    ConfigFile.loadConfig();
                    sender.sendMessage(ConfigFile.PREFIX + "已重载配置!");
                    return true;
                }
            } else {
                sender.sendMessage(ConfigFile.PREFIX + "你没有权限这样做!");
                return true;
            }

        }
        return false;
    }

    public static List<String> tab(CommandSender sender, String[] args) {
        if ((args == null) || (args.length <= 1)) {
            return args1;
        }
        return new ArrayList<String>();
    }
}
