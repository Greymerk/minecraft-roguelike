package greymerk.roguelike.treasure.loot;


import greymerk.roguelike.config.RogueConfig;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



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
	}
}