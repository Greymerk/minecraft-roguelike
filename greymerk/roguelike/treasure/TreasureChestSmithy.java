package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ItemSpecialty;
import greymerk.roguelike.treasure.loot.Quality;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestSmithy extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){

		int middle = chest.getSizeInventory()/2;
						
		if(level == 0){
			chest.setInventorySlotContents(middle, ItemSpecialty.getRandomItem(Equipment.SWORD, rand, Quality.IRON));
		} else {
			chest.setInventorySlotContents(middle - 1, ItemSpecialty.getRandomArmour(rand, rand.nextBoolean() ? Quality.STONE : Quality.IRON));
			chest.setInventorySlotContents(middle + 1, ItemSpecialty.getRandomTool(rand, Quality.IRON));	
		}
	}
}
