package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.config.RogueConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CatacombSettingsResolver {

	private static final String SETTINGS_DIRECTORY = RogueConfig.configDirName + "/settings";
	Map<String, CatacombSettings> settings;
	CatacombSettings base;
	
	public CatacombSettingsResolver(){
		settings = new HashMap<String, CatacombSettings>();
		base = new CatacombSettings();
		File settingsDir = new File(SETTINGS_DIRECTORY);
		if(!settingsDir.exists() || !settingsDir.isDirectory()) return;
		File[] settingsFiles = settingsDir.listFiles();
		for(int i = 0; i < settingsFiles.length; ++i){
			File toParse = settingsFiles[i];
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
		return new CatacombSettings(base, this.settings.get(name));
	}
	
	public CatacombSettings getSettings(){
		return base;
	}
}
