package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestOre extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, LootSettings loot, int level){

		int quantity = 4 + rand.nextInt(4);
		
		ItemStack item;

		for (int i = 0; i < quantity; i++) {
			item = loot.get(Loot.ORE, rand);			
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
