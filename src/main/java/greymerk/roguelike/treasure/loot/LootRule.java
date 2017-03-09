package greymerk.roguelike.treasure.loot;

import java.util.Random;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;
import net.minecraft.item.ItemStack;

public class LootRule {

	private Treasure type;
	private IWeighted<ItemStack> item;
	int level;
	private boolean toEach;
	int amount;
	
	public LootRule(Treasure type, IWeighted<ItemStack> item, int level, boolean toEach, int amount){
		this.type = type;
		this.item = item;
		this.level = level;
		this.toEach = toEach;
		this.amount = amount;
	}
	
	
	
	public void process(Random rand, TreasureManager treasure){
		if(toEach && type != null) treasure.addItemToAll(rand, type, level, item, amount);		
		if(toEach && type == null) treasure.addItemToAll(rand, level, item, amount);
		if(!toEach && type != null) treasure.addItem(rand, type, level, item, amount);
		if(!toEach && type == null) treasure.addItem(rand, level, item, amount);
	}
	
	@Override
	public String toString(){
		
		String type = this.type != null ? this.type.toString() : "NONE";
		int level = this.level;
		int amount = this.amount;
		
		return "type: " + type + " level: " + level + " amount: " + amount;
	}
}
