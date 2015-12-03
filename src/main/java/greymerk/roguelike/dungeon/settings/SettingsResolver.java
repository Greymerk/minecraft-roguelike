package greymerk.roguelike.dungeon.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.builtin.SettingsBasicLoot;
import greymerk.roguelike.dungeon.settings.builtin.SettingsDesertTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsForestTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsGenerator;
import greymerk.roguelike.dungeon.settings.builtin.SettingsJungleTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMesaTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMountainTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsRooms;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSecrets;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSegments;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSize;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSwampTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsTheme;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class SettingsResolver {

	private static final String SETTINGS_DIRECTORY = RogueConfig.configDirName + "/settings";
	private Map<String, DungeonSettings> settings;
	private List<DungeonSettings> builtin;
	private DungeonSettings base;
	
	public SettingsResolver(){
		settings = new HashMap<String, DungeonSettings>();
		DungeonSettings base = new SettingsBlank();
		base = new DungeonSettings(base, new SettingsBasicLoot());
		base = new DungeonSettings(base, new SettingsRooms());
		base = new DungeonSettings(base, new SettingsSecrets());
		base = new DungeonSettings(base, new SettingsSegments());
		base = new DungeonSettings(base, new SettingsSize());
		base = new DungeonSettings(base, new SettingsTheme());
		base = new DungeonSettings(base, new SettingsGenerator());
		base.setCriteria(new SpawnCriteria());
		this.base = base;

		this.builtin = new ArrayList<DungeonSettings>();
		this.builtin.add(new SettingsDesertTheme());
		this.builtin.add(new SettingsJungleTheme());
		this.builtin.add(new SettingsSwampTheme());
		this.builtin.add(new SettingsMountainTheme());
		this.builtin.add(new SettingsForestTheme());
		this.builtin.add(new SettingsMesaTheme());
		
		File settingsDir = new File(SETTINGS_DIRECTORY);
		if(!settingsDir.exists() || !settingsDir.isDirectory()) return;
		File[] settingsFiles = settingsDir.listFiles();
		Arrays.sort(settingsFiles);
		
		for(int i = 0; i < settingsFiles.length; ++i){
			File toParse = settingsFiles[i];
			DungeonSettings toAdd = null;
			try{
				toAdd = parseFile(toParse);
			} catch (Exception e){
				System.err.println("Error found in file " + toParse.getName());
				Throwable cause = e.getCause();
				if(cause != null){
					System.err.println(cause.getMessage());
				} else {
					System.err.println(e.getMessage());
				}
				return;
			}
			settings.put(toAdd.getName(), toAdd);
		}
	}
	
	private DungeonSettings parseFile(File toParse) throws Exception{
		String content;
		
		try {
			content = Files.toString(toParse, Charsets.UTF_8);
		} catch (IOException e1) {
			return null;
		}
		
		JsonParser jParser = new JsonParser();
		JsonObject root = null;
		DungeonSettings toAdd = null;
		
		try {
			root = (JsonObject)jParser.parse(content);
		} catch (Exception e){
			throw e;
		}
		
		try {
			toAdd = new DungeonSettings(settings, root);
		} catch (Exception e){
			throw e;
		}
		
		return toAdd;
	}
	
	public DungeonSettings getByName(String name){
		DungeonSettings override = this.settings.get(name);
		if(override == null) return null;
		return new DungeonSettings(this.base, override);
	}
	
	public ISettings getSettings(WorldEditor editor, Random rand, Coord pos){
		
		DungeonSettings builtin = this.getBuiltin(editor, rand, pos);
		DungeonSettings custom = this.getCustom(editor, rand, pos);
		
		if(custom != null){
			return new DungeonSettings(this.base, custom);
		}
		
		if(builtin != null && RogueConfig.getBoolean(RogueConfig.DONOVELTYSPAWN)){
			return new DungeonSettings(this.base, builtin);
		}
		
		if(this.base.isValid(editor, pos)) return new DungeonSettings(this.base);
		
		return null;
		
	}
	
	private DungeonSettings getBuiltin(WorldEditor editor, Random rand, Coord pos){
		WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<DungeonSettings>();

		for(DungeonSettings setting : this.builtin){
			if(setting.isValid(editor, pos)){
				settingsRandomizer.add(new WeightedChoice<DungeonSettings>(setting, setting.criteria.weight));
			}
		}
		
		return settingsRandomizer.get(rand);
	}
	
	private DungeonSettings getCustom(WorldEditor editor, Random rand, Coord pos){
		WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<DungeonSettings>();
		
		for(DungeonSettings setting : this.settings.values()){
			if(setting.isValid(editor, pos)){
				int weight = setting.criteria.weight;
				settingsRandomizer.add(new WeightedChoice<DungeonSettings>(setting, weight));
			}
		}
		
		return settingsRandomizer.get(rand);
	}
	
	public ISettings getDefaultSettings(){
		return new DungeonSettings(base);
	}
}
