package greymerk.roguelike;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestArmour extends TreasureChestBase{
	
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
		
		item = ItemLoot.getPotion(rand, rank);
		chest.setInventorySlotContents(middle - 1, item);
		
		int slot = 1 + rand.nextInt(4);
		
		if(rand.nextInt(5 + (rank * 3)) == 0){
			switch(slot){
			case ItemLoot.HEAD:
				item = ItemSpecialty.getItem(ItemSpecialty.HELMET, rand, rank);
				break;
			case ItemLoot.CHEST:
				item = ItemSpecialty.getItem(ItemSpecialty.CHEST, rand, rank);
				break;
			case ItemLoot.LEGS:
				item = ItemSpecialty.getItem(ItemSpecialty.LEGS, rand, rank);
				break;
			case ItemLoot.FEET:
				item = ItemSpecialty.getItem(ItemSpecialty.FEET, rand, rank);
				break;
			}	
		} else {
			item = ItemLoot.getEquipmentBySlot(rand, slot, rank, true);
		}

		chest.setInventorySlotContents(middle, item);
		
		item = ItemLoot.getFood(rand, rank);
		chest.setInventorySlotContents(middle + 1, item);
	}

}
