package greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;
import net.minecraft.item.ItemStack;

public class LootRuleManager {

	private List<LootRule> rules;
	
	public LootRuleManager(){
		this.rules = new ArrayList<LootRule>();
	}
	
	public LootRuleManager(JsonElement e) throws Exception {
		this.rules = new ArrayList<LootRule>();
		JsonArray arr = e.getAsJsonArray();
		for(JsonElement ruleElement : arr){
			
			JsonObject rule = ruleElement.getAsJsonObject();
			
			Treasure type = rule.has("type") ? Treasure.valueOf(rule.get("type").getAsString()) : null;
			
			if(!rule.has("loot")) continue;
			JsonArray data = rule.get("loot").getAsJsonArray();
			WeightedRandomizer<ItemStack> items = new WeightedRandomizer<ItemStack>(1);		
			for(JsonElement item : data){
				items.add(parseProvider(item.getAsJsonObject()));
			}

			List<Integer> levels = new ArrayList<Integer>();
			JsonElement levelElement = rule.get("level");
			if(levelElement.isJsonArray()){
				JsonArray levelArray = levelElement.getAsJsonArray();
				for(JsonElement lvl : levelArray){
					levels.add(lvl.getAsInt());
				}
			} else {
				levels.add(rule.get("level").getAsInt());
			}			
			
			boolean each = rule.get("each").getAsBoolean();
			int amount = rule.get("quantity").getAsInt();
			
			for(int level : levels){
				this.add(type, items, level, each, amount);	
			}
		}
	}

	public void add(Treasure type, IWeighted<ItemStack> item, int level, boolean toEach, int amount){
		this.rules.add(new LootRule(type, item, level, toEach, amount));
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
	
	private IWeighted<ItemStack> parseProvider(JsonObject lootItem) throws Exception {
		
		int weight = lootItem.has("weight") ? lootItem.get("weight").getAsInt() : 1;
		
		
		if(lootItem.get("data").isJsonObject()){
			JsonObject data = lootItem.get("data").getAsJsonObject();
			WeightedRandomLoot item = null;
			item = new WeightedRandomLoot(data, weight);
			return item;
		}

		JsonArray data = lootItem.get("data").getAsJsonArray();
		WeightedRandomizer<ItemStack> items = new WeightedRandomizer<ItemStack>(weight);		
		for(JsonElement e : data){
			items.add(parseProvider(e.getAsJsonObject()));
		}
		
		return items;
	}
	
	@Override
	public String toString(){
		return Integer.toString(this.rules.size());
	}
}
