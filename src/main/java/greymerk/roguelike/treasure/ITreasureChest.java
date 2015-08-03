package greymerk.roguelike.treasure;

import java.util.Random;

import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.item.ItemStack;

public interface ITreasureChest {
		
	public ITreasureChest generate(WorldEditor editor, Random rand, LootSettings loot, Coord pos, int level, boolean trapped);
	
	public boolean setInventorySlot(ItemStack item, int slot);
	
	public int getInventorySize();
	
	public boolean slotEmpty(int slot);
	
}