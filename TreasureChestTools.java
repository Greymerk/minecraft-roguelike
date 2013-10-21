package greymerk.roguelike;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestTools extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){

		int rank = Dungeon.getRank(posY);
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;		
		
		item = new ItemStack(Block.torchWood, 4 + rand.nextInt(12));
		chest.setInventorySlotContents(middle - 1, item);
		
		item = ItemLoot.getTool(rand, rank, true);
		chest.setInventorySlotContents(middle, item);
		
		item = ItemLoot.getOre(rand, rank);
		chest.setInventorySlotContents(middle + 1, item);
	}
}
