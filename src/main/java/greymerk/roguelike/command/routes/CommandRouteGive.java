package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.TextFormat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandRouteGive extends CommandRouteBase{
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, List<String> args) {
		ArgumentParser ap = new ArgumentParser(args);
		
		if(!ap.hasEntry(0)){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Usage: roguelike give novelty_name", TextFormat.GRAY)));
			return;
		}
		
		EntityPlayerMP player = null;
		try {
			player = CommandBase.getCommandSenderAsPlayer(sender);
		} catch (PlayerNotFoundException e) {
			sender.sendMessage(new TextComponentString(TextFormat.apply("Failed: Player error", TextFormat.RED)));
			return;
		}
		ItemStack item = ItemNovelty.getItemByName(ap.get(0));
		if(item == null){
			sender.sendMessage(new TextComponentString(TextFormat.apply("Failed: No such item", TextFormat.RED)));
			return;
		}
		EntityItem drop = player.entityDropItem(item, 0);
		drop.setNoPickupDelay();
		sender.sendMessage(new TextComponentString(TextFormat.apply("Success: Given " + item.getDisplayName(), TextFormat.GREEN)));
		return;
	}
}
