package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Potion;
import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestPotions extends TreasureChestBase{
	
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){
		int middle = chest.getSizeInventory()/2;
		
		ItemStack item;
		item = Loot.getLoot(Loot.POTION, rand, level);	
		chest.setInventorySlotContents(middle - 1, Potion.getRandom(rand));

		item = Loot.getLoot(Loot.POTION, rand, level);
		chest.setInventorySlotContents(middle, item);

		item = Loot.getLoot(Loot.POTION, rand, level);
		chest.setInventorySlotContents(middle + 1, item);
		
	}
}
