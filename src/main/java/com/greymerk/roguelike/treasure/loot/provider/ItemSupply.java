package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.blocks.FlowerPot;
import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class ItemSupply extends ItemBase{

	private WeightedRandomizer<ItemStack> loot;
	
	public ItemSupply(int weight, Difficulty diff) {
		super(weight, diff);
		this.loot = new WeightedRandomizer<ItemStack>();
		
		if(diff.gt(Difficulty.MEDIUM)) {
			loot.add(new WeightedRandomLoot(Items.COD, 2, 32, 1));
			loot.add(new WeightedRandomLoot(Items.SALMON, 2, 32, 1));
			loot.add(new WeightedRandomLoot(Items.CHICKEN, 4, 32, 1));
			loot.add(new WeightedRandomLoot(Items.PORKCHOP, 4, 32, 1));
			loot.add(new WeightedRandomLoot(Items.MUTTON, 4, 32, 1));
			loot.add(new WeightedRandomLoot(Items.RABBIT, 4, 32, 1));
		}
		
		if(diff.lt(Difficulty.MEDIUM)) {
			loot.add(new WeightedRandomLoot(Items.WHEAT_SEEDS, 1, 8, 1));
			loot.add(new WeightedRandomLoot(Items.PUMPKIN_SEEDS, 1, 4, 1));
			loot.add(new WeightedRandomLoot(Items.MELON_SEEDS, 1, 4, 1));
			loot.add(new WeightedRandomLoot(Items.BEETROOT_SEEDS, 1, 4, 1));
			loot.add(new WeightedRandomLoot(Items.WHEAT, 4, 32, 1));
			loot.add(new WeightedRandomLoot(Items.POTATO, 2, 16, 1));
			loot.add(new WeightedRandomLoot(Items.CARROT, 2, 16, 1));
		}
		
		loot.add(new WeightedRandomLoot(Items.TORCH, 8, 48, 5));
		loot.add(new WeightedRandomLoot(Items.RABBIT_HIDE, 1, 8, 1));
		loot.add(new WeightedRandomLoot(Items.RABBIT_FOOT, 1, 4, 1));
		loot.add(new WeightedRandomLoot(Items.SWEET_BERRIES, 1, 4, 1));
		loot.add(new WeightedRandomLoot(Items.PAPER, 4, 32, 1));
		loot.add(new WeightedRandomLoot(Items.TURTLE_SCUTE, 1, 1, 1));
		loot.add(new WeightedRandomLoot(Items.STRING, 8, 32, 1));
		loot.add(new WeightedRandomLoot(Items.STICK, 8, 64, 1));
		loot.add(new WeightedRandomLoot(Items.LEATHER, 4, 24, 1));
		loot.add(new WeightedRandomLoot(Items.CLAY_BALL, 8, 64, 1));
		loot.add(new WeightedRandomLoot(Items.QUARTZ, 2, 16, 1));
		loot.add(new WeightedRandomLoot(Items.INK_SAC, 2, 12, 1));
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {
		if(rand.nextInt(100) == 0) return FlowerPot.getFlowerItem(RandHelper.pickFrom(FlowerPot.saplings, rand));
		return loot.get(rand);
	}
}
