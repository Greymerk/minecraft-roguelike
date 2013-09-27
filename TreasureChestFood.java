package greymerk.roguelike;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestFood extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){

		
		
		int rank = Dungeon.getRank(posY);
		
		int middle;
		try{
			middle = chest.getSizeInventory()/2;
		} catch(NullPointerException e){
			return;
		}
						
		ItemStack item;		
		
		for(int i = 0; i < 3; i++){
			item = ItemLoot.getFood(rand, rank);
			chest.setInventorySlotContents((middle - 1) + i, item);
		}
	}

}
