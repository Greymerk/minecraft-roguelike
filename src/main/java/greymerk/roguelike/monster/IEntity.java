package greymerk.roguelike.monster;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public interface IEntity {

  void setSlot(EntityEquipmentSlot slot, ItemStack item);

  void setMobClass(MobType type, boolean clear);

  void setChild(boolean child);

  boolean instance(Class<?> type);

  void setName(String name);

}
