package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;

public class TreasureChestOre extends TreasureChest{
	
	@Override
	protected void fillChest(LootSettings loot, int level){

		int quantity = 4 + rand.nextInt(4);
		
		ItemStack item;

		for (int i = 0; i < quantity; i++) {
			item = loot.get(Loot.ORE, rand);			
			this.setRandomEmptySlot(item);
		}
		
		this.type = Treasure.ORE;
	}
}
