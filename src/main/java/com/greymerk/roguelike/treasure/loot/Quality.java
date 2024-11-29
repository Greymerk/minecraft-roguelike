package com.greymerk.roguelike.treasure.loot;

import java.util.HashMap;
import java.util.Map;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.util.IWeighted;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public enum Quality{
	
	WOOD, STONE, IRON, GOLD, DIAMOND, NETHERITE;
	
	private static Map<Difficulty, IWeighted<Quality>> armourQuality;
	private static Map<Difficulty, IWeighted<Quality>> weaponQuality;
	
	static {
		armourQuality = new HashMap<Difficulty, IWeighted<Quality>>();
		weaponQuality = new HashMap<Difficulty, IWeighted<Quality>>();
		
		armourQuality.put(Difficulty.EASIEST, 	create(300, 100, 20, 5, 2, 1));
		armourQuality.put(Difficulty.EASY, 		create(200, 80, 40, 5, 2, 1));
		armourQuality.put(Difficulty.MEDIUM, 	create(50, 80, 60, 5, 3, 1));
		armourQuality.put(Difficulty.HARD, 		create(5, 40, 80, 5, 5, 1));
		armourQuality.put(Difficulty.HARDEST, 	create(1, 10, 50, 5, 10, 1));
		
		weaponQuality.put(Difficulty.EASIEST, 	create(100, 300, 50, 2, 1, 1));
		weaponQuality.put(Difficulty.EASY, 		create(50, 200, 50, 5, 2, 1));
		weaponQuality.put(Difficulty.MEDIUM, 	create(10, 80, 80, 5, 5, 1));
		weaponQuality.put(Difficulty.HARD, 		create(5, 20, 70, 5, 5, 1));
		weaponQuality.put(Difficulty.HARDEST, 	create(1, 10, 50, 5, 10, 1));
		
	}

	private static WeightedRandomizer<Quality> create(int wood, int stone, int iron, int gold, int diamond, int netherite){
		WeightedRandomizer<Quality> randomizer = new WeightedRandomizer<Quality>();
		randomizer.add(new WeightedChoice<Quality>(WOOD, wood));
		randomizer.add(new WeightedChoice<Quality>(STONE, stone));
		randomizer.add(new WeightedChoice<Quality>(IRON, iron));
		randomizer.add(new WeightedChoice<Quality>(GOLD, gold));
		randomizer.add(new WeightedChoice<Quality>(DIAMOND, diamond));
		randomizer.add(new WeightedChoice<Quality>(NETHERITE, netherite));
		return randomizer;
	}
	
	public static Quality get(Random rand, Difficulty diff, Equipment type) {
		
		switch(type){
		case HELMET:
		case CHEST:
		case LEGS:
		case FEET:
			return armourQuality.get(diff).get(rand);
		case PICK:
		case AXE:
		case SHOVEL:
		case SWORD:
		case BOW:
			return weaponQuality.get(diff).get(rand);
		}
		return Quality.WOOD;
	}
	
	public static Quality get(Difficulty diff){
		switch(diff){
		case EASIEST: return Quality.WOOD;
		case EASY: return Quality.STONE;
		case MEDIUM: return Quality.IRON;
		case HARD: return Quality.GOLD;
		case HARDEST: return Quality.DIAMOND;
		default: return Quality.WOOD;
		}
	}

	public static Quality getArmourQuality(Random rand, Difficulty diff) {
		return armourQuality.get(diff).get(rand);
	}

	public static Quality getToolQuality(Random rand, Difficulty diff) {
		return weaponQuality.get(diff).get(rand);
	}

	public static Quality getWeaponQuality(Random rand, Difficulty diff) {
		return weaponQuality.get(diff).get(rand);
	}
	
	
}
