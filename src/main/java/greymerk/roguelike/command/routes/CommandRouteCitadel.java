package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CommandRouteCitadel extends CommandRouteBase{

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		ArgumentParser ap = new ArgumentParser(args);
		
		int x;
		int z;
		try {
			x = CommandBase.parseInt(ap.get(1));
			z = CommandBase.parseInt(ap.get(2));
		} catch (NumberInvalidException e) {
			sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
			return;
		}
		
		World world = sender.getEntityWorld();
		Citadel.generate(new WorldEditor(world), x, z);
		return;
	}
}
