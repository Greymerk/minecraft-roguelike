package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.worldgen.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class CatacombSettings implements ICatacombSettings{

	
	private static final int NUM_LEVELS = 5;
	private String name;
	protected Map<Integer, CatacombLevelSettings> levels;
	protected SpawnCriteria criteria;
	
	public CatacombSettings(){
		this.levels = new HashMap<Integer, CatacombLevelSettings>();
	}
	
	public CatacombSettings(Map<String, CatacombSettings> settings, JsonObject root){
		
		this.name = root.get("name").getAsString();
		
		if(root.has("criteria")){
			this.criteria = new SpawnCriteria(root.get("criteria").getAsJsonObject());
		}
		
		levels = new HashMap<Integer, CatacombLevelSettings>();
		
		List<String> inheritList = new ArrayList<String>();
		if(root.has("inherit")){
			JsonArray inherit = root.get("inherit").getAsJsonArray();
			for(JsonElement e : inherit){
				inheritList.add(e.getAsString());
			}
		}
		
		CatacombSettings base = new CatacombSettingsBlank();
		
		for(String name : inheritList){
			if(settings.containsKey(name)){
				base = new CatacombSettings(base, settings.get(name));
			}
		}
		
		parseJson(base, root);
	}
	
	private void parseJson(CatacombSettings base, JsonObject root){
		
		if(!root.has("levels")){
			this.levels.putAll(base.levels);
			return;
		}
		
		JsonObject levelSet = root.get("levels").getAsJsonObject();
		
		for(int i = 0; i < 5; ++i){
			if(levelSet.has(Integer.toString(i))){
				JsonObject data = levelSet.get(Integer.toString(i)).getAsJsonObject();
				CatacombLevelSettings override = new CatacombLevelSettings(data);
				CatacombLevelSettings setting = new CatacombLevelSettings(base.getLevelSettings(i), override);
				this.levels.put(i, setting);
			} else {
				this.levels.put(i, base.getLevelSettings(i));
			}
		}
	}
	
	public CatacombSettings(CatacombSettings base, CatacombSettings override){
		levels = new HashMap<Integer, CatacombLevelSettings>();
		
		for(int i = 0; i < NUM_LEVELS; ++i){
			if(override.levels.get(i) == null){
				levels.put(i, new CatacombLevelSettings(base.levels.get(i)));
			} else {
				levels.put(i, new CatacombLevelSettings(base.levels.get(i), override.levels.get(i)));
			}
		}
	}
	
	public CatacombSettings(CatacombSettings toCopy){
		this.levels = new HashMap<Integer, CatacombLevelSettings>();
		
		for(int i = 0; i < NUM_LEVELS; ++i){
			this.levels.put(i, new CatacombLevelSettings(toCopy.levels.get(i)));
		}
	}

	public String getName(){
		return this.name;
	}
	
	public void setCriteria(SpawnCriteria criteria){
		this.criteria = criteria;
	}
	
	@Override
	public boolean isValid(World world, Coord pos) {
		
		if(this.criteria == null) return false;
		
		return this.criteria.isValid(world, pos);
	}

	@Override
	public CatacombLevelSettings getLevelSettings(int level) {
		return levels.get(level);
	}
}
