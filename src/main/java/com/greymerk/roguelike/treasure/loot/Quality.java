package com.greymerk.roguelike.treasure.loot;

import java.util.HashMap;
import java.util.List;
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
	private static Map<Difficulty, IWeighted<Quality>> toolQuality;
	static {
		armourQuality = new HashMap<Difficulty, IWeighted<Quality>>();
		weaponQuality = new HashMap<Difficulty, IWeighted<Quality>>();
		toolQuality = new HashMap<Difficulty, IWeighted<Quality>>();
		
		List.of(Difficulty.values()).forEach(diff -> {
			WeightedRandomizer<Quality> armour = new WeightedRandomizer<Quality>();
			switch(diff){
			case EASIEST:
				armour.add(new WeightedChoice<Quality>(WOOD, 100));
				armour.add(new WeightedChoice<Quality>(STONE, 30));
				armour.add(new WeightedChoice<Quality>(IRON, 20));
				armour.add(new WeightedChoice<Quality>(GOLD, 5));
				armour.add(new WeightedChoice<Quality>(DIAMOND, 3));
				armour.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case EASY:
				armour.add(new WeightedChoice<Quality>(WOOD, 100));
				armour.add(new WeightedChoice<Quality>(STONE, 50));
				armour.add(new WeightedChoice<Quality>(IRON, 30));
				armour.add(new WeightedChoice<Quality>(GOLD, 10));
				armour.add(new WeightedChoice<Quality>(DIAMOND, 3));
				armour.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case MEDIUM:
				armour.add(new WeightedChoice<Quality>(WOOD, 30));
				armour.add(new WeightedChoice<Quality>(STONE, 100));
				armour.add(new WeightedChoice<Quality>(IRON, 50));
				armour.add(new WeightedChoice<Quality>(GOLD, 20));
				armour.add(new WeightedChoice<Quality>(DIAMOND, 5));
				armour.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case HARD:
				armour.add(new WeightedChoice<Quality>(WOOD, 10));
				armour.add(new WeightedChoice<Quality>(STONE, 30));
				armour.add(new WeightedChoice<Quality>(IRON, 100));
				armour.add(new WeightedChoice<Quality>(GOLD, 5));
				armour.add(new WeightedChoice<Quality>(DIAMOND, 10));
				armour.add(new WeightedChoice<Quality>(NETHERITE, 3));
				break;
			case HARDEST:
				armour.add(new WeightedChoice<Quality>(WOOD, 1));
				armour.add(new WeightedChoice<Quality>(STONE, 5));
				armour.add(new WeightedChoice<Quality>(IRON, 20));
				armour.add(new WeightedChoice<Quality>(GOLD, 3));
				armour.add(new WeightedChoice<Quality>(DIAMOND, 50));
				armour.add(new WeightedChoice<Quality>(NETHERITE, 5));
				break;
			}
			armourQuality.put(diff, armour);
			
			WeightedRandomizer<Quality> weapon = new WeightedRandomizer<Quality>();
			switch(diff){
			case EASIEST:
				weapon.add(new WeightedChoice<Quality>(WOOD, 200));
				weapon.add(new WeightedChoice<Quality>(STONE, 50));
				weapon.add(new WeightedChoice<Quality>(IRON, 10));
				weapon.add(new WeightedChoice<Quality>(GOLD, 3));
				weapon.add(new WeightedChoice<Quality>(DIAMOND, 1));
				weapon.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case EASY:
				weapon.add(new WeightedChoice<Quality>(WOOD, 20));
				weapon.add(new WeightedChoice<Quality>(STONE, 30));
				weapon.add(new WeightedChoice<Quality>(IRON, 10));
				weapon.add(new WeightedChoice<Quality>(GOLD, 3));
				weapon.add(new WeightedChoice<Quality>(DIAMOND, 1));
				weapon.add(new WeightedChoice<Quality>(NETHERITE, 1));
				
				break;
			case MEDIUM:
				weapon.add(new WeightedChoice<Quality>(WOOD, 10));
				weapon.add(new WeightedChoice<Quality>(STONE, 20));
				weapon.add(new WeightedChoice<Quality>(IRON, 10));
				weapon.add(new WeightedChoice<Quality>(GOLD, 3));
				weapon.add(new WeightedChoice<Quality>(DIAMOND, 1));
				weapon.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case HARD:
				weapon.add(new WeightedChoice<Quality>(WOOD, 1));
				weapon.add(new WeightedChoice<Quality>(STONE, 3));
				weapon.add(new WeightedChoice<Quality>(IRON, 5));
				weapon.add(new WeightedChoice<Quality>(GOLD, 3));
				weapon.add(new WeightedChoice<Quality>(DIAMOND, 1));
				weapon.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case HARDEST:
				weapon.add(new WeightedChoice<Quality>(WOOD, 1));
				weapon.add(new WeightedChoice<Quality>(STONE, 2));
				weapon.add(new WeightedChoice<Quality>(IRON, 15));
				weapon.add(new WeightedChoice<Quality>(GOLD, 5));
				weapon.add(new WeightedChoice<Quality>(DIAMOND, 3));
				weapon.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			}
			weaponQuality.put(diff, weapon);
			
			WeightedRandomizer<Quality> tool = new WeightedRandomizer<Quality>();
			switch(diff){
			case EASIEST:
				tool.add(new WeightedChoice<Quality>(WOOD, 10));
				tool.add(new WeightedChoice<Quality>(STONE, 20));
				tool.add(new WeightedChoice<Quality>(IRON, 10));
				tool.add(new WeightedChoice<Quality>(GOLD, 3));
				tool.add(new WeightedChoice<Quality>(DIAMOND, 1));
				tool.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case EASY:
				tool.add(new WeightedChoice<Quality>(WOOD, 2));
				tool.add(new WeightedChoice<Quality>(STONE, 10));
				tool.add(new WeightedChoice<Quality>(IRON, 10));
				tool.add(new WeightedChoice<Quality>(GOLD, 3));
				tool.add(new WeightedChoice<Quality>(DIAMOND, 1));
				tool.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case MEDIUM:
				tool.add(new WeightedChoice<Quality>(WOOD, 1));
				tool.add(new WeightedChoice<Quality>(STONE, 5));
				tool.add(new WeightedChoice<Quality>(IRON, 10));
				tool.add(new WeightedChoice<Quality>(GOLD, 5));
				tool.add(new WeightedChoice<Quality>(DIAMOND, 3));
				tool.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case HARD:
				tool.add(new WeightedChoice<Quality>(WOOD, 1));
				tool.add(new WeightedChoice<Quality>(STONE, 3));
				tool.add(new WeightedChoice<Quality>(IRON, 10));
				tool.add(new WeightedChoice<Quality>(GOLD, 5));
				tool.add(new WeightedChoice<Quality>(DIAMOND, 5));
				tool.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			case HARDEST:
				tool.add(new WeightedChoice<Quality>(WOOD, 1));
				tool.add(new WeightedChoice<Quality>(STONE, 2));
				tool.add(new WeightedChoice<Quality>(IRON, 10));
				tool.add(new WeightedChoice<Quality>(GOLD, 3));
				tool.add(new WeightedChoice<Quality>(DIAMOND, 5));
				tool.add(new WeightedChoice<Quality>(NETHERITE, 1));
				break;
			}
			toolQuality.put(diff, tool);
		});
		
	}

	public static Quality get(Random rand, Difficulty diff, Equipment type) {
		
		switch(type){
		case SWORD:
		case BOW:
			return weaponQuality.get(diff).get(rand);
		case HELMET:
		case CHEST:
		case LEGS:
		case FEET:
			return armourQuality.get(diff).get(rand);
		case PICK:
		case AXE:
		case SHOVEL:
			return toolQuality.get(diff).get(rand);
		}
		return null;
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
		return toolQuality.get(diff).get(rand);
	}

	public static Quality getWeaponQuality(Random rand, Difficulty diff) {
		return weaponQuality.get(diff).get(rand);
	}
	
	
}
