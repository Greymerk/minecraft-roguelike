package greymerk.roguelike.util;


import java.util.List;
import java.util.Random;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class CommandSpawnDungeon extends CommandBase
{
	public String getCommandName(){
		return "roguelike";
	}

	/**
	 * Return the required permission level for this command.
	 */
	public int getRequiredPermissionLevel(){
		return 2;
	}

	public String getCommandUsage(ICommandSender par1ICommandSender){
		return "";
	}

	public void processCommand(ICommandSender sender, String[] args){
		
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			sender.addChatMessage(new ChatComponentText(TextFormat.apply("Usage: roguelike [dungeon | give | config]", TextFormat.GRAY)));
			return;
		}
		
		if(ap.match(0, "config")){
			if(!ap.hasEntry(1)){
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Usage: roguelike config reload", TextFormat.GRAY)));
				return;
			}
			if(ap.match(1, "reload")){
				RogueConfig.reload(true);
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Configurations Reloaded", TextFormat.GREEN)));
				return;
			}
			return;
		}
		
		if(ap.match(0, "settings")){
			if(!ap.hasEntry(1)){
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Usage: roguelike settings [reload | list]", TextFormat.GRAY)));
				return;
			}
			if(ap.match(1, "reload")){
				Dungeon.initResolver();
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Settings Reloaded", TextFormat.GREEN)));
				return;
			}
			if(ap.match(1, "list")){
				sender.addChatMessage(new ChatComponentText(TextFormat.apply(Dungeon.settingsResolver.toString(), TextFormat.GREEN)));
				return;
			}
			
			return;
		}
		
		if(ap.match(0, "give")){
			if(!ap.hasEntry(1)){
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Usage: roguelike give novelty_name", TextFormat.GRAY)));
				return;
			}
			
			EntityPlayerMP player = null;
			try {
				player = getCommandSenderAsPlayer(sender);
			} catch (PlayerNotFoundException e) {
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failed: Player error", TextFormat.RED)));
				return;
			}
			ItemStack item = ItemNovelty.getItemByName(ap.get(1));
			if(item == null){
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failed: No such item", TextFormat.RED)));
				return;
			}
			EntityItem drop = player.entityDropItem(item, 0);
			drop.delayBeforeCanPickup = 0;
			sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Given " + item.getDisplayName(), TextFormat.GREEN)));
			return;
		}
		
		if(ap.match(0, "dungeon")){
			
			if(!ap.hasEntry(1)){
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Usage: roguelike dungeon {X Z | here} [setting]", TextFormat.GRAY)));
				return;
			}
			

			int x;
			int z;
			
			String settingName = null;
			
			if(ap.match(1, "here")){
				EntityPlayerMP player = null;
				try {
					player = getCommandSenderAsPlayer(sender);
				} catch (PlayerNotFoundException e) {
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Cannot find player", TextFormat.RED)));
					return;
				}
				x = (int) player.posX;
				z = (int) player.posZ;
				
				if(ap.hasEntry(2)){
					settingName = ap.get(2);
				}
			} else if(ap.match(1, "nearby")){
				EntityPlayerMP player = null;
				try {
					player = getCommandSenderAsPlayer(sender);
				} catch (PlayerNotFoundException e) {
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Cannot find player", TextFormat.RED)));
					return;
				}
				
				x = (int) player.posX;
				z = (int) player.posZ;
				
				if(ap.hasEntry(2)){
					int num = 0;
					try {
						num = parseInt(sender, ap.get(2));
					} catch (NumberInvalidException e) {
						sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Third argument must be a whole number", TextFormat.RED)));
						return;
					}
					
					if(num <= 0){
						sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Third argument must be greater than zero.", TextFormat.RED)));
						return;
					}
					
					for(int i = 0; i < num; ++i){
						
						WorldEditor editor = new WorldEditor(player.worldObj);
						Dungeon toGenerate = new Dungeon(editor);
						Random rand = new Random();
						toGenerate.generateNear(rand, x, z);
					}
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Dungeons generated all over the place!", TextFormat.GREEN)));
					return;
				}

				WorldEditor editor = new WorldEditor(player.worldObj);
				Dungeon toGenerate = new Dungeon(editor);
				Random rand = Dungeon.getRandom(editor, x, z);
				toGenerate.generateNear(rand, x, z);
				
				try {
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Dungeon generated at " + toGenerate.getPosition().toString(), TextFormat.GREEN)));
				} catch (Exception e) {
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Unable to generate dungeon", TextFormat.RED)));
				}
				return;
				
			} else {
				
				try {
					x = parseInt(sender, ap.get(1));
					z = parseInt(sender, ap.get(2));
				} catch (NumberInvalidException e) {
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
					return;
				}
				
				if(ap.hasEntry(3)){
					settingName = ap.get(3);
				}
			}
			
			World world = sender.getEntityWorld();
			WorldEditor editor = new WorldEditor(world);
			
			if(settingName != null){
				Dungeon.initResolver();
				ISettings settings = Dungeon.settingsResolver.getWithDefault(settingName);
				 
				if(settings == null){
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failed: " + settingName + " not found.", TextFormat.RED)));
					return;
				}
				
				Dungeon dungeon = new Dungeon(editor);
				dungeon.generate(settings, x, z);
				try {
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: \"" + settingName + "\" Dungeon generated at " + dungeon.getPosition().toString(), TextFormat.GREEN)));
				} catch (Exception e) {
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Unable to generate dungeon", TextFormat.RED)));
				}
				return;
			}
			
			Random rand = Dungeon.getRandom(editor, x, z);
			ISettings settings = Dungeon.settingsResolver.getSettings(editor, rand, new Coord(x, 0, z));
			if(settings != null){
				IDungeon dungeon = new Dungeon(editor);
				dungeon.generate(settings, x, z);
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Dungeon generated at " + Integer.toString(x) + " " + Integer.toString(z), TextFormat.GREEN)));
				return;
			}
			
			IDungeon dungeon = new Dungeon(editor);
			dungeon.generate(Dungeon.settingsResolver.getDefaultSettings(), x, z);
			sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Dungeon generated at " + Integer.toString(x) + " " + Integer.toString(z), TextFormat.GREEN)));
			return;
		}
		
		if(ap.match(0, "citadel")){
			int x;
			int z;
			try {
				x = parseInt(sender, ap.get(1));
				z = parseInt(sender, ap.get(2));
			} catch (NumberInvalidException e) {
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
				return;
			}
			
			World world = sender.getEntityWorld();
			Citadel.generate(new WorldEditor(world), x, z);
			return;
		}
		
		// user typed an invalid first argument
		sender.addChatMessage(new ChatComponentText(TextFormat.apply("Usage: roguelike [dungeon | give | config]", TextFormat.GRAY)));
		
	}

	/**
	 * Adds the strings available in this command to the given list of tab completion options.
	 */
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr){
		return null;
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2){
		return par2 == 0;
	}
}
