package greymerk.roguelike.command.routes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.towers.ITower;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CommandRouteTower extends CommandRouteBase{

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			List<String> towers = Stream.of(Tower.values())
                    .map(Tower::name)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
			
			sender.sendMessage(new TextComponentString(TextFormat.apply("Tower types: " + StringUtils.join(towers, " "), TextFormat.GRAY)));

			return;
		}
		String towerName = ap.get(0);
		Tower type;
		try{
			type = Tower.get(towerName.toUpperCase());	
		} catch(Exception e){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: No such tower type: " + towerName, TextFormat.RED)));
			return;
		}
		
		EntityPlayerMP player = null;
		try {
			player = CommandBase.getCommandSenderAsPlayer(sender);
		} catch (PlayerNotFoundException e) {
			sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Cannot find player", TextFormat.RED)));
			return;
		}
		
		Coord here = new Coord ((int) player.posX, 50, (int) player.posZ);
		ITower tower = Tower.get(type);
		World world = sender.getEntityWorld();
		IWorldEditor editor = new WorldEditor(world);
		tower.generate(editor, Dungeon.getRandom(editor, here), Theme.getTheme(Theme.OAK), here);
		sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + here.toString(), TextFormat.GREEN)));
		return;
	}
	
}
