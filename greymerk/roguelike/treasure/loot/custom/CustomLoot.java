package greymerk.roguelike.treasure.loot.custom;


import greymerk.roguelike.config.RogueConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	private List<LootProvider> lootList;
		
	public CustomLoot(){
		lootList = new ArrayList<LootProvider>();
		
		for(int i = 0; i <= 4; i++){
			lootList.add(new LootProvider());
		}
	}
	
	public ItemStack getLoot(Random rand, int level) {
		return lootList.get(level).getLoot(rand);
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
		
		JsonElement lootTable = root.getAsJsonObject().get("CustomLoot");
		
		for(int i = 0; i <= 4; i++){
			JsonObject tableData = lootTable.getAsJsonObject();
			JsonElement tierData = tableData.get(Integer.toString(i));
			JsonArray tier = tierData.getAsJsonArray();
			for(JsonElement loot : tier){
				JsonArray itemData = loot.getAsJsonArray(); 
				int id = itemData.get(0).getAsInt();
				int dam = itemData.get(1).getAsInt();
				int min = itemData.get(2).getAsInt();
				int max = itemData.get(3).getAsInt();
				int weight = itemData.get(4).getAsInt();
				
				lootList.get(i).add(new WeightedRandomLoot(id, dam, min, max, weight));
			}
		}		
	}
	
	public boolean isEmpty(){
		return this.lootList.isEmpty();
	}
}