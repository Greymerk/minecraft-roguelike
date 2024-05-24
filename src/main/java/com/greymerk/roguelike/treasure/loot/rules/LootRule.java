package com.greymerk.roguelike.treasure.loot.rules;

import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.TreasureManager;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;
import com.greymerk.roguelike.util.IWeighted;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class LootRule {

	private Treasure type;
	private IWeighted<ItemStack> item;
	int level;
	int amount;
	
	public LootRule(Treasure type, IWeighted<ItemStack> item, int level, int amount){
		this.type = type;
		this.item = item;
		this.level = level;
		this.amount = amount;
	}
	
	public void process(Random rand, TreasureManager treasure){
		treasure.addItemToAll(rand, type, level, item, amount);	
	}

	public void process(Random rand, ITreasureChest chest) {
		if(chest.getLevel() != this.level) return;
		if(this.type == Treasure.ALL) addItems(rand, chest);
		if(this.type == chest.getType()) addItems(rand, chest);
	}
	
	public void addItems(Random rand, ITreasureChest chest) {
		for(int i = 0; i < amount; ++i) {
			chest.setRandomEmptySlot(item.get(rand));
		}
	}
	
	
	
	@Override
	public String toString(){
		
		String type = this.type.toString();
		int level = this.level;
		int amount = this.amount;
		
		return "type: " + type + " level: " + level + " amount: " + amount;
	}
}
