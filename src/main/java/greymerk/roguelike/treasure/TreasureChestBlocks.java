package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;

public class TreasureChestBlocks extends TreasureChest{
	
	@Override
	protected void fillChest(LootSettings loot, int level){
		
		int quantity = 6 + rand.nextInt(6);
		
		ItemStack item;

		for (int i = 0; i < quantity; i++) {
			item = loot.get(Loot.BLOCK, rand);		
			this.setRandomEmptySlot(item);
		}
		
		this.type = Treasure.BLOCKS;
	}
}
