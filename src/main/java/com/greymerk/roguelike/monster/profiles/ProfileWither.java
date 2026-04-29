package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MobType;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;

public class ProfileWither implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
		mob.setMobClass(MobType.WITHERSKELETON, false);
		mob.setSlot(EquipmentSlot.MAINHAND, ItemWeapon.getSword(world.registryAccess(), world.enabledFeatures(), rand, diff, mob.canEnchant(rand, diff)));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, diff, mob);
	}

}
