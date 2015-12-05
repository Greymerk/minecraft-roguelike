package greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ILoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
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
	
	public void fillChests(Random rand, ILoot loot){

		this.addItemToAll(rand, Treasure.STARTER, 0, loot.get(Loot.WEAPON, 0), 2);
		this.addItemToAll(rand, Treasure.STARTER, 0, new ItemSpecialty(0, 0, Equipment.LEGS, Quality.WOOD), 2);
		this.addItemToAll(rand, Treasure.STARTER, 0, loot.get(Loot.TOOL, 0), 2);
		this.addItemToAll(rand, Treasure.STARTER, 0, loot.get(Loot.FOOD, 0), 4);
		
		for(int i = 0; i < 5; ++i){
			this.addItemToAll(rand, Treasure.ARMOUR, i, loot.get(Loot.POTION, i), 1);
			this.addItemToAll(rand, Treasure.ARMOUR, i, loot.get(Loot.ARMOUR, i), 1);
			this.addItemToAll(rand, Treasure.ARMOUR, i, loot.get(Loot.FOOD, i), 1);
			this.addItemToAll(rand, Treasure.WEAPONS, i, loot.get(Loot.POTION, i), 1);
			this.addItemToAll(rand, Treasure.WEAPONS, i, loot.get(Loot.WEAPON, i), 1);
			this.addItemToAll(rand, Treasure.WEAPONS, i, loot.get(Loot.FOOD, i), 1);
			this.addItemToAll(rand, Treasure.BLOCKS, i, loot.get(Loot.BLOCK, i), 1);
			this.addItemToAll(rand, Treasure.ENCHANTING, i, loot.get(Loot.ENCHANTBONUS, i), 2);
			this.addItemToAll(rand, Treasure.ENCHANTING, i, loot.get(Loot.ENCHANTBOOK, i), 1);
			this.addItemToAll(rand, Treasure.FOOD, i, loot.get(Loot.FOOD, i), 8);
			this.addItemToAll(rand, Treasure.ORE, i, loot.get(Loot.ORE, i), 5);
			this.addItemToAll(rand, Treasure.POTIONS, i, loot.get(Loot.POTION, i), 3);
			this.addItemToAll(rand, Treasure.TOOLS, i, loot.get(Loot.ORE, i), 1);
			this.addItemToAll(rand, Treasure.TOOLS, i, loot.get(Loot.TOOL, i), 1);
			this.addItemToAll(rand, Treasure.TOOLS, i, loot.get(Loot.BLOCK, i), 1);
			this.addItemToAll(rand, Treasure.SUPPLIES, i, loot.get(Loot.SUPPLY, i), 6);
			this.addItemToAll(rand, Treasure.SMITH, i, loot.get(Loot.SMITHY, i), 1);
			this.addItemToAll(rand, Treasure.SMITH, i, loot.get(Loot.ORE, i), 1);
			this.addItemToAll(rand, Treasure.MUSIC, i, loot.get(Loot.MUSIC, i), 1);
			this.addItemToAll(rand, Treasure.REWARD, i, loot.get(Loot.REWARD, i), 1);
			this.addItemToAll(rand, i, loot.get(Loot.JUNK, i), 6);
			this.addItem(rand, i, loot.get(Loot.SPECIAL, i), 3);
			
		}
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
