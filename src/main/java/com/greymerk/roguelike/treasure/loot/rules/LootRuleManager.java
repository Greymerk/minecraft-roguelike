package com.greymerk.roguelike.treasure.loot.rules;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.treasure.ITreasureChest;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.TreasureManager;
import com.greymerk.roguelike.util.IWeighted;
import com.greymerk.roguelike.util.WeightedChoice;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class LootRuleManager {

	private List<LootRule> rules;
	
	public LootRuleManager(){
		this.rules = new ArrayList<LootRule>();
	}

	public void add(Treasure type, IWeighted<ItemStack> item, int level, int amount){
		this.rules.add(new LootRule(type, item, level, amount));
	}
	
	public void add(Treasure type, ItemStack item, int level, int amount){
		IWeighted<ItemStack> toAdd = new WeightedChoice<ItemStack>(item, 1);
		this.rules.add(new LootRule(type, toAdd, level, amount));
	}
	
	public void add(LootRule toAdd){
		this.rules.add(toAdd);
	}
	
	public void add(LootRuleManager other){
		if(other == null) return;
		this.rules.addAll(other.rules);
	}
	
	public void process(Random rand, TreasureManager treasure){
		for(LootRule rule : this.rules){
			rule.process(rand, treasure);
		}
	}
	
	public void process(Random rand, ITreasureChest chest) {
		for(LootRule rule : this.rules) {
			rule.process(rand, chest);
		}
	}
	
	@Override
	public String toString(){
		return Integer.toString(this.rules.size());
	}
}
