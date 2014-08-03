package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.IDungeonFactory;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
import greymerk.roguelike.catacomb.segment.ISegmentGenerator;
import greymerk.roguelike.catacomb.segment.Segment;
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

	public CatacombLevelSettings(){
		numRooms = 10;
		range = 40;
		
		DungeonFactory dungeons;
		dungeons = new DungeonFactory();
		dungeons.addSingle(Dungeon.CAKE);
		dungeons.addSingle(Dungeon.FIRE);
		dungeons.addSingle(Dungeon.SMITH);
		dungeons.addRandom(Dungeon.BRICK, 10);
		dungeons.addRandom(Dungeon.CORNER, 3);
		rooms = dungeons;
		
		secrets = new SecretFactory();
		secrets.addRoom(Dungeon.ENIKO);
		
		theme = Theme.getTheme(Theme.CHECKER);
		
		segments = new SegmentGenerator(Segment.ARCH);
		segments.add(Segment.INSET, 1);
		segments.add(Segment.FIREPLACE, 1);
		loot = new LootSettings(0);

	}
	
	public CatacombLevelSettings(CatacombLevelSettings toCopy){
		this.numRooms = toCopy.numRooms;
		this.range = toCopy.numRooms;
		this.rooms = new DungeonFactory(toCopy.rooms);
		this.secrets = new SecretFactory(toCopy.secrets);
		this.theme = toCopy.theme;
		this.segments = new SegmentGenerator(toCopy.segments);
		this.loot = new LootSettings(toCopy.loot);
	}
	
	public CatacombLevelSettings(CatacombLevelSettings base, CatacombLevelSettings override){
		this.numRooms = override.numRooms;
		this.range = override.range;
		this.rooms = override.rooms == null ? base.rooms : override.rooms;
		this.secrets = override.secrets == null ? base.secrets : override.secrets;
		this.theme = override.theme == null ? base.theme : override.theme;
		this.segments = override.segments == null ? base.segments : override.segments;
		this.loot = new LootSettings(base.loot, override.loot);
	}
	
	public CatacombLevelSettings(JsonObject data){
		this.numRooms = data.has("numRooms") ? data.get("numRooms").getAsInt() : null;
		this.range = data.has("range") ? data.get("range").getAsInt() : null;
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
