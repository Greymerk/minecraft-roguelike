package greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.treasure.loot.ILoot;
import net.minecraft.item.ItemStack;

public class TreasureManager {

	List<ITreasureChest> chests;
	
	public TreasureManager(){
		this.chests = new ArrayList<ITreasureChest>();
	}
	
	public void add(ITreasureChest toAdd){
		this.chests.add(toAdd);
	}
	
	public void addAll(List<ITreasureChest> chests) {
		this.chests.addAll(chests);
	}
	
	public void fillChests(Random rand, ILoot loot){
		for(ITreasureChest chest : this.chests){
			Treasure.fillChest(chest, rand, loot);
		}
	}

	public void addItemToAll(Treasure type, ItemStack item){
		for(ITreasureChest chest : this.getChests(type)){
			chest.setRandomEmptySlot(item);
		}
	}
	
	public void addItemToAll(int level, ItemStack item){
		for(ITreasureChest chest : this.getChests(level)){
			chest.setRandomEmptySlot(item);
		}
	}
	
	public void addItem(Random rand, Treasure type, ItemStack item){
		addItem(rand, type, item, 1);
	}
	
	public void addItem(Random rand, int level, ItemStack item){
		addItem(rand, level, item, 1);
	}
	
	public void addItem(Random rand, Treasure type, ItemStack item, int amount){
		List<ITreasureChest> c = this.getChests(type);
		
		if(c.isEmpty()) return;
		
		for(int i = 0; i < amount; ++i){
			c.get(rand.nextInt(c.size())).setRandomEmptySlot(item);
		}
	}
	
	public void addItem(Random rand, int level, ItemStack item, int amount){
		List<ITreasureChest> c = this.getChests(level);
		
		if(c.isEmpty()) return;
		
		for(int i = 0; i < amount; ++i){
			c.get(rand.nextInt(c.size())).setRandomEmptySlot(item);
		}
	}
	
	public List<ITreasureChest> getChests(Treasure type){
		ArrayList<ITreasureChest> c = new ArrayList<ITreasureChest>();
		for(ITreasureChest chest : this.chests){
			if(chest.getType() == type) c.add(chest);
		}
		return c;
	}
	
	public List<ITreasureChest> getChests(int level){
		ArrayList<ITreasureChest> c = new ArrayList<ITreasureChest>();
		for(ITreasureChest chest : this.chests){
			if(chest.getLevel() == level) c.add(chest);
		}
		return c;
	}
	
	public List<ITreasureChest> getChests(){
		return this.chests;
	}
}
