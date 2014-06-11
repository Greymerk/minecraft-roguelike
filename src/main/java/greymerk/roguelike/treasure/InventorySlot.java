package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;

public class InventorySlot{
	
	int slot;
	ITreasureChest chest;
	
	public InventorySlot(int index, ITreasureChest chest){
		this.slot = index;
		this.chest = chest;
	}
	
	public void set(ItemStack item){
		chest.setInventorySlot(item, slot);
	}
	
	public boolean empty(){
		return chest.slotEmpty(slot);
	}
}

