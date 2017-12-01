package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.TextFormat;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandRouteSettings extends CommandRouteBase{

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike settings [reload | list]", TextFormat.GRAY)));
			return;
		}
		if(ap.match(0, "reload")){
			try{
				Dungeon.initResolver();
				sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Settings Reloaded", TextFormat.GREEN)));
			} catch(Exception e) {
				if(e.getMessage() == null){
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Uncaught Exception", TextFormat.RED)));
				} else {
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: " + e.getMessage(), TextFormat.RED)));	
				}
			}
			return;
		}
		if(ap.match(0, "list")){
			if(ap.hasEntry(1)){
				String namespace = ap.get(1);
				sender.sendMessage(new TextComponentString(TextFormat.apply(Dungeon.settingsResolver.toString(namespace), TextFormat.GREEN)));
				return;
			}
			sender.sendMessage(new TextComponentString(TextFormat.apply(Dungeon.settingsResolver.toString(), TextFormat.GREEN)));
			return;
		}
		
		return;
	}
}
