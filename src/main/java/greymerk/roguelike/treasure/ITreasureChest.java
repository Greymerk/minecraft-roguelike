package greymerk.roguelike.treasure;

import java.util.Random;

import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.item.ItemStack;

public interface ITreasureChest {
		
	public ITreasureChest generate(WorldEditor editor, Random rand, LootSettings loot, Coord pos, int level, boolean trapped);
	
	public boolean setSlot(int slot, ItemStack item);
	
	public boolean setRandomEmptySlot(ItemStack item);
	
	public boolean isEmptySlot(int slot);
	
	public Treasure getType();
	
	public int getSize();
}