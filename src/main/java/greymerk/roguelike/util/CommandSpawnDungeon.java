package greymerk.roguelike.util;


import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CommandSpawnDungeon extends CommandBase
{
    public String getCommandName(){
        return "dungeon";
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
    	
    	if(args[0].equals("config")){
    		if(args[1].equals("reload")){
    			RogueConfig.reload(true);
    		}
    		return;
    	}
    	
    	if(args[0].equals("give")){
    		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
    		ItemStack item = ItemNovelty.getItemByName(args[1]);
    		EntityItem drop = player.entityDropItem(item, 0);
    		drop.delayBeforeCanPickup = 0;
    		notifyAdmins(sender, "commands.give.success", new Object[] {item.getDisplayName(), item.getItem(), 1, player.getDisplayName()});
    		return;
    	}
    	
    	
    	int x = parseInt(sender, args[0]);
    	int z = parseInt(sender, args[1]);
    	
    	World world = sender.getEntityWorld();    	
    	Catacomb.generate(world, x, z);
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
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
