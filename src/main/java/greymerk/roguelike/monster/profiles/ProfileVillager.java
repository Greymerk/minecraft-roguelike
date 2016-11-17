package greymerk.roguelike.monster.profiles;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.monster.IMonsterProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class ProfileVillager implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, Entity mob) {
		EntityZombie zombie = (EntityZombie)mob;
		
		List<VillagerProfession> professions = VillagerRegistry.instance().getRegistry().getValues();
		VillagerProfession profession = professions.get(rand.nextInt(professions.size()));
		//zombie.setVillagerType(profession);
	}

}
