package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsBasicLoot;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsDesertTheme;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsEniTheme;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsEthoTheme;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsJungleTheme;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsRooms;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsSecrets;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsSegments;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsSize;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsSwampTheme;
import greymerk.roguelike.catacomb.settings.builtin.CatacombSettingsTheme;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.world.World;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CatacombSettingsResolver {

	private static final String SETTINGS_DIRECTORY = RogueConfig.configDirName + "/settings";
	private Map<String, CatacombSettings> settings;
	private List<CatacombSettings> builtin;
	private CatacombSettings base;
	
	public CatacombSettingsResolver(){
		settings = new HashMap<String, CatacombSettings>();
		CatacombSettings base = new CatacombSettingsBlank();
		base = new CatacombSettings(base, new CatacombSettingsBasicLoot());
		base = new CatacombSettings(base, new CatacombSettingsRooms());
		base = new CatacombSettings(base, new CatacombSettingsSecrets());
		base = new CatacombSettings(base, new CatacombSettingsSegments());
		base = new CatacombSettings(base, new CatacombSettingsSize());
		base = new CatacombSettings(base, new CatacombSettingsTheme());
		base.setCriteria(new SpawnCriteria());
		this.base = base;

		this.builtin = new ArrayList<CatacombSettings>();
		this.builtin.add(new CatacombSettingsDesertTheme());
		this.builtin.add(new CatacombSettingsJungleTheme());
		this.builtin.add(new CatacombSettingsSwampTheme());
		this.builtin.add(new CatacombSettingsEniTheme());
		this.builtin.add(new CatacombSettingsEthoTheme());
		
		File settingsDir = new File(SETTINGS_DIRECTORY);
		if(!settingsDir.exists() || !settingsDir.isDirectory()) return;
		File[] settingsFiles = settingsDir.listFiles();
		Arrays.sort(settingsFiles);
		
		for(int i = 0; i < settingsFiles.length; ++i){
			File toParse = settingsFiles[i];
			CatacombSettings toAdd = null;
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
	
	private CatacombSettings parseFile(File toParse) throws Exception{
		String content;
		
		try {
			content = Files.toString(toParse, Charsets.UTF_8);
		} catch (IOException e1) {
			return null;
		}
		
		JsonParser jParser = new JsonParser();
		JsonObject root = null;
		CatacombSettings toAdd = null;
		
		try {
			root = (JsonObject)jParser.parse(content);
		} catch (Exception e){
			throw e;
		}
		
		try {
			toAdd = new CatacombSettings(settings, root);
		} catch (Exception e){
			throw e;
		}
		
		return toAdd;
	}
	
	public CatacombSettings getByName(String name){
		CatacombSettings override = this.settings.get(name);
		if(override == null) return null;
		return new CatacombSettings(this.base, override);
	}
	
	public ICatacombSettings getSettings(World world, Random rand, Coord pos){
		
		CatacombSettings builtin = this.getBuiltin(world, rand, pos);
		CatacombSettings custom = this.getCustom(world, rand, pos);
		
		if(custom != null){
			return new CatacombSettings(this.base, custom);
		}
		
		if(builtin != null && RogueConfig.getBoolean(RogueConfig.DONOVELTYSPAWN)){
			return new CatacombSettings(this.base, builtin);
		}
		
		if(this.base.isValid(world, pos)) return new CatacombSettings(this.base);
		
		return null;
		
	}
	
	private CatacombSettings getBuiltin(World world, Random rand, Coord pos){
		WeightedRandomizer<CatacombSettings> settingsRandomizer = new WeightedRandomizer<CatacombSettings>();

		for(CatacombSettings setting : this.builtin){
			if(setting.isValid(world, pos)){
				settingsRandomizer.add(new WeightedChoice<CatacombSettings>(setting, setting.criteria.weight));
			}
		}
		
		return settingsRandomizer.get(rand);
	}
	
	private CatacombSettings getCustom(World world, Random rand, Coord pos){
		WeightedRandomizer<CatacombSettings> settingsRandomizer = new WeightedRandomizer<CatacombSettings>();
		
		for(CatacombSettings setting : this.settings.values()){
			if(setting.isValid(world, pos)){
				int weight = setting.criteria.weight;
				settingsRandomizer.add(new WeightedChoice<CatacombSettings>(setting, weight));
			}
		}
		
		return settingsRandomizer.get(rand);
	}
	
	public ICatacombSettings getDefaultSettings(){
		return new CatacombSettings(base);
	}
}
