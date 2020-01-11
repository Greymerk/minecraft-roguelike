package greymerk.roguelike.monster.profiles;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class ProfileAshlea implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {

    mob.setChild(true);

    MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, level, mob);

    ItemStack weapon = ItemNovelty.getItem(ItemNovelty.ASHLEA);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, weapon);

    for (EntityEquipmentSlot slot : new EntityEquipmentSlot[]{
        EntityEquipmentSlot.HEAD,
        EntityEquipmentSlot.CHEST,
        EntityEquipmentSlot.LEGS,
        EntityEquipmentSlot.FEET
    }) {
      ItemStack item = ItemArmour.get(rand, Slot.getSlot(slot), Quality.WOOD);
      ItemArmour.dyeArmor(item, 255, 100, 255);
      mob.setSlot(slot, item);
    }
  }
}
