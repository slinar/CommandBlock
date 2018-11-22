package me.commandBlock;

import java.util.List;

import org.bukkit.Bukkit;

public final class CheckCommand {

    public static boolean check(String commandStr, String playerName) {
        // 从ConfigFile中接收玩家白名单列表和禁用指令列表
        List<String> playerList = ConfigFile.getPlayerWhiteList();
        List<String> commandList = ConfigFile.getCommandList();

        // 如果玩家白名单列表匹配成功，则直接返回false
        if (playerList.size() > 0) {
            for (int i = 0; i < playerList.size(); i++) {
                if (playerList.get(i).equals(playerName)) {
                    return false;
                }
            }
        }

        if (commandList.size() > 0) {
            // 处理用户输入的信息
            String[] commandStrs = commandStr.split(" ");
            String[] temp = commandStrs[0].split(":");
            if (temp.length > 1) {
                commandStrs[0] = "/" + temp[temp.length - 1];
            }

            // 如果玩家将要执行的指令和禁用指令列表匹配成功，则返回true
     outer: for (int i = 0; i < commandList.size(); i++) {
                String[] strings = tr(commandList.get(i)).split(" ");
                if (commandStrs.length < strings.length) {
                    continue;
                }
                for (int count = 0; count < strings.length; count++) {
                    if (!strings[count].equalsIgnoreCase(commandStrs[count])) {
                        continue outer;
                    }
                }
                Bukkit.getLogger().info(ConfigFile.PREFIX_C + "命令列表匹配成功：" + commandList.get(i));
                return true;
            }
        }

        // 如果全部没有匹配成功，则表示玩家不在白名单，而且玩家执行的指令也没有被禁止，返回false
        return false;
    }
    
    private static String tr(String string) {
        return string.trim().replaceAll(" {2,}", " ");
    }
    
}
