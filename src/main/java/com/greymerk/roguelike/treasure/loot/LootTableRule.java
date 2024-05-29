package com.greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.TreasureManager;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;

import net.minecraft.util.Identifier;

public class LootTableRule {

	private Identifier table;
	private List<Treasure> type;
	List<Difficulty> diff;
	
	public void process(TreasureManager treasure){
		List<ITreasureChest> chests = getMatching(treasure);
		for(ITreasureChest chest : chests){
			if(chest.getType() != Treasure.EMPTY) chest.setLootTable(table);
		}
	}
	
	private List<ITreasureChest> getMatching(TreasureManager treasure){
		if(this.type == null && this.diff == null) return treasure.getChests();
		
		List<ITreasureChest> chests = new ArrayList<ITreasureChest>();
		if(this.type == null){
			for(Difficulty d : this.diff){
				chests.addAll(treasure.getChests(d));
			}
		}
		
		if(this.diff == null){
			for(Treasure type : this.type){
				chests.addAll(treasure.getChests(type));
			}
		}
		
		return chests;
	}
}
