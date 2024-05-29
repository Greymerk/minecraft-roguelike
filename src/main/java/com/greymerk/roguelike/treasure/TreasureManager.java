package com.greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;
import com.greymerk.roguelike.util.IWeighted;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class TreasureManager {

	List<ITreasureChest> chests;
	
	public TreasureManager(){
		this.chests = new ArrayList<ITreasureChest>();
	}
	
	public void add(ITreasureChest toAdd){
		this.chests.add(toAdd);
	}
	
	public void addItemToAll(Random rand, Treasure type, Difficulty diff, IWeighted<ItemStack> item, int amount){
		addItemToAll(rand, this.getChests(type, diff), item, amount);
	}
	
	public void addItemToAll(Random rand, Difficulty diff, IWeighted<ItemStack> item, int amount){
		addItemToAll(rand, this.getChests(diff), item, amount);
	}
	
	public void addItemToAll(Random rand, Treasure type, IWeighted<ItemStack> item, int amount){		
		addItemToAll(rand, this.getChests(type), item, amount);
	}
	
	private void addItemToAll(Random rand, List<ITreasureChest> chests, IWeighted<ItemStack> item, int amount){
		for(ITreasureChest chest : chests){
			for(int i = 0; i < amount; ++i){
				chest.setRandomEmptySlot(item.get(rand));
			}
		}
	}
	
	
	public void addItem(Random rand, Difficulty diff, IWeighted<ItemStack> item, int amount){
		this.addItem(rand, getChests(diff), item, amount);
	}
	
	public void addItem(Random rand, Treasure type, IWeighted<ItemStack> item, int amount){
		this.addItem(rand, getChests(type), item, amount);
	}
	
	public void addItem(Random rand, Treasure type, Difficulty diff, IWeighted<ItemStack> item, int amount){
		this.addItem(rand, getChests(type, diff), item, amount);
	}
	
	private void addItem(Random rand, List<ITreasureChest> chests, IWeighted<ItemStack> item, int amount){
		if(chests.isEmpty()) return;
		
		for(int i = 0; i < amount; ++i){
			ITreasureChest chest = chests.get(rand.nextInt(chests.size()));
			chest.setRandomEmptySlot(item.get(rand));
		}
	}
	
	public List<ITreasureChest> getChests(Treasure type, Difficulty diff){
		ArrayList<ITreasureChest> c = new ArrayList<ITreasureChest>();
		for(ITreasureChest chest : this.chests){
			if(chest.getType() == type && chest.getLevel() == diff) c.add(chest);
		}
		return c;
	}
	
	public List<ITreasureChest> getChests(Treasure type){
		ArrayList<ITreasureChest> c = new ArrayList<ITreasureChest>();
		for(ITreasureChest chest : this.chests){
			if(chest.getType() == type) c.add(chest);
		}
		return c;
	}
	
	public List<ITreasureChest> getChests(Difficulty diff){
		ArrayList<ITreasureChest> c = new ArrayList<ITreasureChest>();
		for(ITreasureChest chest : this.chests){
			if(chest.getType() == Treasure.EMPTY) continue;
			if(chest.getLevel() == diff) c.add(chest);
		}
		return c;
	}
	
	public List<ITreasureChest> getChests(){
		ArrayList<ITreasureChest> c = new ArrayList<ITreasureChest>();
		for(ITreasureChest chest : this.chests){
			if(chest.getType() != Treasure.EMPTY) c.add(chest);
		}
		return c;
	}
}
