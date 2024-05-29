package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ProfileBaby implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, Difficulty diff, IEntity mob) {
		mob.setChild(true);
		
		if(rand.nextInt(4) == 0){
			MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, diff, mob);
		}
		
		mob.setSlot(EquipmentSlot.MAINHAND, toy(rand));
	}
	
	private ItemStack toy(Random rand) {
		
		if(rand.nextInt(100) == 0) return ItemNovelty.getItem(ItemNovelty.VECHS);
		
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
