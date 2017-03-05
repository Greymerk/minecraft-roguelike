package greymerk.roguelike.monster;

import java.util.Random;

import net.minecraft.world.World;

public interface IMonsterProfile {
	
	public void addEquipment(World world, Random rand, int level, IEntity mob);
	
}
