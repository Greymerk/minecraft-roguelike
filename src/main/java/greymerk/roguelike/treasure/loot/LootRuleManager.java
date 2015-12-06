package greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.treasure.TreasureManager;

public class LootRuleManager {

	private List<LootRule> rules;
	
	public LootRuleManager(){
		this.rules = new ArrayList<LootRule>();
	}
	
	public void add(LootRule toAdd){
		this.rules.add(toAdd);
	}
	
	public void process(Random rand, ILoot loot, TreasureManager treasure){
		for(LootRule rule : this.rules){
			rule.process(rand, loot, treasure);
		}
	}
}
