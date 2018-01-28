package greymerk.roguelike.command;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.item.ItemStack;

public interface ICommandContext {

	public void sendMessage(String message, MessageType type);
	
	public IWorldEditor createEditor();
	
	public Coord getPos();
	
	public void give(ItemStack item);
	
}
