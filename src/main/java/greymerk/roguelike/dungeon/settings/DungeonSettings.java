package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;


public class DungeonSettings implements ISettings{
	
	public static final int MAX_NUM_LEVELS = 5;
	private String name;
	protected TowerSettings towerSettings;
	protected Map<Integer, LevelSettings> levels;
	protected SpawnCriteria criteria;
	protected int depth;
	protected LootRuleManager lootRules;
	protected List<SettingsType> overrides;
	
	public DungeonSettings(){
		this.levels = new HashMap<Integer, LevelSettings>();
		this.lootRules = new LootRuleManager();
		this.depth = 0;
	}
	
	public DungeonSettings(Map<String, DungeonSettings> settings, JsonObject root) throws Exception{
		
		this.lootRules = new LootRuleManager();
		levels = new HashMap<Integer, LevelSettings>();
		this.depth = 0;
		
		this.name = root.get("name").getAsString();
		if(root.has("criteria")) this.criteria = new SpawnCriteria(root.get("criteria").getAsJsonObject());
		if(root.has("tower")) this.towerSettings = new TowerSettings(root.get("tower"));
		if(root.has("loot_rules")) this.lootRules = new LootRuleManager(root.get("loot_rules"));
		
		if(root.has("depth")){
			int depth = root.get("depth").getAsInt();
			if(depth < 1) this.depth = 1;
			if(depth > 5) this.depth = 5;
			this.depth = depth;
		}
		
		if(root.has("overrides")){
			this.overrides = new ArrayList<SettingsType>();
			JsonArray overrides = root.get("overrides").getAsJsonArray();
			for(JsonElement e : overrides){
				String type = e.getAsString();
				this.overrides.add(SettingsType.valueOf(type));
			}
		}
		
		List<String> inheritList = new ArrayList<String>();
		if(root.has("inherit")){
			JsonArray inherit = root.get("inherit").getAsJsonArray();
			for(JsonElement e : inherit){
				inheritList.add(e.getAsString());
			}
		}
		
		// generate an inherit base and then apply inherited aspects.
		DungeonSettings base = new SettingsBlank();
		for(String name : inheritList){
			if(settings.containsKey(name)){
				base = new DungeonSettings(base, settings.get(name));
			} else {
				System.err.println(name + " setting inherited before creation in " + this.name);
			}
		}
		
		this.lootRules.add(base.lootRules);
		if(!root.has("depth")) this.depth = base.depth;
		if(!root.has("overrides")) this.overrides = base.overrides;
		if(!root.has("tower")) this.towerSettings = base.towerSettings;
		if(!root.has("levels")){
			this.levels.putAll(base.levels);
			return;
		}
		
		JsonObject levelSet = root.get("levels").getAsJsonObject();
		for(int i = 0; i < 5; ++i){
			LevelSettings setting = new LevelSettings(base.getLevelSettings(i));
			
			if(levelSet.has("all")){
				JsonObject data = levelSet.get("all").getAsJsonObject();
				setting = new LevelSettings(setting, new LevelSettings(data));
			}
			
			if(levelSet.has(Integer.toString(i))){
				JsonObject data = levelSet.get(Integer.toString(i)).getAsJsonObject();
				setting = new LevelSettings(setting, new LevelSettings(data));
			}
			
			this.levels.put(i, setting);
		}
	}
	
	public DungeonSettings(DungeonSettings base, DungeonSettings override){
		
		levels = new HashMap<Integer, LevelSettings>();
		if(override.depth != 0){
			depth = override.depth;
		} else {
			depth = base.depth;
		}
		
		this.lootRules = new LootRuleManager();
		if(!override.getOverrides().contains(SettingsType.LOOTRULES)){
			this.lootRules.add(base.lootRules);
		}
		this.lootRules.add(override.lootRules);
		if(base.overrides != null || override.overrides != null){
			this.overrides = new ArrayList<SettingsType>();
			if(base.overrides != null) this.overrides.addAll(base.overrides);
			if(override.overrides != null) this.overrides.addAll(override.overrides);
		}
		
		if(override.towerSettings == null){
			this.towerSettings = base.towerSettings;
		} else {
			this.towerSettings = override.towerSettings;
		}
		
		for(int i = 0; i < MAX_NUM_LEVELS; ++i){
			if(override.levels.get(i) == null){
				levels.put(i, new LevelSettings(base.levels.get(i)));
			} else {
				levels.put(i, new LevelSettings(base.levels.get(i), override.levels.get(i)));
			}
		}
	}
	
	public DungeonSettings(DungeonSettings toCopy){
		
		this.depth = toCopy.depth;
		
		this.levels = new HashMap<Integer, LevelSettings>();
		
		this.towerSettings = toCopy.towerSettings;
		this.lootRules = toCopy.lootRules;
		
		for(int i = 0; i < MAX_NUM_LEVELS; ++i){
			this.levels.put(i, new LevelSettings(toCopy.levels.get(i)));
		}
		
		if(toCopy.overrides != null){
			this.overrides = new ArrayList<SettingsType>();
			this.overrides.addAll(toCopy.overrides);
		}
	}

	public String getName(){
		return this.name;
	}
	
	public void setCriteria(SpawnCriteria criteria){
		this.criteria = criteria;
	}
	
	@Override
	public boolean isValid(IWorldEditor editor, Coord pos) {
		
		if(this.criteria == null) return false;
		
		return this.criteria.isValid(editor, pos);
	}

	@Override
	public LevelSettings getLevelSettings(int level) {
		return levels.get(level);
	}
	
	@Override
	public TowerSettings getTower(){
		if(this.towerSettings == null) return new TowerSettings(Tower.ROGUE, Theme.getTheme(Theme.PURPUR));
		
		return this.towerSettings;
	}

	@Override
	public int getNumLevels() {
		
		if(depth < 0) return 0;
		if(depth > MAX_NUM_LEVELS) return MAX_NUM_LEVELS;
		
		return depth;
	}

	@Override
	public LootRuleManager getLootRules() {
		return this.lootRules;
	}

	@Override
	public List<SettingsType> getOverrides() {
		if(this.overrides == null) return new ArrayList<SettingsType>();
		return this.overrides;
	}
	
	@Override
	public String toString(){
		String strg = "";
		if(this.name != null){
			strg += this.name + " : ";
		}
		strg += this.lootRules != null ? this.lootRules.toString() : 0; 
		return strg;
	}
}
