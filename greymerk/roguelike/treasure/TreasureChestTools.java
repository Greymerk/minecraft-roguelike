package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestTools extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){

		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;		
		
		item = Loot.getLoot(Loot.BLOCK, rand, 0);
		chest.setInventorySlotContents(middle - 1, item);
		
		item = Loot.getLoot(Loot.TOOL, rand, level);
		chest.setInventorySlotContents(middle, item);
		
		item = Loot.getLoot(Loot.ORE, rand, 0);
		chest.setInventorySlotContents(middle + 1, item);
	}
}
