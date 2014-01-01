package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.ItemFood;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Slot;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestWeapons extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){
		
		int level = Catacomb.getLevel(posY);
		
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;
		
		item = new ItemStack(Item.arrow, 2 + rand.nextInt(6));
		chest.setInventorySlotContents(middle - 1, item);
		
		item = Loot.getEquipmentBySlot(rand, Slot.WEAPON, level, true);
		chest.setInventorySlotContents(middle, item);
		
		item = ItemFood.getRandom(rand, level);
		chest.setInventorySlotContents(middle + 1, item);
	}

}
