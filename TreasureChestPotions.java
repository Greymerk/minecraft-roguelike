package greymerk.roguelike;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestPotions extends TreasureChestBase{
	
	
	@Override
	protected void fillChest(TileEntityChest chest){
		int middle = chest.getSizeInventory()/2;
		
		chest.setInventorySlotContents(middle - 1, Potion.getRandom(rand, Dungeon.getRank(posY)));

		chest.setInventorySlotContents(middle, Potion.getSpecific(rand, Dungeon.getRank(posY), Potion.HEALING));

		chest.setInventorySlotContents(middle + 1, Potion.getRandom(rand, Dungeon.getRank(posY)));
		
	}
}
