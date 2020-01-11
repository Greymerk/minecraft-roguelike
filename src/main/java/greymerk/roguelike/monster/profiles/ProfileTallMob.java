package greymerk.roguelike.monster.profiles;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Slot;

public class ProfileTallMob implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {
    for (EntityEquipmentSlot slot : new EntityEquipmentSlot[]{
        EntityEquipmentSlot.HEAD,
        EntityEquipmentSlot.CHEST,
        EntityEquipmentSlot.LEGS,
        EntityEquipmentSlot.FEET
    }) {
      ItemStack item = Loot.getEquipmentBySlot(rand, Slot.getSlot(slot), level, Enchant.canEnchant(world.getDifficulty(), rand, level));
      mob.setSlot(slot, item);
    }

  }

}
