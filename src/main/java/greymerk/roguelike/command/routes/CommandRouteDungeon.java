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

		int x;
		int z;
		
		String settingName = null;
		
		if(ap.match(0, "here")){
			EntityPlayerMP player = null;
			try {
				player = CommandBase.getCommandSenderAsPlayer(sender);
			} catch (PlayerNotFoundException e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Cannot find player", TextFormat.RED)));
				return;
			}
			x = (int) player.posX;
			z = (int) player.posZ;
			
			if(ap.hasEntry(1)){
				settingName = ap.get(1);
			}
		} else if(ap.match(0, "nearby")){
			EntityPlayerMP player = null;
			try {
				player = CommandBase.getCommandSenderAsPlayer(sender);
			} catch (PlayerNotFoundException e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Cannot find player", TextFormat.RED)));
				return;
			}
			
			x = (int) player.posX;
			z = (int) player.posZ;
			
			if(ap.hasEntry(1)){
				int num = 0;
				try {
					num = CommandBase.parseInt(ap.get(1));
				} catch (NumberInvalidException e) {
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Third argument must be a whole number", TextFormat.RED)));
					return;
				}
				
				if(num <= 0){
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Third argument must be greater than zero.", TextFormat.RED)));
					return;
				}
				
				for(int i = 0; i < num; ++i){
					IWorldEditor editor = new WorldEditor(player.world);
					Dungeon toGenerate = new Dungeon(editor);
					Random rand = new Random();
					toGenerate.generateNear(rand, x, z);
				}
				sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeons generated all over the place!", TextFormat.GREEN)));
				return;
			}

			IWorldEditor editor = new WorldEditor(player.world);
			Dungeon toGenerate = new Dungeon(editor);
			Random rand = Dungeon.getRandom(editor, new Coord(x, 0, z));
			toGenerate.generateNear(rand, x, z);
			
			try {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + toGenerate.getPosition().toString(), TextFormat.GREEN)));
			} catch (Exception e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Unable to generate dungeon", TextFormat.RED)));
			}
			return;
			
		} else {
			
			try {
				x = CommandBase.parseInt(ap.get(0));
				z = CommandBase.parseInt(ap.get(1));
			} catch (NumberInvalidException e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
				return;
			}
			
			if(ap.hasEntry(2)){
				settingName = ap.get(2);
			}
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
			
			Random rand = Dungeon.getRandom(editor, new Coord(x, 0, z));
			ISettings settings = null; 
					
			try{
				settings = Dungeon.settingsResolver.getWithName(settingName, editor, rand, new Coord(x, 0, z));	
			} catch(Exception e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: " + e.getMessage(), TextFormat.RED)));
				return;
			}
			
			
			if(settings == null){
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failed: " + settingName + " not found.", TextFormat.RED)));
				return;
			}
			
			Dungeon dungeon = new Dungeon(editor);
			dungeon.generate(settings, new Coord(x, 0, z));
			try {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Success: \"" + settingName + "\" Dungeon generated at " + dungeon.getPosition().toString(), TextFormat.GREEN)));
			} catch (Exception e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Unable to generate dungeon", TextFormat.RED)));
			}
			return;
		}
		
		Random rand = Dungeon.getRandom(editor, new Coord(x, 0, z));
		
		ISettings settings = null; 
				
		try{
			settings = Dungeon.settingsResolver.getSettings(editor, rand, new Coord(x, 0, z));
		} catch(Exception e){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: " + e.getMessage(), TextFormat.RED)));
			e.printStackTrace();
			return;
		}
		
		if(settings != null){
			IDungeon dungeon = new Dungeon(editor);
			dungeon.generate(settings, new Coord(x, 0, z));
			sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + Integer.toString(x) + " " + Integer.toString(z), TextFormat.GREEN)));
			return;
		}
		
		IDungeon dungeon = new Dungeon(editor);
		dungeon.generate(Dungeon.settingsResolver.getDefaultSettings(), new Coord(x, 0, z));
		sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + Integer.toString(x) + " " + Integer.toString(z), TextFormat.GREEN)));
		return;
	}
}
