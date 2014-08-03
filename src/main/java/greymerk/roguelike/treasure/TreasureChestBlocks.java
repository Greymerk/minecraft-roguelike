package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestBlocks extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, LootSettings loot, int level){
		
		int quantity = 6 + rand.nextInt(6);
		
		ItemStack item;

		for (int i = 0; i < quantity; i++) {
			item = loot.get(Loot.BLOCK, rand);		
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
