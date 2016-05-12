package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.TippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemJunk extends ItemBase{

	public ItemJunk(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level){

		if(level > 0 && rand.nextInt(200) == 0){
			if(level > 2 && rand.nextInt(10) == 0) return new ItemStack(Items.diamond_horse_armor, 1, 0);
			if(level > 1 && rand.nextInt(5) == 0) return new ItemStack(Items.golden_horse_armor, 1, 0);
			if(rand.nextInt(3) == 0) return new ItemStack(Items.iron_horse_armor, 1, 0);
			return new ItemStack(Items.saddle);
		}

		if(level > 1 && rand.nextInt(100) == 0) return new ItemStack(Items.ghast_tear);

		if(rand.nextInt(70) == 0) return Shield.get(rand);
		
		if(level > 1 && rand.nextInt(40) == 0){
			return TippedArrow.get(rand, 4 + rand.nextInt(level) * 2);
		}
		
		if(level > 1 && rand.nextInt(40) == 0){			
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

		if(rand.nextInt(60) == 0){
			if(level > 2 && rand.nextBoolean()) return PotionMixture.getPotion(rand, PotionMixture.VILE);
			return PotionMixture.getPotion(rand, PotionMixture.LAUDANUM);	
		}
		
		if(rand.nextInt(15) == 0){
			return new ItemStack(Blocks.torch, 2 + rand.nextInt(5));
		}

		if(level > 0 && rand.nextInt(10) == 0){
			switch(rand.nextInt(7)){
			case 0: return new ItemStack(Items.slime_ball);
			case 1: return new ItemStack(Items.snowball);
			case 2: return new ItemStack(Items.mushroom_stew);
			case 3: return new ItemStack(Items.clay_ball);
			case 4: return new ItemStack(Items.flint);
			case 5: return new ItemStack(Items.feather);
			case 6: return new ItemStack(Items.glass_bottle);
			}
		}

		switch(rand.nextInt(6)){
		case 0: return new ItemStack(Items.bone);
		case 1: return new ItemStack(Items.rotten_flesh);
		case 2: return new ItemStack(Items.spider_eye);
		case 3: return new ItemStack(Items.paper);
		case 4: return new ItemStack(Items.string);
		case 5: return new ItemStack(Items.stick);
		default: return new ItemStack(Items.stick);
		}
	}
}
