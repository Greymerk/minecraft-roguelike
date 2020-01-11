package greymerk.roguelike.monster.profiles;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;

public class ProfileSkeleton implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {


    if (level == 3 && rand.nextInt(40) == 0) {
      MonsterProfile.get(MonsterProfile.POISONARCHER).addEquipment(world, rand, level, mob);
      return;
    }

    if (level > 1 && rand.nextInt(50) == 0) {
      MonsterProfile.get(MonsterProfile.MAGICARCHER).addEquipment(world, rand, level, mob);
      return;
    }

    if (level > 1 && rand.nextInt(10) == 0) {
      MonsterProfile.get(MonsterProfile.WITHER).addEquipment(world, rand, level, mob);
      return;
    }

    if (level > 0 && rand.nextInt(20) == 0) {
      MonsterProfile.get(MonsterProfile.SWORDSMAN).addEquipment(world, rand, level, mob);
      return;
    }

    MonsterProfile.get(MonsterProfile.ARCHER).addEquipment(world, rand, level, mob);
  }

}
