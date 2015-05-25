package greymerk.roguelike.util;


import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.settings.ICatacombSettings;
import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.config.RogueConfig;
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
		
		if(args.length == 0) return;
		
		if(args[0].equals("config")){
			if(args[1].equals("reload")){
				RogueConfig.reload(true);
			}
			return;
		}
		
		if(args[0].equals("give")){
			EntityPlayerMP player = null;
			try {
				player = getCommandSenderAsPlayer(sender);
			} catch (PlayerNotFoundException e) {
				sender.addChatMessage(new ChatComponentText("No such player"));
				return;
			}
			ItemStack item = ItemNovelty.getItemByName(args[1]);
			EntityItem drop = player.entityDropItem(item, 0);
			drop.setNoPickupDelay();
			return;
		}
		
		if(args[0].equals("dungeon")){
			int x;
			int z;
			try {
				x = parseInt(args[1]);
				z = parseInt(args[2]);
			} catch (NumberInvalidException e) {
				sender.addChatMessage(new ChatComponentText("Numeric error"));
				return;
			}
			
			
			World world = sender.getEntityWorld();
			
			if(args.length == 4){
				String name = args[3];
				Catacomb.initResolver();
				CatacombSettings setting = Catacomb.settingsResolver.getByName(name);
				if(setting == null){
					System.err.println(name + " not found.");
					return;
				}
				Catacomb.generate(world, setting, x, z);
				return;
			}
			
			Random rand = Catacomb.getRandom(world, x, z);
			ICatacombSettings settings = Catacomb.settingsResolver.getSettings(world, rand, new Coord(x, 0, z));
			if(settings != null){
				Catacomb.generate(world, settings, x, z);
				return;
			}
			
			Catacomb.generate(world, Catacomb.settingsResolver.getDefaultSettings(), x, z);
		}
		
		if(args[0].equals("citadel")){
			int x;
			int z;
			try {
				x = parseInt(args[1]);
				z = parseInt(args[2]);
			} catch (NumberInvalidException e) {
				sender.addChatMessage(new ChatComponentText("Numeric error"));
				return;
			}
			
			World world = sender.getEntityWorld();
			Citadel.generate(world, x, z);
		}
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
