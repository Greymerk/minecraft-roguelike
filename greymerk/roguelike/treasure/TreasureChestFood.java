package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestFood extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){

		
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;		
		
		for(int i = 0; i < 3; i++){
			item = Loot.getLootByCategory(Loot.FOOD, rand, level);
			chest.setInventorySlotContents((middle - 1) + i, item);
		}
	}

}
