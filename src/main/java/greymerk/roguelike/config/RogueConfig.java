package greymerk.roguelike.config;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Tuple;

public enum RogueConfig {

	DONATURALSPAWN, LEVELRANGE, LEVELMAXROOMS, LEVELSCATTER, SPAWNFREQUENCY, GENEROUS, MOBDROPS, DIMENSIONWL, DIMENSIONBL, 
	PRECIOUSBLOCKS, LOOTING, DONOVELTYSPAWN, UPPERLIMIT, LOWERLIMIT, ROGUESPAWNERS, ENCASE, FURNITURE;
	
	public static final String configDirName = "config/roguelike_dungeons";
	public static final String configFileName = "roguelike.cfg";
	
	public static boolean testing = false;
	private static ConfigFile instance = null;
	
	public static String getName(RogueConfig option){
		switch(option){
		case DONATURALSPAWN: return "doNaturalSpawn";
		case DONOVELTYSPAWN: return "doNoveltySpawn";
		case LEVELRANGE: return "levelRange";
		case LEVELMAXROOMS: return "levelMaxRooms";
		case LEVELSCATTER: return "levelScatter";
		case SPAWNFREQUENCY: return "spawnFrequency";
		case GENEROUS: return "generous";
		case DIMENSIONWL: return "dimensionWL";
		case DIMENSIONBL: return "dimensionBL";
		case PRECIOUSBLOCKS: return "preciousBlocks";
		case LOOTING: return "looting";
		case UPPERLIMIT: return "upperLimit";
		case LOWERLIMIT: return "lowerLimit";
		case ROGUESPAWNERS: return "rogueSpawners";
		case ENCASE: return "encase";
		case FURNITURE: return "furniture";
		default: return null;
		}
	}
	
	public static Tuple<String, ?> getDefault(RogueConfig option){
		switch(option){
		case DONATURALSPAWN: return new Tuple<String, Boolean>(getName(option), true);
		case DONOVELTYSPAWN: return new Tuple<String, Boolean>(getName(option), true);
		case LEVELRANGE: return new Tuple<String, Integer>(getName(option), 80);
		case LEVELMAXROOMS: return new Tuple<String, Integer>(getName(option), 30);
		case LEVELSCATTER: return new Tuple<String, Integer>(getName(option), 10);
		case SPAWNFREQUENCY: return new Tuple<String, Integer>(getName(option), 10);
		case GENEROUS: return new Tuple<String, Boolean>(getName(option), true);
		case DIMENSIONWL:
			List<Integer> bl = new ArrayList<Integer>();
			bl.add(0);
			return new Tuple<String, List<Integer>>(getName(option), bl);
		case DIMENSIONBL:
			return new Tuple<String, List<Integer>>(getName(option), new ArrayList<Integer>());
		case PRECIOUSBLOCKS: return new Tuple<String, Boolean>(getName(option), true);
		case LOOTING: return new Tuple<String, Double>(getName(option), 0.085D);
		case UPPERLIMIT: return new Tuple<String, Integer>(getName(option), 100);
		case LOWERLIMIT: return new Tuple<String, Integer>(getName(option), 60);
		case ROGUESPAWNERS: return new Tuple<String, Boolean>(getName(option), true);
		case ENCASE: return new Tuple<String, Boolean>(getName(option), false);
		case FURNITURE: return new Tuple<String, Boolean>(getName(option), true);
		default: return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void setDefaults(){
		if(!instance.ContainsKey(getName(DONATURALSPAWN))) setBoolean(DONATURALSPAWN, (Boolean)getDefault(DONATURALSPAWN).getSecond());
		if(!instance.ContainsKey(getName(DONOVELTYSPAWN))) setBoolean(DONOVELTYSPAWN, (Boolean)getDefault(DONOVELTYSPAWN).getSecond());
		if(!instance.ContainsKey(getName(LEVELRANGE)))setInt(LEVELRANGE, (Integer)getDefault(LEVELRANGE).getSecond());
		if(!instance.ContainsKey(getName(LEVELMAXROOMS)))setInt(LEVELMAXROOMS, (Integer)getDefault(LEVELMAXROOMS).getSecond());
		if(!instance.ContainsKey(getName(LEVELSCATTER)))setInt(LEVELSCATTER, (Integer)getDefault(LEVELSCATTER).getSecond());
		if(!instance.ContainsKey(getName(SPAWNFREQUENCY)))setInt(SPAWNFREQUENCY, (Integer)getDefault(SPAWNFREQUENCY).getSecond());
		if(!instance.ContainsKey(getName(GENEROUS))) setBoolean(GENEROUS, (Boolean)getDefault(GENEROUS).getSecond());
		if(!instance.ContainsKey(getName(DIMENSIONWL)))setIntList(DIMENSIONWL, (List<Integer>)getDefault(DIMENSIONWL).getSecond());
		if(!instance.ContainsKey(getName(DIMENSIONBL)))setIntList(DIMENSIONBL, (List<Integer>)getDefault(DIMENSIONBL).getSecond());
		if(!instance.ContainsKey(getName(PRECIOUSBLOCKS)))setBoolean(PRECIOUSBLOCKS, (Boolean)getDefault(PRECIOUSBLOCKS).getSecond());
		if(!instance.ContainsKey(getName(LOOTING)))setDouble(LOOTING, (Double) getDefault(LOOTING).getSecond());
		if(!instance.ContainsKey(getName(UPPERLIMIT)))setInt(UPPERLIMIT, (Integer) getDefault(UPPERLIMIT).getSecond());
		if(!instance.ContainsKey(getName(LOWERLIMIT)))setInt(LOWERLIMIT, (Integer) getDefault(LOWERLIMIT).getSecond());
		if(!instance.ContainsKey(getName(ROGUESPAWNERS))) setBoolean(ROGUESPAWNERS, (Boolean)getDefault(ROGUESPAWNERS).getSecond());
		if(!instance.ContainsKey(getName(ENCASE))) setBoolean(ENCASE, (Boolean)getDefault(ENCASE).getSecond());
		if(!instance.ContainsKey(getName(FURNITURE))) setBoolean(FURNITURE, (Boolean)getDefault(FURNITURE).getSecond());
	}
	
	public static double getDouble(RogueConfig option){
		if(testing) return (Double)getDefault(option).getSecond();
		reload(false);
		Tuple<String, ?> def = getDefault(option);
		return instance.GetDouble(getName(option), (Double)def.getSecond());
	}
	
	public static void setDouble(RogueConfig option, double value){
		reload(false);
		instance.Set(getName(option), value);
	}
	
	public static boolean getBoolean(RogueConfig option){
		if(testing) return (Boolean)getDefault(option).getSecond();
		reload(false);
		Tuple<String, ?> def = getDefault(option);
		return instance.GetBoolean(getName(option), (Boolean)def.getSecond());
	}
	
	public static void setBoolean(RogueConfig option, Boolean value){
		reload(false);
		instance.Set(getName(option), value);
	}
	
	public static int getInt(RogueConfig option){
		if(testing) return (Integer)getDefault(option).getSecond();
		reload(false);
		Tuple<String, ?> def = getDefault(option);
		return instance.GetInteger((String)def.getFirst(), (Integer)def.getSecond());
	}
	
	public static void setInt(RogueConfig option, int value){
		reload(false);
		Tuple<String, ?> def = getDefault(option);
		instance.Set((String)def.getFirst(), value);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Integer> getIntList(RogueConfig option){
		if(testing) return (ArrayList<Integer>)getDefault(option).getSecond();
		reload(false);
		Tuple<String, ?> def = getDefault(option);
		return instance.GetListInteger((String)def.getFirst(), (ArrayList<Integer>)def.getSecond());
	}
	
	public static void setIntList(RogueConfig option, List<Integer> value){
		reload(false);
		Tuple<String, ?> def = getDefault(option);
		instance.Set((String)def.getFirst(), value);
	}
	
	private static void init(){
		
		if(testing) return;
		
		// make sure file exists
		File configDir = new File(configDirName);
		if(!configDir.exists()){
			configDir.mkdir();
		}
		
		File cfile = new File(configDirName + "/" + configFileName);
		
		if(!cfile.exists()){
			try {
				cfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// read in configs
		try {
			instance = new ConfigFile(configDirName + "/" + configFileName, new INIParser());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaults();
		
		try {
			instance.Write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reload(boolean force){
		if(instance == null || force){
			init();
		}
	}
}
