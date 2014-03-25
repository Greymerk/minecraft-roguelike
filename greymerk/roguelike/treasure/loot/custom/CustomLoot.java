package greymerk.roguelike.treasure.loot.custom;


import greymerk.roguelike.config.RogueConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;



public class CustomLoot{
	
	private List<WeightedRandomLoot> lootList;
		
	public CustomLoot(){
		lootList = new ArrayList<WeightedRandomLoot>();
	}
	
	public void addLoot(WeightedRandomLoot loot){
		lootList.add(loot);
		Collections.sort(lootList);
	}
	
	public ItemStack getLoot(Random rand, int scale) {
		
		for(WeightedRandomLoot loot : lootList){
			if(loot.roll(rand, scale)){
				return loot.getItem(rand);
			}
		}
		
		return new ItemStack(Item.stick);
	}
	
	public void parseLoot(){
		
		// make sure file exists
		File configDir = new File(RogueConfig.configDirName);
		if(!configDir.exists()) return;

		File lootfile = new File(RogueConfig.configDirName + "/" + "roguelike-loot.cfg");

		String content;
		
		try {
			content = Files.toString(lootfile, Charsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		JsonElement root = new JsonParser().parse(content);
		
		JsonElement lootList = root.getAsJsonObject().get("CustomLoot");
		JsonArray jList = lootList.getAsJsonArray(); 
		
		for(JsonElement e : jList){
			JsonArray itemData = e.getAsJsonArray(); 
			int id = itemData.get(0).getAsInt();
			int dam = itemData.get(1).getAsInt();
			int min = itemData.get(2).getAsInt();
			int max = itemData.get(3).getAsInt();
			int weight = itemData.get(4).getAsInt();
			int scale = itemData.get(5).getAsInt();
			
			this.addLoot(new WeightedRandomLoot(id, dam, min, max, weight, scale));
		}		
	}
	
	public boolean isEmpty(){
		return this.lootList.isEmpty();
	}
}