package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProfileZombie implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, Entity mob) {
		EntityZombie zombie = (EntityZombie)mob;
		
		if(rand.nextInt(100) == 0){
			MonsterProfile.get(MonsterProfile.RLEAHY).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(rand.nextInt(100) == 0){
			MonsterProfile.get(MonsterProfile.ASHLEA).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(rand.nextInt(40) == 0){
			MonsterProfile.get(MonsterProfile.BABY).addEquipment(world, rand, level, mob);
			return;
		}
		
		if(rand.nextInt(10) == 0){
			MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, level, mob);
		} else if(level > 1 && rand.nextInt(10) == 0){
			zombie.func_189778_a(ZombieType.HUSK);
		}

		
		ItemStack weapon = ItemTool.getRandom(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
		mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, weapon);
		mob.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, Shield.get(rand));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, level, mob);
		
	}

}
