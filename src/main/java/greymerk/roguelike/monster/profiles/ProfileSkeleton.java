package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ProfileSkeleton implements IMonsterProfile{

	@Override
	public void addEquipment(World world, Random rand, int level, Entity mob) {
		
		if(level > 1 && rand.nextInt(150) == 0){
			MonsterProfile.get(MonsterProfile.MAGICARCHER).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(level > 2 && rand.nextInt(100) == 0){
			MonsterProfile.get(MonsterProfile.POISONARCHER).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(level > 0 && rand.nextInt(30) == 0){
			MonsterProfile.get(MonsterProfile.SWORDSMAN).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(level > 1 && rand.nextInt(20) == 0){
			MonsterProfile.get(MonsterProfile.WITHER).addEquipment(world, rand, level, mob);
			return;
		}

		MonsterProfile.get(MonsterProfile.ARCHER).addEquipment(world, rand, level, mob);
	}

}
