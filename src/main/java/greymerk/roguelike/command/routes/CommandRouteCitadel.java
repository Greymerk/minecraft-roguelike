package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandRouteCitadel extends CommandRouteBase{

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		
		Coord pos;
		try{
			pos = CommandRouteDungeon.getLocation(sender, args);	
		} catch(Exception e){
			return;
		}
		
		World world = sender.getEntityWorld();
		Citadel.generate(new WorldEditor(world), pos.getX(), pos.getZ());
		return;
	}
}
