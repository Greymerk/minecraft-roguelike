package greymerk.roguelike.monster.profiles;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;

public class ProfileJohnny implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {
    mob.setMobClass(MobType.VINDICATOR, false);
    mob.setSlot(EntityEquipmentSlot.MAINHAND, ItemSpecialty.getRandomItem(Equipment.AXE, rand, 4));
    MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, 3, mob);
    mob.setName("Johnny");
  }

}
