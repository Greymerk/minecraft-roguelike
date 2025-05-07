package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.blocks.FlowerPot;
import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class ItemSupply extends ItemBase{

	private WeightedRandomizer<ItemStack> loot;
	
	public ItemSupply(int weight, Difficulty diff) {
		super(weight, diff);
		this.loot = new WeightedRandomizer<ItemStack>();
		
		if(diff.lt(Difficulty.MEDIUM)) {
			loot.add(new WeightedRandomLoot(Items.WHEAT_SEEDS, 1, 8, 1));
			loot.add(new WeightedRandomLoot(Items.PUMPKIN_SEEDS, 1));
			loot.add(new WeightedRandomLoot(Items.MELON_SEEDS, 1));
			loot.add(new WeightedRandomLoot(Items.BEETROOT_SEEDS, 1));
			loot.add(new WeightedRandomLoot(Items.WHEAT, 1, 32, 1));
			loot.add(new WeightedRandomLoot(Items.POTATO, 1, 32, 1));
			loot.add(new WeightedRandomLoot(Items.CARROT, 1, 32, 1));
		} else {
			loot.add(new WeightedRandomLoot(Items.COD, 1, 16, 1));
			loot.add(new WeightedRandomLoot(Items.SALMON, 1, 16, 1));
			loot.add(new WeightedRandomLoot(Items.CHICKEN, 1, 16, 1));
			loot.add(new WeightedRandomLoot(Items.PORKCHOP, 1, 16, 1));
			loot.add(new WeightedRandomLoot(Items.MUTTON, 1, 16, 1));
			loot.add(new WeightedRandomLoot(Items.RABBIT, 1, 16, 1));
		}
		
		loot.add(new WeightedRandomLoot(Items.TORCH, 1, 32, 5));
		loot.add(new WeightedRandomLoot(Items.RABBIT_HIDE, 1, 4, 1));
		loot.add(new WeightedRandomLoot(Items.RABBIT_FOOT, 1));
		loot.add(new WeightedRandomLoot(Items.SWEET_BERRIES, 1, 4, 1));
		loot.add(new WeightedRandomLoot(Items.PAPER, 1, 32, 1));
		loot.add(new WeightedRandomLoot(Items.SCUTE, 1));
		loot.add(new WeightedRandomLoot(Items.STRING, 1, 16, 1));
		loot.add(new WeightedRandomLoot(Items.STICK, 1, 16, 1));
		loot.add(new WeightedRandomLoot(Items.LEATHER, 1, 8, 1));
		loot.add(new WeightedRandomLoot(Items.CLAY_BALL, 1, 16, 1));
		loot.add(new WeightedRandomLoot(Items.QUARTZ, 1, 16, 1));
		loot.add(new WeightedRandomLoot(Items.INK_SAC, 1, 4, 1));
		loot.add(new WeightedRandomLoot(Items.GUNPOWDER, 1, 16, 1));
		
		
		WeightedRandomizer<ItemStack> saplings = new WeightedRandomizer<ItemStack>(1);
		FlowerPot.saplings.forEach(sapling -> {
			saplings.add(new WeightedRandomLoot(FlowerPot.getFlowerItem(sapling).getItem(), 1, 4, 1));
		});
		loot.add(saplings);
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {		
		return loot.get(rand);
	}
}
