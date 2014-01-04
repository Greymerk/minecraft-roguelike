package greymerk.roguelike.treasure;

import java.util.Random;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public interface ITreasureChest {
		
	public ITreasureChest generate(World world, Random rand, int x, int y, int z, boolean trapped);
	
	public ITreasureChest generate(World world, Random rand, int x, int y, int z);

	public boolean setInventorySlot(ItemStack item, int slot);
	
	public int getInventorySize();
	
}