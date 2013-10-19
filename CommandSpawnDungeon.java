package greymerk.roguelike;


import java.util.List;
import java.util.Random;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

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
    	
    	if(args[0].equals("give")){
    		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
    		ItemStack item = ItemNovelty.getItemByName(args[1]);
    		EntityItem drop = player.dropPlayerItem(item);
    		drop.delayBeforeCanPickup = 0;
    		notifyAdmins(sender, "commands.give.success", new Object[] {item.getDisplayName(), item.itemID, 1, player.getEntityName()});
    		return;
    	}
    	
    	
    	int x = parseInt(sender, args[0]);
    	int z = parseInt(sender, args[1]);
    	
    	World world = sender.getEntityWorld();
    	
    	Random rand = new Random();
    	rand.setSeed(world.getSeed() | x | z);
    	
    	Catacomb cata = new Catacomb(world, rand);
    	cata.generate(x, z);
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
