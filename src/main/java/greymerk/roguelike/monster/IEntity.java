package greymerk.roguelike.monster;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public interface IEntity {

	public void setSlot(EntityEquipmentSlot slot, ItemStack item);
	
	public void setMobClass(MobType type, boolean clear);
	
	public void setChild(boolean child);
	
	public boolean instance(Class<?> type);
	
}
