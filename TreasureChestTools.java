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
		
		if(rand.nextInt(10 + (rank * 3)) == 0){
			switch(rand.nextInt(3)){
			case 0:
				item = ItemSpecialty.getItem(ItemSpecialty.PICK, rand, rank);
				break;
			case 1:
				item = ItemSpecialty.getItem(ItemSpecialty.AXE, rand, rank);
				break;
			case 2:
				item = ItemSpecialty.getItem(ItemSpecialty.SHOVEL, rand, rank);
				break;
			}
		} else {
			item = ItemLoot.getTool(rand, rank);
		}
		chest.setInventorySlotContents(middle, item);
		
		item = ItemLoot.getOre(rand, rank);
		chest.setInventorySlotContents(middle + 1, item);
	}
}
