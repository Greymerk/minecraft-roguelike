package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestOre extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){

		int quantity = 4 + rand.nextInt(4);
		
		ItemStack item;

		for (int i = 0; i < quantity; i++) {
			item = Loot.getLoot(Loot.ORE, rand, level);			
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
