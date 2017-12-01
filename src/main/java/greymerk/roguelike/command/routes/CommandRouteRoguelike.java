package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.util.TextFormat;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandRouteRoguelike extends CommandRouteBase{

	public CommandRouteRoguelike(){
		super();	
		this.addRoute("dungeon", new CommandRouteDungeon());
		this.addRoute("give", new CommandRouteGive());
		this.addRoute("config", new CommandRouteConfig());
		this.addRoute("settings", new CommandRouteSettings());
		this.addRoute("tower", new CommandRouteTower());
		this.addRoute("biome", new CommandRouteBiome());
		this.addRoute("citadel", new CommandRouteCitadel());
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		if(args.size() == 0){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike [dungeon | give | config | settings]", TextFormat.GRAY)));
		}
		super.execute(server, sender, args);
	}
}
