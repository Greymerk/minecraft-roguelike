package greymerk.roguelike.dungeon.settings;

import java.util.Set;

import com.google.gson.JsonObject;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.IDungeonFactory;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.ISegmentGenerator;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class LevelSettings {

	private int numRooms;
	private int range;
	private int scatter;
	private int levelDifficulty;
	private DungeonFactory rooms;
	private SecretFactory secrets;
	private ITheme theme;
	private SegmentGenerator segments;
	private SpawnerSettings spawners;
	private LevelGenerator generator;
	
	public LevelSettings(){
		numRooms = RogueConfig.getInt(RogueConfig.LEVELMAXROOMS);
		range = RogueConfig.getInt(RogueConfig.LEVELRANGE);
		scatter = RogueConfig.getInt(RogueConfig.LEVELSCATTER);
		rooms = new DungeonFactory();
		secrets = new SecretFactory();
		levelDifficulty = -1;
	}
	
	public LevelSettings(LevelSettings toCopy){
		init(toCopy);
	}
	
	public LevelSettings(LevelSettings base, LevelSettings other, Set<SettingsType> overrides){
		this();
		
		if(base == null && other == null){
			return;
		}
		
		if(base == null && other != null){
			init(other); return;
		}
		
		if(base != null && other == null){
			init(base); return;
		}
		
		this.numRooms = other.numRooms != base.numRooms && other.numRooms != RogueConfig.getInt(RogueConfig.LEVELMAXROOMS) 
				? other.numRooms 
				: base.numRooms;
		
		this.range = other.range != base.range && other.range != RogueConfig.getInt(RogueConfig.LEVELRANGE) ? other.range : base.range;
		this.scatter = other.scatter != base.scatter && other.scatter != RogueConfig.getInt(RogueConfig.LEVELSCATTER) ? other.scatter : base.scatter;
		
		this.levelDifficulty = (base.levelDifficulty != other.levelDifficulty && other.levelDifficulty != -1) || base.levelDifficulty == -1 ? other.levelDifficulty : base.levelDifficulty;
		
		if(overrides.contains(SettingsType.ROOMS)){
			this.rooms = new DungeonFactory(base.rooms);
		} else {
			this.rooms = new DungeonFactory(base.rooms, other.rooms);			
		}
		
		if(overrides.contains(SettingsType.SECRETS)){
			this.secrets = new SecretFactory(other.secrets);
		} else {
			this.secrets = new SecretFactory(base.secrets, other.secrets);
		}

		if(other.theme != null){
			if(base.theme == null || overrides.contains(SettingsType.THEMES)){
				this.theme = Theme.create(other.theme);
			} else {
				this.theme = Theme.create(base.theme, other.theme);
			}
		} else if(base.theme != null){
			this.theme = Theme.create(base.theme);
		}
		
		if(base.segments != null || other.segments != null){
			this.segments = other.segments == null ? new SegmentGenerator(base.segments) : new SegmentGenerator(other.segments);
		}
		
		this.spawners = other.spawners == null ? base.spawners : other.spawners;
		this.generator = other.generator == null? base.generator : other.generator;
	}
	
	public LevelSettings(JsonObject data) throws Exception{
		this();
		this.numRooms = data.has("numRooms") ? data.get("numRooms").getAsInt() : RogueConfig.getInt(RogueConfig.LEVELMAXROOMS);
		this.range = data.has("range") ? data.get("range").getAsInt() : RogueConfig.getInt(RogueConfig.LEVELRANGE);
		this.scatter = data.has("scatter") ? data.get("scatter").getAsInt() : RogueConfig.getInt(RogueConfig.LEVELSCATTER);
		this.levelDifficulty = data.has("diff") ? data.get("diff").getAsInt() : -1;
		if(data.has("rooms")) this.rooms = new DungeonFactory(data.get("rooms").getAsJsonArray());
		if(data.has("secrets")) this.secrets = new SecretFactory(data.get("secrets").getAsJsonArray());
		this.theme = data.has("theme") ? Theme.create(data.get("theme").getAsJsonObject()) : null;
		this.segments = data.has("segments") ? new SegmentGenerator(data.get("segments").getAsJsonObject()) : null;
		this.spawners = data.has("spawners") ? new SpawnerSettings(data.get("spawners").getAsJsonObject()) : null;
		this.generator = data.has("generator") ? LevelGenerator.valueOf(data.get("generator").getAsString()) : null;
	}
	
	public LevelGenerator getGenerator(){
		
		if(this.generator == null){
			return LevelGenerator.CLASSIC;
		}
		
		return this.generator;
	}
	
	public void setGenerator(LevelGenerator type){
		this.generator = type;
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
	
	private void init(LevelSettings toCopy){
		this.numRooms = toCopy.numRooms;
		this.range = toCopy.range;
		this.scatter = toCopy.scatter;
		this.levelDifficulty = toCopy.levelDifficulty;
		this.rooms = toCopy.rooms != null ? new DungeonFactory(toCopy.rooms) : null;
		this.secrets = toCopy.secrets != null ? new SecretFactory(toCopy.secrets) : null;
		this.theme = toCopy.theme != null ? toCopy.theme : null;
		this.segments = toCopy.segments != null ? new SegmentGenerator(toCopy.segments) : null;
		this.spawners = toCopy.spawners;
		this.generator = toCopy.generator;
	}
	
	@Override
	public boolean equals(Object o){
		LevelSettings other = (LevelSettings) o;
		if(other.generator != this.generator) return false;
		if(!this.secrets.equals(other.secrets)) return false;
		if(!this.rooms.equals(other.rooms)) return false;
		return true;
	}
}
