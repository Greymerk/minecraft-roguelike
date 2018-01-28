package greymerk.roguelike.command.routes;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.command.ICommandContext;
import greymerk.roguelike.command.MessageType;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;

public class CommandRouteDungeon extends CommandRouteBase{

	@Override
	public void execute(ICommandContext context, List<String> args) {
		
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			context.sendMessage("Usage: roguelike dungeon {X Z | here} [setting]", MessageType.INFO);
			return;
		}
		
		Coord pos;
		try{
			pos = getLocation(context, args);	
		} catch(Exception e){
			return;
		}

		String settingName = null;
		if(ap.match(0, "here") || ap.match(0, "nearby")){
			settingName = ap.get(1);
		} else {
			settingName = ap.get(2);
		}

		IWorldEditor editor = context.createEditor();
		
		if(settingName != null){
			try{
				Dungeon.initResolver();
			} catch(Exception e) {
				context.sendMessage("Failure: " + e.getMessage(), MessageType.ERROR);
				return;
			}
			
			Random rand = Dungeon.getRandom(editor, pos);
			ISettings settings = null; 
					
			try{
				settings = Dungeon.settingsResolver.getWithName(settingName, editor, rand, pos);	
			} catch(Exception e) {
				context.sendMessage("Failure: " + e.getMessage(), MessageType.ERROR);
				return;
			}
			
			
			if(settings == null){
				context.sendMessage("Failed: " + settingName + " not found.", MessageType.ERROR);
				return;
			}
			
			Dungeon dungeon = new Dungeon(editor);
			dungeon.generate(settings, pos);
			try {
				context.sendMessage("Success: \"" + settingName + "\" Dungeon generated at " + dungeon.getPosition().toString(), MessageType.ERROR);
			} catch (Exception e) {
				context.sendMessage("Failure: Unable to generate dungeon", MessageType.ERROR);
			}
			return;
		}
		
		Random rand = Dungeon.getRandom(editor, pos);
		
		ISettings settings = null; 
				
		try{
			settings = Dungeon.settingsResolver.getSettings(editor, rand, pos);
		} catch(Exception e){
			context.sendMessage("Failure: " + e.getMessage(), MessageType.ERROR);
			e.printStackTrace();
			return;
		}
		
		if(settings != null){
			IDungeon dungeon = new Dungeon(editor);
			dungeon.generate(settings, pos);
			context.sendMessage("Success: Dungeon generated at " + pos.toString(), MessageType.SUCCESS);
			return;
		}
		
		IDungeon dungeon = new Dungeon(editor);
		dungeon.generate(Dungeon.settingsResolver.getDefaultSettings(), pos);
		context.sendMessage("Success: Dungeon generated at " + pos.toString(), MessageType.SUCCESS);
		return;
	}
	
	public static Coord getLocation(ICommandContext context, List<String> args) throws NumberInvalidException, PlayerNotFoundException{
		ArgumentParser ap = new ArgumentParser(args);

		Coord pos = context.getPos();
		
		if(ap.match(0, "here") || ap.match(0, "nearby")){
			return new Coord((int) pos.getX(), 0, (int) pos.getZ());
		} else {
			try {
				int x = CommandBase.parseInt(ap.get(0));
				int z = CommandBase.parseInt(ap.get(1));
				return new Coord(x, 0, z);
			} catch (NumberInvalidException e) {
				context.sendMessage("Failure: Invalid Coords: X Z", MessageType.ERROR);
				throw(e);
			}
		}
	}
}
