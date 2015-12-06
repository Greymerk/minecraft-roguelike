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
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ILoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootRule;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public class DungeonSettings implements ISettings{
	
	public static final int MAX_NUM_LEVELS = 5;
	private String name;
	protected TowerSettings towerSettings;
	protected Map<Integer, LevelSettings> levels;
	protected SpawnCriteria criteria;
	protected int depth;
	
	public DungeonSettings(){
		this.levels = new HashMap<Integer, LevelSettings>();
		this.depth = 0;
	}
	
	public DungeonSettings(Map<String, DungeonSettings> settings, JsonObject root) throws Exception{
		
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
			this.towerSettings = new TowerSettings(root.get("tower"));
		}
		
		levels = new HashMap<Integer, LevelSettings>();
		
		List<String> inheritList = new ArrayList<String>();
		if(root.has("inherit")){
			JsonArray inherit = root.get("inherit").getAsJsonArray();
			for(JsonElement e : inherit){
				inheritList.add(e.getAsString());
			}
		}
		
		DungeonSettings base = new SettingsBlank();
		
		for(String name : inheritList){
			if(settings.containsKey(name)){
				base = new DungeonSettings(base, settings.get(name));
			} else {
				throw new Exception(name + " must be previously defined in order to be inherited");
			}
		}
		
		parseJson(base, root);
	}
	
	private void parseJson(DungeonSettings base, JsonObject root){
		
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
			
			LevelSettings setting = new LevelSettings();
			
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
		
		levels = new HashMap<Integer, LevelSettings>();
		
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
		
		for(int i = 0; i < MAX_NUM_LEVELS; ++i){
			this.levels.put(i, new LevelSettings(toCopy.levels.get(i)));
		}
	}

	public String getName(){
		return this.name;
	}
	
	public void setCriteria(SpawnCriteria criteria){
		this.criteria = criteria;
	}
	
	@Override
	public boolean isValid(WorldEditor editor, Coord pos) {
		
		if(this.criteria == null) return false;
		
		return this.criteria.isValid(editor, pos);
	}

	@Override
	public LevelSettings getLevelSettings(int level) {
		return levels.get(level);
	}
	
	@Override
	public TowerSettings getTower(){
		if(this.towerSettings == null) return new TowerSettings(Tower.ROGUE, Theme.getTheme(Theme.TOWER));
		
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
		LootRuleManager lootRules = new LootRuleManager();
		ILoot loot = Loot.getLoot();
		lootRules.add(new LootRule(Treasure.STARTER, loot.get(Loot.WEAPON, 0),  0, true, 2));
		lootRules.add(new LootRule(Treasure.STARTER, loot.get(Loot.FOOD, 0),  0, true, 2));
		lootRules.add(new LootRule(Treasure.STARTER, loot.get(Loot.WEAPON, 0),  0, true, 2));
		lootRules.add(new LootRule(Treasure.STARTER, new ItemSpecialty(0, 0, Equipment.LEGS, Quality.WOOD), 0, true, 2));
		for(int i = 0; i < 5; ++i){
			lootRules.add(new LootRule(Treasure.ARMOUR, loot.get(Loot.POTION, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.ARMOUR, loot.get(Loot.ARMOUR, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.ARMOUR, loot.get(Loot.FOOD, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.WEAPONS, loot.get(Loot.POTION, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.WEAPONS, loot.get(Loot.WEAPON, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.WEAPONS, loot.get(Loot.FOOD, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.BLOCKS, loot.get(Loot.BLOCK, i),  i, true, 6));
			lootRules.add(new LootRule(Treasure.WEAPONS, loot.get(Loot.FOOD, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBONUS, i),  i, true, 2));
			lootRules.add(new LootRule(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBOOK, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.FOOD, loot.get(Loot.FOOD, i),  i, true, 8));
			lootRules.add(new LootRule(Treasure.ORE, loot.get(Loot.ORE, i),  i, true, 3));
			lootRules.add(new LootRule(Treasure.POTIONS, loot.get(Loot.POTION, i),  i, true, 3));
			lootRules.add(new LootRule(Treasure.TOOLS, loot.get(Loot.ORE, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.TOOLS, loot.get(Loot.TOOL, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.TOOLS, loot.get(Loot.BLOCK, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.SUPPLIES, loot.get(Loot.SUPPLY, i),  i, true, 6));
			lootRules.add(new LootRule(Treasure.SMITH, loot.get(Loot.ORE, i),  i, true, 2));
			lootRules.add(new LootRule(Treasure.SMITH, loot.get(Loot.SMITHY, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.MUSIC, loot.get(Loot.MUSIC, i),  i, true, 1));
			lootRules.add(new LootRule(Treasure.REWARD, loot.get(Loot.REWARD, i),  i, true, 2));
			lootRules.add(new LootRule(null, loot.get(Loot.JUNK, i),  i, true, 6));
			lootRules.add(new LootRule(null, loot.get(Loot.SPECIAL, i),  i, false, 3));
		}
		return lootRules;
	}
}
