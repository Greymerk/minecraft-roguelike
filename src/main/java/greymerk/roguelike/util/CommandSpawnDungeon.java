package greymerk.roguelike.util;


import java.util.List;
import java.util.Random;
import java.util.Set;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

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

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
		
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike [dungeon | give | config | settings]", TextFormat.GRAY)));
			return;
		}
		
		if(ap.match(0, "structure")){
			String name = ap.hasEntry(1) ? ap.get(1) : VanillaStructure.getName(VanillaStructure.STRONGHOLD);			
			World world = sender.getEntityWorld();
			
			IWorldEditor editor = new WorldEditor(world);
			
			VanillaStructure type = VanillaStructure.getType(name);
			if(type == null){
				sender.sendMessage(new TextComponentString(TextFormat.apply(name + " type name invalid", TextFormat.RED)));
				return;
			}
			
			Coord here = new Coord(sender.getPosition());
			Coord structure = editor.findNearestStructure(type, here);
			
			if(structure == null){
				sender.sendMessage(new TextComponentString(TextFormat.apply(name + " not found", TextFormat.RED)));
				return;
			}
			
			sender.sendMessage(new TextComponentString(TextFormat.apply("Nearest " + name + ": " + structure.toString(), TextFormat.GOLD)));
			sender.sendMessage(new TextComponentString(TextFormat.apply("Distance: " + here.distance(structure), TextFormat.GOLD)));
			return;
		}
		
		if(ap.match(0, "biome")){
			World world = sender.getEntityWorld();
			IWorldEditor editor = new WorldEditor(world);
			Coord pos;
			if(!ap.hasEntry(1)){
				pos = new Coord(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ());
			} else {
				int x = parseInt(ap.get(1));
				int z = parseInt(ap.get(2));
				pos = new Coord(x, 0, z);
			}
			sender.sendMessage(new TextComponentString(TextFormat.apply("Biome Information for " + pos.toString(), TextFormat.GOLD)));
			
			Biome biome = editor.getBiome(pos);
			sender.sendMessage(new TextComponentString(TextFormat.apply(biome.getBiomeName(), TextFormat.GOLD)));
			Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
			String types = "";
			for(BiomeDictionary.Type type : biomeTypes){
				types += type.getName() + " ";
			}
			sender.sendMessage(new TextComponentString(TextFormat.apply(types, TextFormat.GOLD)));
			return;
		}
		
		if(ap.match(0, "config")){
			if(!ap.hasEntry(1)){
				sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike config reload", TextFormat.GRAY)));
				return;
			}
			if(ap.match(1, "reload")){
				RogueConfig.reload(true);
				sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Configurations Reloaded", TextFormat.GREEN)));
				return;
			}
			return;
		}
		
		if(ap.match(0, "dim")){
			int dim = sender.getEntityWorld().provider.getDimensionType().getId();
			sender.sendMessage(new TextComponentString(TextFormat.apply("Dimension id: " + Integer.toString(dim), TextFormat.GOLD)));
			return;
		}
		
		if(ap.match(0, "settings")){
			if(!ap.hasEntry(1)){
				sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike settings [reload | list]", TextFormat.GRAY)));
				return;
			}
			if(ap.match(1, "reload")){
				try{
					Dungeon.initResolver();
					sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Settings Reloaded", TextFormat.GREEN)));
				} catch(Exception e) {
					if(e.getMessage() == null){
						sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Uncaught Exception", TextFormat.RED)));
					} else {
						sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: " + e.getMessage(), TextFormat.RED)));	
					}
				}
				return;
			}
			if(ap.match(1, "list")){
				sender.sendMessage(new TextComponentString(TextFormat.apply(Dungeon.settingsResolver.toString(), TextFormat.GREEN)));
				return;
			}
			
			return;
		}
		
		if(ap.match(0, "give")){
			if(!ap.hasEntry(1)){
				sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike give novelty_name", TextFormat.GRAY)));
				return;
			}
			
			EntityPlayerMP player = null;
			try {
				player = getCommandSenderAsPlayer(sender);
			} catch (PlayerNotFoundException e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failed: Player error", TextFormat.RED)));
				return;
			}
			ItemStack item = ItemNovelty.getItemByName(ap.get(1));
			if(item == null){
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failed: No such item", TextFormat.RED)));
				return;
			}
			EntityItem drop = player.entityDropItem(item, 0);
			drop.setNoPickupDelay();
			sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Given " + item.getDisplayName(), TextFormat.GREEN)));
			return;
		}
		
		if(ap.match(0, "dungeon")){
			
			if(!ap.hasEntry(1)){
				sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike dungeon {X Z | here} [setting]", TextFormat.GRAY)));
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
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Cannot find player", TextFormat.RED)));
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
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Cannot find player", TextFormat.RED)));
					return;
				}
				
				x = (int) player.posX;
				z = (int) player.posZ;
				
				if(ap.hasEntry(2)){
					int num = 0;
					try {
						num = parseInt(ap.get(2));
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
				Random rand = Dungeon.getRandom(editor, x, z);
				toGenerate.generateNear(rand, x, z);
				
				try {
					sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + toGenerate.getPosition().toString(), TextFormat.GREEN)));
				} catch (Exception e) {
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Unable to generate dungeon", TextFormat.RED)));
				}
				return;
				
			} else {
				
				try {
					x = parseInt(ap.get(1));
					z = parseInt(ap.get(2));
				} catch (NumberInvalidException e) {
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
					return;
				}
				
				if(ap.hasEntry(3)){
					settingName = ap.get(3);
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
				
				Random rand = Dungeon.getRandom(editor, x, z);
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
				dungeon.generate(settings, x, z);
				try {
					sender.sendMessage(new TextComponentString(TextFormat.apply("Success: \"" + settingName + "\" Dungeon generated at " + dungeon.getPosition().toString(), TextFormat.GREEN)));
				} catch (Exception e) {
					sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Unable to generate dungeon", TextFormat.RED)));
				}
				return;
			}
			
			Random rand = Dungeon.getRandom(editor, x, z);
			
			ISettings settings = null; 
					
			try{
				settings = Dungeon.settingsResolver.getSettings(editor, rand, new Coord(x, 0, z));
			} catch(Exception e){
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: " + e.getMessage(), TextFormat.RED)));
			}
			
			if(settings != null){
				IDungeon dungeon = new Dungeon(editor);
				dungeon.generate(settings, x, z);
				sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + Integer.toString(x) + " " + Integer.toString(z), TextFormat.GREEN)));
				return;
			}
			
			IDungeon dungeon = new Dungeon(editor);
			dungeon.generate(Dungeon.settingsResolver.getDefaultSettings(), x, z);
			sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Dungeon generated at " + Integer.toString(x) + " " + Integer.toString(z), TextFormat.GREEN)));
			return;
		}
		
		if(ap.match(0, "citadel")){
			int x;
			int z;
			try {
				x = parseInt(ap.get(1));
				z = parseInt(ap.get(2));
			} catch (NumberInvalidException e) {
				sender.sendMessage(new TextComponentString(TextFormat.apply("Failure: Invalid Coords: X Z", TextFormat.RED)));
				return;
			}
			
			World world = sender.getEntityWorld();
			Citadel.generate(new WorldEditor(world), x, z);
			return;
		}
		
		// user typed an invalid first argument
		sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike [dungeon | give | config]", TextFormat.GRAY)));
		
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

	@Override
	public String getName() {
		return "roguelike";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "";
	}
}
