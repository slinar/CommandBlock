package me.commandBlock;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class CheckCommand {
    private static CommandBlock ins = CommandBlock.getIns();

    /**
     * 根据配置文件检查玩家输入的指令是否需要拦截
     * @param commandStr 玩家输入的命令字符串
     * @param playerName 玩家名字
     * @return true 匹配成功 <br/> false 匹配失败
     */
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
        
        // 如果配置文件中有需要拦截的命令
        if (commandList.size() > 0) {
            // 处理用户输入的信息
            String[] commandStrs = commandStr.split(" ");
            String[] temp = commandStrs[0].split(":");
            commandStrs[0] = temp[temp.length - 1].replace("/", "");

            // 如果玩家将要执行的指令和禁用指令列表匹配成功，则返回true
     outer: for (int i = 0; i < commandList.size(); i++) {
                String[] regs = tr(commandList.get(i)).split(" ");
                if (commandStrs.length >= regs.length) {
                    // 如果第count个子字符串没有匹配成功,那后续的子字符串不用再匹配了,直接继续匹配配置文件中下一条命令
                    // 最大循环次数是配置文件中每条命令分割后的字符串数组的长度
                    regs[0] = regs[0].replace("/", "");
                    for (int count = 0; count < regs.length; count++) {
                        if (!match(regs[count], commandStrs[count])) {
                            continue outer;
                        }
                    }
                    if (CmdCb.debug) ins.getLogger().info("命令列表匹配成功：" + commandList.get(i));
                    return true;
                }
            }
        }

        // 如果全部没有匹配成功，则表示玩家不在白名单，而且玩家执行的指令也没有被禁止，返回false
        return false;
    }
    
    /**
     * 根据自定义规则匹配两个字符串, * 表示零个或多个有效字符, ? 表示一个有效字符 <br/>
     * 有效字符: 小写a-z, 大写A-Z, 数字0-9, 下划线_
     * @param reg  规则字符串
     * @param strs  目标字符串
     * @return true  匹配成功<br/>false  匹配失败
     */
    private static boolean match(String reg, String strs) {
        boolean result = false;
        try {
            reg = reg.toLowerCase()
                     .replace("\\", "")
                     .replaceAll("\\*{1,}", "\\\\w*")
                     .replace("?", "\\w")
                     .replace(".", "[.]")
                     .replace("+", "[+]");
            Matcher matcher = Pattern.compile(reg).matcher(strs.toLowerCase());
            result = matcher.matches();
        } catch (PatternSyntaxException e) {
            System.out.println("错误的正则表达式:" + e.getPattern());
        }
        if (CmdCb.debug) ins.getLogger().info("reg:" + reg + "  " + "strs:" + strs + "  " + String.valueOf(result));
        return result;
    }
    
    /**
     * 去除首尾空格以及把两个及两个以上的空格替换成一个空格
     * @param 源字符串
     * @return 结果字符串
     */
    private static String tr(String string) {
        return string.trim().replaceAll(" {2,}", " ");
    }
}
