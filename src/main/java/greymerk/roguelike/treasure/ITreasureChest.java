package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Coord;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ITreasureChest {
		
	public ITreasureChest generate(World world, Random rand, LootSettings loot, int x, int y, int z, int level, boolean trapped);
	
	public ITreasureChest generate(World world, Random rand, LootSettings loot, int x, int y, int z);
	
	public ITreasureChest generate(World world, Random rand, LootSettings loot, Coord pos);

	public boolean setInventorySlot(ItemStack item, int slot);
	
	public int getInventorySize();
	
	public boolean slotEmpty(int slot);
	
}