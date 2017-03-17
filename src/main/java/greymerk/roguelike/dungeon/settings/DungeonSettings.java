package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	protected SettingIdentifier id;
	protected List<SettingIdentifier> inherit;
	protected boolean exclusive;
	protected TowerSettings towerSettings;
	protected Map<Integer, LevelSettings> levels;
	protected SpawnCriteria criteria;
	protected LootRuleManager lootRules;
	protected Set<SettingsType> overrides;
	
	public DungeonSettings(){
		this.inherit = new ArrayList<SettingIdentifier>();
		this.levels = new HashMap<Integer, LevelSettings>();
		this.exclusive = false;
		this.lootRules = new LootRuleManager();
		this.overrides = new HashSet<SettingsType>();
	}
	
	public DungeonSettings(JsonObject root) throws Exception{
		this();
		
		if(root.has("namespace")){
			String name = root.get("name").getAsString();
			String namespace = root.get("namespace").getAsString();
			this.id = new SettingIdentifier(namespace, name);
		} else {
			this.id = new SettingIdentifier(root.get("name").getAsString());
		}
		
		if(root.has("exclusive")) this.exclusive = root.get("exclusive").getAsBoolean();
		if(root.has("criteria")) this.criteria = new SpawnCriteria(root.get("criteria").getAsJsonObject());
		if(root.has("tower")) this.towerSettings = new TowerSettings(root.get("tower"));
		if(root.has("loot_rules")) this.lootRules = new LootRuleManager(root.get("loot_rules"));
		
		if(root.has("overrides")){
			JsonArray overrides = root.get("overrides").getAsJsonArray();
			for(JsonElement e : overrides){
				String type = e.getAsString();
				this.overrides.add(SettingsType.valueOf(type));
			}
		}

		if(root.has("inherit")){
			JsonArray inherit = root.get("inherit").getAsJsonArray();
			for(JsonElement e : inherit){
				this.inherit.add(new SettingIdentifier(e.getAsString()));
			}
		}
		
		if(!root.has("levels")) return;
		
		JsonObject levelSet = root.get("levels").getAsJsonObject();
		for(int i = 0; i < MAX_NUM_LEVELS; ++i){
			LevelSettings setting = new LevelSettings();
			
			if(levelSet.has("all")){
				JsonObject data = levelSet.get("all").getAsJsonObject();
				setting = new LevelSettings(setting, new LevelSettings(data), overrides);
			}
			
			if(levelSet.has(Integer.toString(i))){
				JsonObject data = levelSet.get(Integer.toString(i)).getAsJsonObject();
				setting = new LevelSettings(setting, new LevelSettings(data), overrides);
			}
			
			this.levels.put(i, setting);
		}
	}
	
	
	public DungeonSettings(DungeonSettings base, DungeonSettings other){
		this();
		
		if(other.overrides != null) this.overrides.addAll(other.overrides);
		
		this.lootRules = new LootRuleManager();
		if(!overrides.contains(SettingsType.LOOTRULES)){
			this.lootRules.add(base.lootRules);
		}
		this.lootRules.add(other.lootRules);
		
		for(SettingIdentifier i : other.inherit){
			this.inherit.add(i);
		}
		
		this.exclusive = other.exclusive;
		
		if(overrides.contains(SettingsType.TOWER) && other.towerSettings != null){
			this.towerSettings = new TowerSettings(other.towerSettings);
		} else {
			if(base.towerSettings != null || other.towerSettings != null){
				this.towerSettings = new TowerSettings(base.towerSettings, other.towerSettings);	
			}
		}
		
		for(int i = 0; i < MAX_NUM_LEVELS; ++i){
			levels.put(i, new LevelSettings(base.levels.get(i), other.levels.get(i), overrides));
		}
	}
	
	public DungeonSettings(DungeonSettings toCopy){
		this();
		
		this.towerSettings = toCopy.towerSettings != null ? new TowerSettings(toCopy.towerSettings) : null;
		this.lootRules = toCopy.lootRules;
		
		for(SettingIdentifier i : toCopy.inherit){
			this.inherit.add(i);
		}
		
		this.exclusive = toCopy.exclusive;
		
		for(int i = 0; i < MAX_NUM_LEVELS; ++i){
			LevelSettings level = toCopy.levels.get(i);
			if(level == null){
				this.levels.put(i, new LevelSettings());
			} else {
				this.levels.put(i, new LevelSettings(toCopy.levels.get(i)));
			}
		}
		
		if(toCopy.overrides != null) this.overrides.addAll(toCopy.overrides);
	}

	public List<SettingIdentifier> getInherits(){
		return this.inherit;
	}
	
	public String getNameSpace(){
		return this.id.getNamespace();
	}
	
	public String getName(){
		return this.id.getName();
	}
	
	public void setCriteria(SpawnCriteria criteria){
		this.criteria = criteria;
	}
	
	@Override
	public boolean isValid(IWorldEditor editor, Coord pos) {
		if(this.criteria == null) this.criteria = new SpawnCriteria();
		return this.criteria.isValid(editor.getInfo(pos));
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
		return MAX_NUM_LEVELS;
	}

	@Override
	public LootRuleManager getLootRules() {
		return this.lootRules;
	}

	@Override
	public Set<SettingsType> getOverrides() {
		return this.overrides;
	}

	@Override
	public boolean isExclusive() {
		return this.exclusive;
	}
	
}
