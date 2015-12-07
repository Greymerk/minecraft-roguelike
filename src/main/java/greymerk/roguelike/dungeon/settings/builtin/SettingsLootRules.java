package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ILoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;

public class SettingsLootRules extends DungeonSettings{
	
	public SettingsLootRules(){
		this.lootRules = new LootRuleManager();
		ILoot loot = Loot.getLoot();
		lootRules.add(Treasure.STARTER, loot.get(Loot.WEAPON, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.FOOD, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, loot.get(Loot.WEAPON, 0),  0, true, 2);
		lootRules.add(Treasure.STARTER, new ItemSpecialty(0, 0, Equipment.LEGS, Quality.WOOD), 0, true, 2);
		for(int i = 0; i < 5; ++i){
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.POTION, i),  i, true, 1);
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.ARMOUR, i),  i, true, 1);
			lootRules.add(Treasure.ARMOUR, loot.get(Loot.FOOD, i),  i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.POTION, i),  i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.WEAPON, i),  i, true, 1);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.FOOD, i),  i, true, 1);
			lootRules.add(Treasure.BLOCKS, loot.get(Loot.BLOCK, i),  i, true, 6);
			lootRules.add(Treasure.WEAPONS, loot.get(Loot.FOOD, i),  i, true, 1);
			lootRules.add(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBONUS, i),  i, true, 2);
			lootRules.add(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBOOK, i),  i, true, 1);
			lootRules.add(Treasure.FOOD, loot.get(Loot.FOOD, i),  i, true, 8);
			lootRules.add(Treasure.ORE, loot.get(Loot.ORE, i),  i, true, 3);
			lootRules.add(Treasure.POTIONS, loot.get(Loot.POTION, i),  i, true, 3);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.ORE, i),  i, true, 1);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.TOOL, i),  i, true, 1);
			lootRules.add(Treasure.TOOLS, loot.get(Loot.BLOCK, i),  i, true, 1);
			lootRules.add(Treasure.SUPPLIES, loot.get(Loot.SUPPLY, i),  i, true, 6);
			lootRules.add(Treasure.SMITH, loot.get(Loot.ORE, i),  i, true, 2);
			lootRules.add(Treasure.SMITH, loot.get(Loot.SMITHY, i),  i, true, 1);
			lootRules.add(Treasure.MUSIC, loot.get(Loot.MUSIC, i),  i, true, 1);
			lootRules.add(Treasure.REWARD, loot.get(Loot.REWARD, i),  i, true, 2);
			lootRules.add(null, loot.get(Loot.JUNK, i),  i, true, 6);
			lootRules.add(null, loot.get(Loot.SPECIAL, i),  i, false, 3);
		}
	}
}
