package greymerk.roguelike.command.routes;

import java.util.List;
import java.util.Set;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class CommandRouteBiome extends CommandRouteBase{

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		
		ArgumentParser ap = new ArgumentParser(args);
		
		World world = sender.getEntityWorld();
		IWorldEditor editor = new WorldEditor(world);
		Coord pos;
		if(!ap.hasEntry(0)){
			pos = new Coord(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ());
		} else {
			int x; int z;
			try {
				x = CommandBase.parseInt(ap.get(0));
				z = CommandBase.parseInt(ap.get(1));
			} catch (NumberInvalidException e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
				return;
			}
			pos = new Coord(x, 0, z);
		}
		sender.sendMessage(new TextComponentString(TextFormat.apply("Biome Information for " + pos.toString(), TextFormat.GOLD)));
		
		Biome biome = editor.getInfo(pos).getBiome();
		sender.sendMessage(new TextComponentString(TextFormat.apply(biome.getBiomeName(), TextFormat.GOLD)));
		Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
		String types = "";
		for(BiomeDictionary.Type type : biomeTypes){
			types += type.getName() + " ";
		}
		sender.sendMessage(new TextComponentString(TextFormat.apply(types, TextFormat.GOLD)));
		return;
	}
}
