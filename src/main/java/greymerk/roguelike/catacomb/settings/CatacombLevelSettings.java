package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.IDungeonFactory;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
import greymerk.roguelike.catacomb.segment.ISegmentGenerator;
import greymerk.roguelike.catacomb.segment.SegmentGenerator;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.treasure.loot.LootSettings;

import com.google.gson.JsonObject;

public class CatacombLevelSettings {

	int numRooms;
	int range;
	DungeonFactory rooms;
	SecretFactory secrets;
	ITheme theme;
	SegmentGenerator segments;
	LootSettings loot;

	private static final int NUM_ROOMS = 10;
	private static final int RANGE = 40;
	
	public CatacombLevelSettings(){
		numRooms = NUM_ROOMS;
		range = RANGE;
	}
	
	public CatacombLevelSettings(CatacombLevelSettings toCopy){
		this.numRooms = toCopy.numRooms;
		this.range = toCopy.range;
		this.rooms = toCopy.rooms != null ? new DungeonFactory(toCopy.rooms) : null;
		this.secrets = toCopy.secrets != null ? new SecretFactory(toCopy.secrets) : null;
		this.theme = toCopy.theme != null ? toCopy.theme : null;
		this.segments = toCopy.segments != null ? new SegmentGenerator(toCopy.segments) : null;
		this.loot = toCopy.loot != null ? new LootSettings(toCopy.loot) : null;
	}
	
	public CatacombLevelSettings(CatacombLevelSettings base, CatacombLevelSettings override){
		this.numRooms = override.numRooms != base.numRooms ? override.numRooms : base.numRooms;
		this.range = override.range != base.range ? override.range : base.range;
		
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
	}
	
	public CatacombLevelSettings(JsonObject data){
		this.numRooms = data.has("numRooms") ? data.get("numRooms").getAsInt() : 10;
		this.range = data.has("range") ? data.get("range").getAsInt() : 40;
		this.rooms = data.has("rooms") ? new DungeonFactory(data.get("rooms").getAsJsonArray()) : null;
		this.secrets = data.has("secrets") ? new SecretFactory(data.get("secrets").getAsJsonArray()) : null;
		this.theme = data.has("theme") ? Theme.create(data.get("theme").getAsJsonObject()) : null;
		this.segments = data.has("segments") ? new SegmentGenerator(data.get("segments").getAsJsonObject()) : null;
		this.loot = data.has("loot") ? new LootSettings(data.get("loot").getAsJsonObject()) : null;
	}
	
	public int getNumRooms(){
		return numRooms;
	}
	
	public void setNumRooms(int num){
		numRooms = num;
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

	public int getRange() {
		return this.range;
	}
	
	public void setRange(int range){
		this.range = range;
	}
}
