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
	
	public LootRuleManager(JsonElement e) {
		this.rules = new ArrayList<LootRule>();
		JsonArray arr = e.getAsJsonArray();
		for(JsonElement ruleElement : arr){
			
			JsonObject rule = ruleElement.getAsJsonObject();
			
			Treasure type = rule.has("type") ? Treasure.valueOf(rule.get("type").getAsString()) : null;
			
			if(!rule.has("item")) continue;
			JsonObject loot = rule.get("item").getAsJsonObject();
			IWeighted<ItemStack> item = this.parseProvider(loot);

			int level = rule.get("level").getAsInt();
			boolean each = rule.get("each").getAsBoolean();
			int amount = rule.get("quantity").getAsInt();
			
			this.add(type, item, level, each, amount);
		}
	}

	public void add(Treasure type, IWeighted<ItemStack> item, int level, boolean toEach, int amount){
		this.rules.add(new LootRule(type, item, level, toEach, amount));
	}
	
	public void add(LootRuleManager other){
		this.rules.addAll(other.rules);
	}
	
	public void process(Random rand, ILoot loot, TreasureManager treasure){
		for(LootRule rule : this.rules){
			rule.process(rand, loot, treasure);
		}
	}
	
	private IWeighted<ItemStack> parseProvider(JsonObject data) {
		
		int weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
		JsonElement loot = data.get("data");
		
		if(loot.isJsonObject()){
			return new WeightedRandomLoot(loot.getAsJsonObject(), weight);
		}
		
		JsonArray lootList = loot.getAsJsonArray();
		
		WeightedRandomizer<ItemStack> items = new WeightedRandomizer<ItemStack>(weight);
		
		for(JsonElement e : lootList){
			items.add(parseProvider(e.getAsJsonObject()));
		}
		
		return items;
	}
}
