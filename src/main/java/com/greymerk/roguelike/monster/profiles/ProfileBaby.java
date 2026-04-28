package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ProfileBaby implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
		mob.setChild(true);
		
		if(rand.nextInt(4) == 0){
			MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, diff, mob);
		}
		
		mob.setSlot(EquipmentSlot.MAINHAND, toy(world.registryAccess(), rand));
	}
	
	private ItemStack toy(RegistryAccess reg, RandomSource rand) {
		
		if(rand.nextInt(100) == 0) return ItemNovelty.getItem(reg, ItemNovelty.VECHS);
		
		WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
		
		randomizer.add(new WeightedChoice<ItemStack>(new ItemStack(Items.BONE), 1));
		randomizer.add(new WeightedChoice<ItemStack>(new ItemStack(Items.STICK), 1));
		randomizer.add(new WeightedChoice<ItemStack>(new ItemStack(Items.ARROW), 1));
		randomizer.add(new WeightedChoice<ItemStack>(new ItemStack(Items.FEATHER), 1));
		randomizer.add(new WeightedChoice<ItemStack>(new ItemStack(Items.OAK_SIGN), 1));
		randomizer.add(new WeightedChoice<ItemStack>(new ItemStack(Items.CARROT), 1));
		randomizer.add(new WeightedChoice<ItemStack>(new ItemStack(Items.COD), 1));
		randomizer.add(new WeightedChoice<ItemStack>(new ItemStack(Items.SOUL_TORCH), 1));
		
		return randomizer.get(rand);
	}

}
