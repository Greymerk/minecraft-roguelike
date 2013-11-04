package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Potion;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestPotions extends TreasureChestBase{
	
	
	@Override
	protected void fillChest(TileEntityChest chest){
		int middle = chest.getSizeInventory()/2;
		
		chest.setInventorySlotContents(middle - 1, Potion.getRandom(rand, Catacomb.getRank(posY)));

		chest.setInventorySlotContents(middle, Potion.getSpecific(rand, Catacomb.getRank(posY), Potion.HEALING));

		chest.setInventorySlotContents(middle + 1, Potion.getRandom(rand, Catacomb.getRank(posY)));
		
	}
}
