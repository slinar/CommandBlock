package me.commandBlock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CmdExecutor implements CommandExecutor,TabCompleter {
	public boolean onCommand(CommandSender sender,Command cmd, String label,String[] args) {
		if (cmd.getName().equalsIgnoreCase("cb")) return CmdCb.exec(sender, args);
		return false;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("cb")){
			return CmdCb.tab(sender, args);
		}
		return new ArrayList<String>();
	}
}
