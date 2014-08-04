package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.config.RogueConfig;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CatacombSettingsResolver {

	private static final String SETTINGS_DIRECTORY = RogueConfig.configDirName + "/settings";
	Map<String, CatacombSettings> settings;
	
	public CatacombSettingsResolver(){
		settings = new HashMap<String, CatacombSettings>();
		settings.put("default", new CatacombSettingsDefault());
		File settingsDir = new File(SETTINGS_DIRECTORY);
		if(!settingsDir.exists() || !settingsDir.isDirectory()) return;
		File[] settingsFiles = settingsDir.listFiles();
		Arrays.sort(settingsFiles);
		for(int i = 0; i < settingsFiles.length; ++i){
			File toParse = settingsFiles[i];
			System.out.println(toParse.getName());
			CatacombSettings toAdd = parseFile(toParse); 
			settings.put(toAdd.getName(), toAdd);
		}
	}
	
	private CatacombSettings parseFile(File toParse){
		String content;
		
		try {
			content = Files.toString(toParse, Charsets.UTF_8);
		} catch (IOException e1) {
			return null;
		}
		
		JsonParser jParser = new JsonParser();
		JsonObject root = (JsonObject)jParser.parse(content);
		
		return new CatacombSettings(settings, root);
	}
	
	public CatacombSettings getByName(String name){
		return new CatacombSettings(settings.get("default"), this.settings.get(name));
	}
	
	public CatacombSettings getSettings(){
		return new CatacombSettings(settings.get("default"));
	}
}
