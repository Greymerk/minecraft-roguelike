package greymerk.roguelike.treasure.loot;

import java.util.Arrays;

public enum Equipment {
	
	SWORD, BOW, HELMET, CHEST, LEGS, FEET, PICK, AXE, SHOVEL;

	public static Equipment[] armour = new Equipment[]{HELMET, CHEST, LEGS, FEET};
	
	public static String getName(Equipment type, Quality quality){
		
		String qualityName;
		String itemName;
		
		switch(type){
		case SWORD: itemName = "sword"; break;
		case BOW: return "minecraft:bow";
		case HELMET:itemName = "helmet"; break;
		case CHEST:itemName = "chestplate"; break;
		case LEGS: itemName = "leggings"; break;
		case FEET: itemName = "boots"; break;
		case PICK: itemName = "pickaxe"; break;
		case AXE: itemName = "axe"; break;
		case SHOVEL: itemName = "shovel"; break;
		default: return "minecraft:stick";
		}
		
		if(Arrays.asList(Equipment.armour).contains(type)){
			switch(quality){
			case WOOD: qualityName = "leather"; break;
			case STONE: qualityName = "chainmail"; break;
			case IRON: qualityName = "iron"; break;
			case GOLD: qualityName = "golden"; break;
			case DIAMOND: qualityName = "diamond"; break;
			default: return "minecraft:stick";
			}
		} else {
			switch(quality){
			case WOOD: qualityName = "wooden"; break;
			case STONE: qualityName = "stone"; break;
			case IRON: qualityName = "iron"; break;
			case GOLD: qualityName = "golden"; break;
			case DIAMOND: qualityName = "diamond"; break;
			default: return "minecraft:stick";
			}
		}
		

		
		return "minecraft:" + qualityName + "_" + itemName;
	}
}
