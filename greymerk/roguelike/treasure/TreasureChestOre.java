package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestOre extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){

		int level = Catacomb.getLevel(posY);
		int middle = chest.getSizeInventory()/2;
		
		ItemStack item;

		for (int i = 0; i < 2 + rand.nextInt(2 + level); i++) {
			item = Loot.getLootByCategory(Loot.ORE, rand, level);			
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
