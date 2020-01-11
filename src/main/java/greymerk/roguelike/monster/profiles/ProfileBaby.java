package greymerk.roguelike.monster.profiles;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.provider.ItemTool;

public class ProfileBaby implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random rand, int level, IEntity mob) {
    mob.setChild(true);

    if (rand.nextBoolean()) {
      MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, level, mob);
    }

    ItemStack weapon = ItemTool.getRandom(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
    mob.setSlot(EntityEquipmentSlot.MAINHAND, weapon);
  }

}
