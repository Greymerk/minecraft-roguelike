package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.PotionMixture;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

public class ItemJunk extends ItemBase{

	public ItemJunk(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level){
		if(rand.nextInt(500) == 0){
			if(rand.nextBoolean()) return ItemNovelty.getItem(ItemNovelty.VECHS);

			switch(level){
			case 0: return ItemNovelty.getItem(ItemNovelty.GRIM);
			case 1: return ItemNovelty.getItem(ItemNovelty.FOURLES);
			case 2: return ItemNovelty.getItem(ItemNovelty.ZISTEAUSIGN);
			case 3: return ItemNovelty.getItem(ItemNovelty.PAULSOARESJR);
			case 4: return ItemNovelty.getItem(ItemNovelty.DINNERBONE);
			}
		}

		if(level > 0 && rand.nextInt(200) == 0){
			if(level > 2 && rand.nextInt(10) == 0) return new ItemStack(Items.diamond_horse_armor, 1, 0);
			if(level > 1 && rand.nextInt(5) == 0) return new ItemStack(Items.golden_horse_armor, 1, 0);
			if(rand.nextInt(3) == 0) return new ItemStack(Items.iron_horse_armor, 1, 0);
			return new ItemStack(Items.saddle);
		}

		if(level > 1 && rand.nextInt(100) == 0) return new ItemStack(Items.ghast_tear);

		if(level > 1 && rand.nextInt(50) == 0){			
			switch(rand.nextInt(6)){
			case 0: return new ItemStack(Items.gunpowder, 1 + rand.nextInt(3));
			case 1: return new ItemStack(Items.blaze_powder, 1 + rand.nextInt(3));
			case 2: return new ItemStack(Items.gold_nugget, 1 + rand.nextInt(3));
			case 3: return new ItemStack(Items.redstone, 1 + rand.nextInt(3));
			case 4: return new ItemStack(Items.glowstone_dust, 1 + rand.nextInt(3));
			case 5: return new ItemStack(Items.dye, 1 + rand.nextInt(3));
			}
		}

		if(level < 3 && rand.nextInt(10 + ((1 + level) * 5)) == 0) return new ItemStack(Items.book);

		if(level > 0 && rand.nextInt(20 / (1 + level)) == 0){
			
			if(level == 4 && rand.nextInt(10) == 0){
				ChestGenHooks hook = rand.nextBoolean() ?
						ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST):
						ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST);
				ItemStack toReturn = hook.getOneItem(rand);
				if(toReturn != null) return toReturn;
			}
			
			ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
			ItemStack toReturn = hook.getOneItem(rand);
			if(toReturn != null) return toReturn;
		}

		if(rand.nextInt(60) == 0){
			if(level > 2 && rand.nextBoolean()) return PotionMixture.getPotion(rand, PotionMixture.VILE);
			return PotionMixture.getPotion(rand, PotionMixture.LAUDANUM);	
		}
		
		if(rand.nextInt(20) == 0){
			return new ItemStack(Blocks.torch, 3 + rand.nextInt(3 + level));
		}

		if(level > 0 && rand.nextInt(10) == 0){
			switch(rand.nextInt(7)){
			case 0: return new ItemStack(Items.slime_ball, 1 + rand.nextInt(3));
			case 1: return new ItemStack(Items.snowball, 1 + rand.nextInt(3));
			case 2: return new ItemStack(Items.mushroom_stew);
			case 3: return new ItemStack(Items.clay_ball, 1 + rand.nextInt(3));
			case 4: return new ItemStack(Items.flint);
			case 5: return new ItemStack(Items.feather, 1 + rand.nextInt(3));
			case 6: return new ItemStack(Items.glass_bottle, 1 + rand.nextInt(3));
			}
		}

		switch(rand.nextInt(6)){
		case 0: return new ItemStack(Items.bone);
		case 1: return new ItemStack(Items.rotten_flesh);
		case 2: return new ItemStack(Items.spider_eye);
		case 3: return new ItemStack(Items.stick);
		case 4: return new ItemStack(Items.string);
		case 5: return new ItemStack(Items.stick);
		default: return new ItemStack(Items.stick);
		}
	}
}
