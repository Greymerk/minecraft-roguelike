package greymerk.roguelike.dungeon.settings;

import greymerk.roguelike.dungeon.theme.Theme;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.worldgen.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class CatacombSettings implements ICatacombSettings{
	
	public static final int MAX_NUM_LEVELS = 5;
	private String name;
	protected CatacombTowerSettings towerSettings;
	protected Map<Integer, CatacombLevelSettings> levels;
	protected SpawnCriteria criteria;
	protected int depth;
	
	public CatacombSettings(){
		this.levels = new HashMap<Integer, CatacombLevelSettings>();
		this.depth = 0;
	}
	
	public CatacombSettings(Map<String, CatacombSettings> settings, JsonObject root) throws Exception{
		
		this.name = root.get("name").getAsString();
		
		if(root.has("criteria")){
			this.criteria = new SpawnCriteria(root.get("criteria").getAsJsonObject());
		}
		
		if(root.has("depth")){
			int depth = root.get("depth").getAsInt();
			if(depth < 1) this.depth = 1;
			if(depth > 5) this.depth = 5;
			this.depth = depth;
		}
		
		if(root.has("tower")){
			this.towerSettings = new CatacombTowerSettings(root.get("tower"));
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
			} else {
				throw new Exception(name + " must be previously defined in order to be inherited");
			}
		}
		
		parseJson(base, root);
	}
	
	private void parseJson(CatacombSettings base, JsonObject root){
		
		if(!root.has("tower")){
			this.towerSettings = base.towerSettings;
		}
		
		if(!root.has("depth")){
			this.depth = base.depth;
		}
		
		if(!root.has("levels")){
			this.levels.putAll(base.levels);
			return;
		}
		
		JsonObject levelSet = root.get("levels").getAsJsonObject();
		
		for(int i = 0; i < 5; ++i){
			
			CatacombLevelSettings setting = new CatacombLevelSettings();
			
			if(levelSet.has("all")){
				JsonObject data = levelSet.get("all").getAsJsonObject();
				setting = new CatacombLevelSettings(setting, new CatacombLevelSettings(data));
			}
			
			if(levelSet.has(Integer.toString(i))){
				JsonObject data = levelSet.get(Integer.toString(i)).getAsJsonObject();
				setting = new CatacombLevelSettings(setting, new CatacombLevelSettings(data));
			}
			
			this.levels.put(i, setting);
		}
	}
	
	public CatacombSettings(CatacombSettings base, CatacombSettings override){
		
		if(override.depth != 0){
			depth = override.depth;
		} else {
			depth = base.depth;
		}
		
		if(override.towerSettings == null){
			this.towerSettings = base.towerSettings;
		} else {
			this.towerSettings = override.towerSettings;
		}
		
		levels = new HashMap<Integer, CatacombLevelSettings>();
		
		for(int i = 0; i < MAX_NUM_LEVELS; ++i){
			if(override.levels.get(i) == null){
				levels.put(i, new CatacombLevelSettings(base.levels.get(i)));
			} else {
				levels.put(i, new CatacombLevelSettings(base.levels.get(i), override.levels.get(i)));
			}
		}
	}
	
	public CatacombSettings(CatacombSettings toCopy){
		
		this.depth = toCopy.depth;
		
		this.levels = new HashMap<Integer, CatacombLevelSettings>();
		
		for(int i = 0; i < MAX_NUM_LEVELS; ++i){
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
	
	@Override
	public CatacombTowerSettings getTower(){
		if(this.towerSettings == null) return new CatacombTowerSettings(Tower.ROGUE, Theme.getTheme(Theme.TOWER));
		
		return this.towerSettings;
	}

	@Override
	public int getNumLevels() {
		
		if(depth < 0) return 0;
		if(depth > MAX_NUM_LEVELS) return MAX_NUM_LEVELS;
		
		return depth;
	}
}
