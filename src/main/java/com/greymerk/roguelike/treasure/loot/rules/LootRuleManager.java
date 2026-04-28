package com.greymerk.roguelike.treasure.loot.rules;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.TreasureManager;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;
import com.greymerk.roguelike.util.IWeighted;
import com.greymerk.roguelike.util.WeightedChoice;

public class LootRuleManager {

	private List<LootRule> rules;
	
	public LootRuleManager(){
		this.rules = new ArrayList<LootRule>();
	}

	public void add(Treasure type, IWeighted<ItemStack> item, Difficulty diff, int amount){
		this.rules.add(new LootRule(type, item, diff, amount));
	}
	
	public void add(Treasure type, ItemStack item, Difficulty diff, int amount){
		IWeighted<ItemStack> toAdd = new WeightedChoice<ItemStack>(item, 1);
		this.rules.add(new LootRule(type, toAdd, diff, amount));
	}
	
	public void add(LootRule toAdd){
		this.rules.add(toAdd);
	}
	
	public void add(LootRuleManager other){
		if(other == null) return;
		this.rules.addAll(other.rules);
	}
	
	public void process(RandomSource rand, TreasureManager treasure){
		for(LootRule rule : this.rules){
			rule.process(rand, treasure);
		}
	}
	
	public void process(RandomSource rand, ITreasureChest chest) {
		for(LootRule rule : this.rules) {
			rule.process(rand, chest);
		}
	}
	
	@Override
	public String toString(){
		return Integer.toString(this.rules.size());
	}
}
