package com.greymerk.roguelike.treasure.loot.rules;

import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.ILoot;
import com.greymerk.roguelike.treasure.loot.Loot;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.treasure.loot.provider.ItemSpecialty;

public class RoguelikeLootRules {

	public static LootRuleManager getLoot() {
		
		LootRuleManager lootRules = new LootRuleManager();
		ILoot loot = Loot.getLoot();
		//lootRules.add(Treasure.STARTER, Book.get(Book.CREDITS), 0, true, 1);
		lootRules.add(Treasure.STARTER, loot.get(Loot.WEAPON, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.FOOD, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.TOOL, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.SUPPLY, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, new ItemSpecialty(0, 0, Equipment.LEGS, Quality.WOOD), 0, true, 2);
		for(int difficulty = 0; difficulty < 5; ++difficulty){
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.POTION, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.ARMOUR, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.FOOD, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.POTION, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.WEAPON, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.FOOD, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.BLOCKS, loot.get(Loot.BLOCK, difficulty),  difficulty, true, 6);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.FOOD, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBONUS, difficulty),  difficulty, true, 2);
			lootRules.add(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBOOK, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.FOOD, loot.get(Loot.FOOD, difficulty),  difficulty, true, 5);
			lootRules.add(Treasure.ORE, loot.get(Loot.ORE, difficulty),  difficulty, true, 5);
			lootRules.add(Treasure.POTIONS, loot.get(Loot.POTION, difficulty),  difficulty, true, 6);
			lootRules.add(Treasure.BREWING, loot.get(Loot.BREWING, difficulty),  difficulty, true, 8);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.ORE, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.TOOL, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.BLOCK, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.SUPPLIES, loot.get(Loot.SUPPLY, difficulty),  difficulty, true, 4);
			lootRules.add(Treasure.SUPPLIES, loot.get(Loot.BLOCK, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.SUPPLIES, loot.get(Loot.FOOD, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.SUPPLIES, loot.get(Loot.ORE, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.SMITH, loot.get(Loot.ORE, difficulty),  difficulty, true, 6);
			lootRules.add(Treasure.SMITH, loot.get(Loot.SMITHY, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.MUSIC, loot.get(Loot.MUSIC, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.REWARD, loot.get(Loot.REWARD, difficulty),  difficulty, true, 1);
			lootRules.add(Treasure.ALL, loot.get(Loot.JUNK, difficulty),  difficulty, true, 6);
		}
		
		return lootRules;
	}
	
}
