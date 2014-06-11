package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.config.RogueConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.biome.BiomeGenBase;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DungeonCustomization {

	private static List<Customization> customizations = new ArrayList<Customization>();
	
	static{
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ITheme getTheme(BiomeGenBase biome, int level){
		Customization c = getCustomization(biome);
		if(c == null) return null;
		return c.getTheme(level);
	}
	
	public static IDungeonFactory getRooms(BiomeGenBase biome, int level){
		Customization c = getCustomization(biome);
		if(c == null) return null;
		return c.getRooms(level);
	}
	
	private static Customization getCustomization(BiomeGenBase biome){
		
		for(Customization custom : customizations){
			if(custom.global) return custom;
			if(custom.match(biome.biomeName)) return custom;
		}
		
		return null;
	}
	
	private static void init() throws Exception{
		File configDir = new File(RogueConfig.configDirName);
		if(!configDir.exists()) return;

		File dungeonfile = new File(RogueConfig.configDirName + "/" + "roguelike-theme.cfg");
		String content;
		
		try {
			content = Files.toString(dungeonfile, Charsets.UTF_8);
		} catch (IOException e1) {
			return;
		}
		
		JsonParser jParser = new JsonParser();
		JsonObject root = (JsonObject)jParser.parse(content);
		JsonElement configs = root.get("DungeonConfig");
		
		JsonArray configArray = configs.getAsJsonArray();

		for(JsonElement confElement : configArray){
			customizations.add(new Customization(confElement.getAsJsonObject()));
		}
	}
	
	private static class Customization{
		
		private boolean global;
		private List<String> biomes;
		private List<ITheme> theme;
		private List<IDungeonFactory> rooms; 
		
		public Customization(JsonObject json) throws Exception{
			biomes = new ArrayList<String>();
			theme = new ArrayList<ITheme>();
			rooms = new ArrayList<IDungeonFactory>();
			
			for(int i = 0; i < 5; ++i){
				theme.add(null);
				rooms.add(null);
			}
			
			if(json.get("biomes") instanceof JsonArray){
				global = false;
				parseBiomes(json.get("biomes").getAsJsonArray());
			} else if(json.get("biomes").getAsString().equals("all")){
				global = true;
			}

			if(json.has("levels")) parseLevels(json.get("levels").getAsJsonObject());
		}
		
		private void parseBiomes(JsonArray json){
			for(JsonElement e : json){
				biomes.add(e.getAsString());
			}
		}
		
		private void parseLevels(JsonObject json) throws Exception{
			if(json.has("all")){
				for(int i = 0; i < Catacomb.NUM_LEVELS; ++i){
					parseLevel(json.get("all").getAsJsonObject(), i);
				}
			}
			
			for(int i = 0; i < Catacomb.NUM_LEVELS; ++i){
				String level = Integer.toString(i); 
				if(json.has(level)) parseLevel(json.get(level).getAsJsonObject(), i);			
			}
		}
		
		private void parseLevel(JsonObject json, int level) throws Exception{
			if(json.has("theme")){
				JsonObject themeData = json.get("theme").getAsJsonObject();
				theme.set(level, Theme.create(themeData));
			}
			
			if(json.has("rooms")){
				JsonArray roomData = json.get("rooms").getAsJsonArray();
				IDungeonFactory dungeons = new DungeonFactory(roomData);
				rooms.set(level, dungeons);
			}
		}
		
		public boolean match(String name){
			
			for(String s : biomes){
				if(name.equals(s)) return true;
			}
			
			return false;
		}
		
		public ITheme getTheme(int level){
			return theme.get(level);
		}
		
		public IDungeonFactory getRooms(int level){
			return rooms.get(level);
		}
	}
}
