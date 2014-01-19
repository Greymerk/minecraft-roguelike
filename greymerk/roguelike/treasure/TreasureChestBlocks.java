package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestBlocks extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){
		
		int quantity = 6 + rand.nextInt(6);
		
		ItemStack item;

		for (int i = 0; i < quantity; i++) {
			item = Loot.getLootByCategory(Loot.BLOCK, rand, level);		
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
