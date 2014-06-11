package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestBlocks extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){
		
		int quantity = 6 + rand.nextInt(6);
		
		ItemStack item;

		for (int i = 0; i < quantity; i++) {
			item = Loot.getLoot(Loot.BLOCK, rand, level);		
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
