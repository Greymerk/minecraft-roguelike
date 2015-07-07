package greymerk.roguelike.dungeon.settings;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.IDungeonFactory;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.ISegmentGenerator;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.dungeon.theme.Theme;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.SpawnerSettings;

import com.google.gson.JsonObject;

public class CatacombLevelSettings {

	int numRooms;
	int range;
	int scatter;
	int levelDifficulty;
	DungeonFactory rooms;
	SecretFactory secrets;
	ITheme theme;
	SegmentGenerator segments;
	LootSettings loot;
	SpawnerSettings spawners;
	
	public CatacombLevelSettings(){
		numRooms = RogueConfig.getInt(RogueConfig.LEVELMAXROOMS);
		range = RogueConfig.getInt(RogueConfig.LEVELRANGE);
		scatter = RogueConfig.getInt(RogueConfig.LEVELSCATTER);
		levelDifficulty = -1;
	}
	
	public CatacombLevelSettings(CatacombLevelSettings toCopy){
		this.numRooms = toCopy.numRooms;
		this.range = toCopy.range;
		this.scatter = toCopy.scatter;
		this.levelDifficulty = toCopy.levelDifficulty;
		this.rooms = toCopy.rooms != null ? new DungeonFactory(toCopy.rooms) : null;
		this.secrets = toCopy.secrets != null ? new SecretFactory(toCopy.secrets) : null;
		this.theme = toCopy.theme != null ? toCopy.theme : null;
		this.segments = toCopy.segments != null ? new SegmentGenerator(toCopy.segments) : null;
		this.loot = toCopy.loot != null ? new LootSettings(toCopy.loot) : null;
		this.spawners = toCopy.spawners != null ? new SpawnerSettings(toCopy.spawners) : null;
	}
	
	public CatacombLevelSettings(CatacombLevelSettings base, CatacombLevelSettings override){
		
		this.numRooms = override.numRooms != base.numRooms && override.numRooms != RogueConfig.getInt(RogueConfig.LEVELMAXROOMS) ? override.numRooms : base.numRooms;
		this.range = override.range != base.range && override.range != RogueConfig.getInt(RogueConfig.LEVELRANGE) ? override.range : base.range;
		this.scatter = override.scatter != base.scatter && override.scatter != RogueConfig.getInt(RogueConfig.LEVELSCATTER) ? override.scatter : base.scatter;
		
		this.levelDifficulty = (base.levelDifficulty != override.levelDifficulty && override.levelDifficulty != -1) || base.levelDifficulty == -1 ? override.levelDifficulty : base.levelDifficulty;
		
		if(base.rooms != null || override.rooms != null){
			this.rooms = override.rooms == null ? new DungeonFactory(base.rooms) : new DungeonFactory(override.rooms);
		}
		
		if(base.secrets != null || override.secrets != null){
			this.secrets = override.secrets == null ? new SecretFactory(base.secrets) : new SecretFactory(override.secrets);
		}
		
		this.theme = override.theme == null ? base.theme : override.theme;
		
		if(base.segments != null || override.segments != null){
			this.segments = override.segments == null ? new SegmentGenerator(base.segments) : new SegmentGenerator(override.segments);
		}
		
		this.loot = new LootSettings(base.loot, override.loot);
		
		this.spawners = new SpawnerSettings(base.spawners, override.spawners);
	}
	
	public CatacombLevelSettings(JsonObject data){
		this.numRooms = data.has("numRooms") ? data.get("numRooms").getAsInt() : RogueConfig.getInt(RogueConfig.LEVELMAXROOMS);
		this.range = data.has("range") ? data.get("range").getAsInt() : RogueConfig.getInt(RogueConfig.LEVELRANGE);
		this.scatter = data.has("scatter") ? data.get("scatter").getAsInt() : RogueConfig.getInt(RogueConfig.LEVELSCATTER);
		this.levelDifficulty = data.has("diff") ? data.get("diff").getAsInt() : -1;
		this.rooms = data.has("rooms") ? new DungeonFactory(data.get("rooms").getAsJsonArray()) : null;
		this.secrets = data.has("secrets") ? new SecretFactory(data.get("secrets").getAsJsonArray()) : null;
		this.theme = data.has("theme") ? Theme.create(data.get("theme").getAsJsonObject()) : null;
		this.segments = data.has("segments") ? new SegmentGenerator(data.get("segments").getAsJsonObject()) : null;
		this.loot = data.has("loot") ? new LootSettings(data.get("loot").getAsJsonObject()) : null;
		this.spawners = data.has("spawners") ? new SpawnerSettings(data.get("spawners").getAsJsonObject()) : null;
	}
	
	public int getScatter(){
		return this.scatter;
	}
	
	public void setScatter(int scatter){
		this.scatter = scatter;
	}
	
	public int getNumRooms(){
		return numRooms;
	}
	
	public void setNumRooms(int num){
		numRooms = num;
	}
	
	public int getDifficulty(Coord pos){
		
		if(this.levelDifficulty == -1){
			return Dungeon.getLevel(pos.getY());
		}
		
		return levelDifficulty;
	}
	
	public void setDifficulty(int num){
		this.levelDifficulty = num;
	}
	
	public IDungeonFactory getRooms(){
		return rooms;
	}
	
	public void setRooms(DungeonFactory rooms){
		this.rooms = rooms;
	}
	
	public SecretFactory getSecrets(){
		return secrets;
	}
	
	public void setSecrets(SecretFactory secrets){
		this.secrets = secrets;
	}
	
	public ISegmentGenerator getSegments(){
		return segments;
	}
	
	public void setSegments(SegmentGenerator segments){
		this.segments = segments;
	}
	
	public ITheme getTheme(){
		return theme;
	}
	
	public void setTheme(ITheme theme){
		this.theme = theme;
	}
	
	public LootSettings getLoot(){
		return loot;
	}
	
	public void setLoot(LootSettings loot){
		this.loot = loot;
	}
	
	public SpawnerSettings getSpawners(){
		return this.spawners;
	}
	
	public void setSpawners(SpawnerSettings spawners){
		this.spawners = spawners;
	}

	public int getRange() {
		return this.range;
	}
	
	public void setRange(int range){
		this.range = range;
	}
}
