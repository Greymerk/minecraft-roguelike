package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class CatacombSettings implements ICatacombSettings{

	private static final int NUM_LEVELS = 5;
	private String name;
	private Map<Integer, CatacombLevelSettings> levels;
	
	public CatacombSettings(){
		levels = new HashMap<Integer, CatacombLevelSettings>();
		
		for(int i = 0; i < NUM_LEVELS; ++i){
			levels.put(i, new CatacombLevelSettings());
		}
		
		IWeighted<ItemStack> carrot = new WeightedChoice<ItemStack>(new ItemStack(Items.carrot_on_a_stick), 0);
		LootSettings loot = new LootSettings(0);
		loot.set(Loot.SMITHY, carrot);
		levels.get(0).setLoot(loot);
		levels.get(0).setTheme(Theme.getTheme(Theme.OAK));
		levels.get(0).setNumRooms(40);
		levels.get(0).setRange(100);
		SecretFactory secrets = levels.get(0).getSecrets();
		secrets.addRoom(Dungeon.FIREWORK);
		secrets.addRoom(Dungeon.ETHO);
		secrets.addRoom(Dungeon.AVIDYA);
		levels.get(1).setTheme(Theme.getTheme(Theme.SPRUCE));
		levels.get(2).setTheme(Theme.getTheme(Theme.CRYPT));
		levels.get(3).setTheme(Theme.getTheme(Theme.MOSSY));
		levels.get(4).setTheme(Theme.getTheme(Theme.NETHER));
	}
	
	public CatacombSettings(Map<String, CatacombSettings> settings, JsonObject root){
		List<String> inheritList = new ArrayList<String>();
		if(root.has("inherit")){
			JsonArray inherit = root.get("inherit").getAsJsonArray();
			for(JsonElement e : inherit){
				inheritList.add(e.getAsString());
			}
		}
		
		CatacombSettings base = null;
		
		for(String name : inheritList){
			if(settings.containsKey(name)){
				if(base == null){
					base = new CatacombSettings(settings.get(name));
				} else {
					base = new CatacombSettings(base, settings.get(name));
				}
			}
		}
		
		parseJson(base, root);
	}
	
	private void parseJson(CatacombSettings base, JsonObject root){
		
		if(!root.has("levels")) return;
		
		JsonObject levelSet = root.get("levels").getAsJsonObject();
		
		for(int i = 0; i < 5; ++i){
			if(levelSet.has(Integer.toString(i))){
				JsonObject data = levelSet.get(Integer.toString(i)).getAsJsonObject();
				CatacombLevelSettings override = new CatacombLevelSettings(data);
				this.levels.put(i, new CatacombLevelSettings(base.getLevelSettings(i), override));
			} else {
				this.levels.put(i, new CatacombLevelSettings(base.getLevelSettings(i)));
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
		levels = new HashMap<Integer, CatacombLevelSettings>();
		
		for(int i = 0; i < NUM_LEVELS; ++i){
			levels.put(i, new CatacombLevelSettings(toCopy.levels.get(i)));
		}
	}

	public String getName(){
		return this.name;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public CatacombLevelSettings getLevelSettings(int level) {
		return levels.get(level);
	}
}
