package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.ItemSpecialty;
import greymerk.roguelike.treasure.loot.Quality;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestSmithy extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){

		int middle = chest.getSizeInventory()/2;
						
		chest.setInventorySlotContents(middle - 2, ItemSpecialty.getRandomItem(ItemSpecialty.SWORD, rand, Quality.IRON));
		chest.setInventorySlotContents(middle, ItemSpecialty.getRandomArmour(rand, rand.nextBoolean() ? Quality.STONE : Quality.IRON));
		chest.setInventorySlotContents(middle + 2, ItemSpecialty.getRandomTool(rand, Quality.IRON));
	}
}
