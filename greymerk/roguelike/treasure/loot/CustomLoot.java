package greymerk.roguelike.treasure.loot;


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
import net.minecraft.src.WeightedRandomItem;



public class CustomLoot{

	public static void parseLoot(){
		
		// make sure file exists
		File configDir = new File(RogueConfig.configDirName);
		if(!configDir.exists()) return;

		File lootfile = new File(RogueConfig.configDirName + "/" + "roguelike-loot.cfg");

		String content;
		
		try {
			content = Files.toString(lootfile, Charsets.UTF_8);
		} catch (IOException e1) {
			//e1.printStackTrace();
			return;
		}
		
		JsonParser jParser = new JsonParser();
		JsonObject root = (JsonObject)jParser.parse(content);
		JsonElement lootChanges = root.get("CustomLoot");
		JsonArray changeList = lootChanges.getAsJsonArray();

		for(JsonElement change : changeList){
			parseEntry(change.getAsJsonObject());
		}
	}
	
	public static void parseEntry(JsonObject entry){
		if(entry.has("clear")) clear(entry);
		if(entry.has("add")) add(entry);
		if(entry.has("addAll")) addAll(entry);
	}
	
	public static void clear(JsonObject entry){
		JsonElement typeData = entry.get("clear");
		String type = typeData.getAsString();
		Loot.clear(Loot.valueOf(type));
	}
	
	public static void add(JsonObject entry){
		JsonObject item = entry.get("add").getAsJsonObject();
		
		int level = item.get("level").getAsInt();
		String type = item.get("type").getAsString();
		int id = item.get("id").getAsInt();
		int dam = item.get("meta").getAsInt();
		int min = item.get("min").getAsInt();
		int max = item.get("max").getAsInt();
		int weight = item.get("weight").getAsInt();
		
		WeightedRandomLoot toAdd = new WeightedRandomLoot(id, dam, min, max, weight);
		
		Loot.add(Loot.valueOf(type), toAdd, level);
	}
	
	public static void addAll(JsonObject entry){
		JsonObject item = entry.get("addAll").getAsJsonObject();
		
		String type = item.get("type").getAsString();
		int id = item.get("id").getAsInt();
		int dam = item.get("meta").getAsInt();
		int min = item.get("min").getAsInt();
		int max = item.get("max").getAsInt();
		int weight = item.get("weight").getAsInt();
		
		WeightedRandomLoot toAdd = new WeightedRandomLoot(id, dam, min, max, weight);
		
		Loot.addAllLevels(Loot.valueOf(type), toAdd);
	}

}