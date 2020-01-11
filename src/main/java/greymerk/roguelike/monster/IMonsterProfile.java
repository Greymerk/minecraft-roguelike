package greymerk.roguelike.monster;

import net.minecraft.world.World;

import java.util.Random;

public interface IMonsterProfile {

  void addEquipment(World world, Random rand, int level, IEntity mob);

}
