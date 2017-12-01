package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.TextFormat;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandRouteConfig extends CommandRouteBase{

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike config reload", TextFormat.GRAY)));
			return;
		}
		if(ap.match(0, "reload")){
			RogueConfig.reload(true);
			sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Configurations Reloaded", TextFormat.GREEN)));
			return;
		}
		return;
	}
	
}
