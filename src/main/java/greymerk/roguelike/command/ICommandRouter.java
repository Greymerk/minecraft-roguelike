package greymerk.roguelike.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public interface ICommandRouter {
	
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args);
	
	public List<String> getTabCompletion(List<String> args);
	
}
