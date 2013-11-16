package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Slot;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestArmour extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){

		int rank = Catacomb.getRank(posY);
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;
		
		item = Loot.getLootByCategory(Loot.POTION, rand, rank);
		chest.setInventorySlotContents(middle - 1, item);
		
		item = Loot.getLootByCategory(Loot.ARMOUR, rand, rank);
		chest.setInventorySlotContents(middle, item);
		
		item = Loot.getLootByCategory(Loot.FOOD, rand, rank);
		chest.setInventorySlotContents(middle + 1, item);
	}

}
