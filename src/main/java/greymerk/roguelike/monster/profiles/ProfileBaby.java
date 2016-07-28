package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProfileBaby implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, Entity mob) {
		EntityZombie zombie = (EntityZombie)mob;
		
		zombie.setChild(true);
		
		if(rand.nextBoolean()){
			MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, level, mob);
		}
		
		ItemStack weapon = ItemTool.getRandom(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
		mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, weapon);
	}

}
