package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestSmithy extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){

		int middle = chest.getSizeInventory()/2;
					
		ItemStack item;
		
		item = Loot.getLoot(Loot.ORE, rand, level);
		chest.setInventorySlotContents(middle - 1, item); 
		
		item = Loot.getLoot(Loot.SMITHY, rand, level);
		chest.setInventorySlotContents(middle, item);
		
		item = Loot.getLoot(Loot.ORE, rand, level);
		chest.setInventorySlotContents(middle + 1, item);

	}
}
