package greymerk.roguelike.command.routes;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CommandRouteDungeon extends CommandRouteBase{

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike dungeon {X Z | here} [setting]", TextFormat.GRAY)));
			return;
		}
		
		Coord pos;
		try{
			pos = getLocation(sender, args);	
		} catch(Exception e){
			return;
		}

		String settingName = null;
		if(ap.match(0, "here") || ap.match(0, "nearby")){
			settingName = ap.get(1);
		} else {
			settingName = ap.get(2);
		}

		World world = sender.getEntityWorld();
		IWorldEditor editor = new WorldEditor(world);
		
		if(settingName != null){
			try{
				Dungeon.initResolver();
			} catch(Exception e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: " + e.getMessage(), TextFormat.RED)));
				return;
			}
			
			Random rand = Dungeon.getRandom(editor, pos);
			ISettings settings = null; 
					
			try{
				settings = Dungeon.settingsResolver.getWithName(settingName, editor, rand, pos);	
			} catch(Exception e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: " + e.getMessage(), TextFormat.RED)));
				return;
			}
			
			
			if(settings == null){
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failed: " + settingName + " not found.", TextFormat.RED)));
				return;
			}
			
			Dungeon dungeon = new Dungeon(editor);
			dungeon.generate(settings, pos);
			try {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Success: \"" + settingName + "\" Dungeon generated at " + dungeon.getPosition().toString(), TextFormat.GREEN)));
			} catch (Exception e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Unable to generate dungeon", TextFormat.RED)));
			}
			return;
		}
		
		Random rand = Dungeon.getRandom(editor, pos);
		
		ISettings settings = null; 
				
		try{
			settings = Dungeon.settingsResolver.getSettings(editor, rand, pos);
		} catch(Exception e){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: " + e.getMessage(), TextFormat.RED)));
			e.printStackTrace();
			return;
		}
		
		if(settings != null){
			IDungeon dungeon = new Dungeon(editor);
			dungeon.generate(settings, pos);
			sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + pos.toString(), TextFormat.GREEN)));
			return;
		}
		
		IDungeon dungeon = new Dungeon(editor);
		dungeon.generate(Dungeon.settingsResolver.getDefaultSettings(), pos);
		sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + pos.toString(), TextFormat.GREEN)));
		return;
	}
	
	public static Coord getLocation(ICommandSender sender, List<String> args) throws NumberInvalidException, PlayerNotFoundException{
		ArgumentParser ap = new ArgumentParser(args);
		
		EntityPlayerMP player = null;
		try {
			player = CommandBase.getCommandSenderAsPlayer(sender);
		} catch (PlayerNotFoundException e) {
			sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Cannot find player", TextFormat.RED)));
			throw(e);
		}
		
		if(ap.match(0, "here") || ap.match(0, "nearby")){
			return new Coord((int) player.posX, 0, (int) player.posZ);
		} else {
			try {
				int x = CommandBase.parseInt(ap.get(0));
				int z = CommandBase.parseInt(ap.get(1));
				return new Coord(x, 0, z);
			} catch (NumberInvalidException e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
				throw(e);
			}
		}
	}
}
