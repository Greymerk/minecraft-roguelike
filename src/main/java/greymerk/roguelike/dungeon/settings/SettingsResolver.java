package greymerk.roguelike.dungeon.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.builtin.SettingsBase;
import greymerk.roguelike.dungeon.settings.builtin.SettingsDesertTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsForestTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsGenerator;
import greymerk.roguelike.dungeon.settings.builtin.SettingsGrasslandTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsIceTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsJungleTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsLootRules;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMesaTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMountainTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsRooms;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSecrets;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSegments;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSize;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSwampTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsTheme;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class SettingsResolver {

	
	private SettingsContainer settings;
	
	public SettingsResolver() throws Exception{
		settings = new SettingsContainer();
		
		this.settings.put(new SettingsRooms());
		this.settings.put(new SettingsSecrets());
		this.settings.put(new SettingsSegments());
		this.settings.put(new SettingsSize());
		this.settings.put(new SettingsTheme());
		this.settings.put(new SettingsGenerator());
		this.settings.put(new SettingsLootRules());
		this.settings.put(new SettingsBase());
		
		this.settings.put(new SettingsDesertTheme());
		this.settings.put(new SettingsGrasslandTheme());
		this.settings.put(new SettingsJungleTheme());
		this.settings.put(new SettingsSwampTheme());
		this.settings.put(new SettingsMountainTheme());
		this.settings.put(new SettingsForestTheme());
		this.settings.put(new SettingsMesaTheme());
		this.settings.put(new SettingsIceTheme());

	}
	
	public void parseCustomSettings(Map<String, String> files) throws Exception{
		for(String name : files.keySet()){
			DungeonSettings toAdd = null;
			try{
				toAdd = parseFile(files.get(name));
			} catch (Exception e){
				throw new Exception("Error in: " + name + " : " + e.getMessage());
			}
			settings.put(toAdd);
		}
	}

	// called from Dungeon class
	public ISettings getSettings(IWorldEditor editor, Random rand, Coord pos) throws Exception{
		
		DungeonSettings builtin = this.getBuiltin(editor, rand, pos);
		DungeonSettings custom = this.getCustom(editor, rand, pos);
		
		// there are no valid dungeons for this location
		if(builtin == null && custom == null) return null;
		
		DungeonSettings exclusive = (custom != null) ? custom : builtin;
				
		return applyInclusives(exclusive, editor, rand, pos);
	}
	
	public ISettings getWithName(String name, IWorldEditor editor, Random rand, Coord pos) throws Exception{
		if(name.equals("random")){
			return generateRandom(editor, rand, pos);
		}
		
		DungeonSettings byName = this.getByName(name);
		if(byName == null) return null;
		DungeonSettings withInclusives = applyInclusives(byName, editor, rand, pos);
		return new DungeonSettings(new SettingsBlank(), withInclusives);
	}
	
	public ISettings getDefaultSettings(){
		return new DungeonSettings(settings.get(new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "base")));
	}

	public DungeonSettings getByName(String name) throws Exception{
		SettingIdentifier id;
		
		try{
			id = new SettingIdentifier(name);
		} catch (Exception e){
			throw new Exception("Malformed Setting ID String: " + name);
		}
		
		if(!this.settings.contains(id)) return null;
		DungeonSettings setting = new DungeonSettings(this.settings.get(id));
		return processInheritance(setting, this.settings);
	}
	
	public ISettings getWithDefault(SettingIdentifier id) throws Exception {
		if(!this.settings.contains(id)) return null;
		DungeonSettings setting = new DungeonSettings(this.settings.get(id));
		return processInheritance(setting, this.settings);
	}
	
	public static DungeonSettings processInheritance(DungeonSettings toProcess, SettingsContainer settings) throws Exception{
		DungeonSettings setting = new SettingsBlank();
		
		for(SettingIdentifier id : toProcess.getInherits()){
			if(settings.contains(id)){
				DungeonSettings inherited = new DungeonSettings(settings.get(id));
				if(!inherited.getInherits().isEmpty()){
					inherited = processInheritance(inherited, settings);
				}
				
				setting = new DungeonSettings(setting, inherited);
			} else {
				throw new Exception("Setting not found: " + id.toString());
			}
		}
		
		return new DungeonSettings(setting, toProcess);
	}
	
	private DungeonSettings parseFile(String content) throws Exception{
		
		JsonParser jParser = new JsonParser();
		JsonObject root = null;
		DungeonSettings toAdd = null;
		
		try {
			root = (JsonObject)jParser.parse(content);
		} catch (JsonSyntaxException e){
			Throwable cause = e.getCause();
			throw new Exception(cause.getMessage());
		} catch (Exception e){
			throw new Exception("An unknown error occurred while parsing json");
		}
		
		toAdd = new DungeonSettings(root);
		
		return toAdd;
	}
	
	private DungeonSettings getBuiltin(IWorldEditor editor, Random rand, Coord pos) throws Exception{
		WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<DungeonSettings>();

		for(DungeonSettings setting : settings.getBuiltinSettings()){			
			if(setting.isValid(editor, pos)){
				settingsRandomizer.add(new WeightedChoice<DungeonSettings>(setting, setting.criteria.weight));
			}
		}
		
		DungeonSettings chosen = settingsRandomizer.get(rand);
		
		return processInheritance(chosen, settings);
	}
	
	private DungeonSettings getCustom(IWorldEditor editor, Random rand, Coord pos) throws Exception{
		
		WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<DungeonSettings>();
		
		for(DungeonSettings setting : settings.getCustomSettings()){
			if(setting.isValid(editor, pos) && setting.isExclusive()){
				settingsRandomizer.add(new WeightedChoice<DungeonSettings>(setting, setting.criteria.weight));
			}
		}
		
		DungeonSettings chosen = settingsRandomizer.get(rand);
		
		if(chosen == null) return null;
		
		return processInheritance(chosen, settings);
	}
	
	private DungeonSettings applyInclusives(DungeonSettings setting, IWorldEditor editor, Random rand, Coord pos){
		
		DungeonSettings toReturn = new DungeonSettings(setting);
		
		for(DungeonSettings s : settings.getCustomSettings()){
			if(!s.isValid(editor, pos)) continue;
			if(s.isExclusive()) continue;
			toReturn = new DungeonSettings(toReturn, s);
		}
		
		return toReturn;
	}
	
	public ISettings generateRandom(IWorldEditor editor, Random rand, Coord pos){
	
		DungeonSettings setting = new SettingsBlank();
		
		setting.towerSettings = new TowerSettings(
				Tower.getRandom(rand),
				Theme.getRandom(rand));
		
		Map<Integer, LevelSettings> levels = new HashMap<Integer, LevelSettings>();
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			
			level.setDifficulty(i);
			level.setGenerator(LevelGenerator.CLASSIC);
			level.setNumRooms(15);
			level.setRange(60);
			
			DungeonFactory rooms = DungeonFactory.getRandom(rand, 8);
			level.setRooms(rooms);
			
			level.setScatter(15);
			
			SecretFactory secrets = SecretFactory.getRandom(rand, 2);
			level.setSecrets(secrets);
			
			SegmentGenerator segments = SegmentGenerator.getRandom(rand, 12);
			level.setSegments(segments);
			
			level.setTheme(Theme.getRandom(rand));
			levels.put(i, level);
		}
		
		setting.levels = levels;
		
		return setting;
		
	}
	
	@Override
	public String toString(){
		return this.settings.toString();		
	}
}
