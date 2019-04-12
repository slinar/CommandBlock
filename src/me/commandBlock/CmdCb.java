package me.commandBlock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CmdCb {
    @SuppressWarnings("serial")
    private static final List<String> args1 = new ArrayList<String>() {
        {
            add("reload");
            add("debug");
        }
    };
    static boolean debug = false;
    private static CommandBlock ins = CommandBlock.getIns();
    
    public static void exec(CommandSender sender, String[] args) {
        if (checkPermissions(sender)) {
            if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
                reload(sender);
            }
            if ((args.length >= 1) && (args[0].equalsIgnoreCase("debug"))) {
                debug(sender, args);
            }
        } else {
            sender.sendMessage(ConfigFile.PREFIX_C + "你没有权限这样做!");
        }
    }
    
    public static List<String> tab(CommandSender sender, String[] args) {
        if ((args == null) || (args.length <= 1)) {
            return args1;
        }
        return new ArrayList<String>();
    }
    
    private static boolean checkPermissions(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) return true;
        if (sender instanceof Player) return sender.isOp();
        ins.getLogger().warning("未处理的命令发送者:" + sender.getClass().getName());
        return false;
    }
    
    private static void reload(CommandSender sender) {
        ConfigFile.loadConfig();
        sender.sendMessage(ConfigFile.PREFIX_C + "已重载配置!");
    }
    
    private static void debug(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(ConfigFile.PREFIX_C + "/cb debug on - 开启debug");
            sender.sendMessage(ConfigFile.PREFIX_C + "/cb debug off - 关闭debug");
        } else if (args[1].equalsIgnoreCase("on")) {
            debug = true;
            sender.sendMessage(ConfigFile.PREFIX_C + "debug 已开启!");
        } else if (args[1].equalsIgnoreCase("off")) {
            debug = false;
            sender.sendMessage(ConfigFile.PREFIX_C + "debug 已关闭!");
        } else {
            sender.sendMessage(ConfigFile.PREFIX_C + "/cb debug on  - 开启调试");
            sender.sendMessage(ConfigFile.PREFIX_C + "/cb debug off - 关闭调试");
        }
    }
}
