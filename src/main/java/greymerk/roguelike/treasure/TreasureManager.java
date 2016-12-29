package greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.util.IWeighted;
import net.minecraft.item.ItemStack;

public class TreasureManager {

	List<ITreasureChest> chests;
	
	public TreasureManager(){
		this.chests = new ArrayList<ITreasureChest>();
	}
	
	public void add(ITreasureChest toAdd){
		this.chests.add(toAdd);
	}
	
	public void addItemToAll(Random rand, Treasure type, int level, IWeighted<ItemStack> item, int amount){
		for(ITreasureChest chest : this.getChests(type, level)){
			for(int i = 0; i < amount; ++i){
				chest.setRandomEmptySlot(item.get(rand));
			}
		}
	}
	
	public void addItemToAll(Random rand, int level, IWeighted<ItemStack> item, int amount){
		for(ITreasureChest chest : this.getChests(level)){
			for(int i = 0; i < amount; ++i){
				chest.setRandomEmptySlot(item.get(rand));
			}
		}
	}
	
	public void addItemToAll(Random rand, Treasure type, IWeighted<ItemStack> item, int amount){
		for(ITreasureChest chest : this.getChests(type)){
			for(int i = 0; i < amount; ++i){
				chest.setRandomEmptySlot(item.get(rand));
			}
		}
	}
	
	public void addItem(Random rand, int level, IWeighted<ItemStack> item, int amount){
		List<ITreasureChest> chests = getChests(level);
		if(chests.isEmpty()) return;
		
		for(int i = 0; i < amount; ++i){
			ITreasureChest chest = chests.get(rand.nextInt(chests.size()));
			chest.setRandomEmptySlot(item.get(rand));
		}
	}
	
	public void addItem(Random rand, Treasure type, IWeighted<ItemStack> item, int amount){
		List<ITreasureChest> chests = getChests(type);
		if(chests.isEmpty()) return;
		
		for(int i = 0; i < amount; ++i){
			ITreasureChest chest = chests.get(rand.nextInt(chests.size()));
			chest.setRandomEmptySlot(item.get(rand));
		}
	}
	
	public void addItem(Random rand, Treasure type, int level, IWeighted<ItemStack> item, int amount){
		List<ITreasureChest> chests = getChests(type, level);
		if(chests.isEmpty()) return;
		
		for(int i = 0; i < amount; ++i){
			ITreasureChest chest = chests.get(rand.nextInt(chests.size()));
			chest.setRandomEmptySlot(item.get(rand));
		}
	}
	
	public List<ITreasureChest> getChests(Treasure type, int level){
		ArrayList<ITreasureChest> c = new ArrayList<ITreasureChest>();
		for(ITreasureChest chest : this.chests){
			if(chest.getType() == type && chest.getLevel() == level) c.add(chest);
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
	
	public List<ITreasureChest> getChests(int level){
		ArrayList<ITreasureChest> c = new ArrayList<ITreasureChest>();
		for(ITreasureChest chest : this.chests){
			if(chest.getType() == Treasure.EMPTY) continue;
			if(chest.getLevel() == level) c.add(chest);
		}
		return c;
	}
	
	public List<ITreasureChest> getChests(){
		return this.chests;
	}
}
