package greymerk.roguelike.util;


import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.CatacombSettings;
import greymerk.roguelike.dungeon.settings.ICatacombSettings;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;

import java.util.List;
import java.util.Random;

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
			drop.setNoPickupDelay();
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
				
			} else {
				
				try {
					x = parseInt(ap.get(1));
					z = parseInt(ap.get(2));
				} catch (NumberInvalidException e) {
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
					return;
				}
				
				if(ap.hasEntry(3)){
					settingName = ap.get(3);
				}
			}
			
			World world = sender.getEntityWorld();
		
			if(settingName != null){
				Dungeon.initResolver();
				CatacombSettings setting = Dungeon.settingsResolver.getByName(settingName);
				if(setting == null){
					sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failed: " + settingName + " not found.", TextFormat.RED)));
					return;
				}
				Dungeon.generate(world, setting, x, z);
				return;
			}
			
			Random rand = Dungeon.getRandom(world, x, z);
			ICatacombSettings settings = Dungeon.settingsResolver.getSettings(world, rand, new Coord(x, 0, z));
			if(settings != null){
				Dungeon.generate(world, settings, x, z);
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Dungeon generated at " + Integer.toString(x) + " " + Integer.toString(z), TextFormat.GREEN)));
				return;
			}
			
			Dungeon.generate(world, Dungeon.settingsResolver.getDefaultSettings(), x, z);
			sender.addChatMessage(new ChatComponentText(TextFormat.apply("Success: Dungeon generated at " + Integer.toString(x) + " " + Integer.toString(z), TextFormat.GREEN)));
			return;
		}
		
		if(ap.match(0, "citadel")){
			int x;
			int z;
			try {
				x = parseInt(ap.get(1));
				z = parseInt(ap.get(2));
			} catch (NumberInvalidException e) {
				sender.addChatMessage(new ChatComponentText(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
				return;
			}
			
			World world = sender.getEntityWorld();
			Citadel.generate(world, x, z);
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
