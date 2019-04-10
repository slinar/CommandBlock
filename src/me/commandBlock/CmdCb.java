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
    static boolean debug = false;
    
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
                if ((args.length >= 1) && (args[0].equalsIgnoreCase("debug"))) {
                    if (args.length == 1) {
                        sender.sendMessage(ConfigFile.PREFIX + "/cb debug on - 开启debug");
                        sender.sendMessage(ConfigFile.PREFIX + "/cb debug off - 关闭debug");
                    } else if (args[1].equalsIgnoreCase("on")) {
                        debug = true;
                        sender.sendMessage(ConfigFile.PREFIX + "debug 已开启!");
                    } else if (args[1].equalsIgnoreCase("off")) {
                        debug = false;
                        sender.sendMessage(ConfigFile.PREFIX + "debug 已关闭!");
                    } else {
                        sender.sendMessage(ConfigFile.PREFIX + "/cb debug on  - 开启调试");
                        sender.sendMessage(ConfigFile.PREFIX + "/cb debug off - 关闭调试");
                    }
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
