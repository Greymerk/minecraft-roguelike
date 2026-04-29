package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MobType;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;

public class ProfileJohnny implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
		mob.setMobClass(MobType.VINDICATOR, false);
		mob.setSlot(EquipmentSlot.MAINHAND, ItemSpecialty.getRandomItem(world.registryAccess(), Equipment.AXE, rand, Difficulty.HARDEST));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, Difficulty.HARD, mob);
		mob.setName("Johnny");
	}

}
