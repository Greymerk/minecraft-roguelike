package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.catacomb.segment.SegmentGenerator;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.treasure.loot.LootSettings;

import java.util.HashMap;

public class CatacombSettingsDefault extends CatacombSettings{
	
	public CatacombSettingsDefault(){
		levels = new HashMap<Integer, CatacombLevelSettings>();
		
		CatacombLevelSettings level0 = new CatacombLevelSettings();
		
		DungeonFactory rooms = new DungeonFactory();
		rooms.addSingle(Dungeon.CAKE);
		rooms.addSingle(Dungeon.FIRE);
		rooms.addSingle(Dungeon.SMITH);
		rooms.addRandom(Dungeon.BRICK, 10);
		rooms.addRandom(Dungeon.CORNER, 3);
		level0.setRooms(rooms);
		
		SecretFactory secrets = new SecretFactory();
		secrets.addRoom(Dungeon.ENIKO);
		secrets.addRoom(Dungeon.BTEAM);
		level0.setSecrets(secrets);
		
		SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
		segments.add(Segment.BOOKS, 1);
		segments.add(Segment.DOOR, 1);
		segments.add(Segment.FIREPLACE, 1);
		level0.setSegments(segments);
		
		LootSettings loot = new LootSettings(0);
		level0.setLoot(loot);
		
		level0.setTheme(Theme.getTheme(Theme.OAK));
		
		levels.put(0, level0);
		
		CatacombLevelSettings level1 = new CatacombLevelSettings(level0);
		level1.setTheme(Theme.getTheme(Theme.SPRUCE));
		levels.put(1, level1);
		CatacombLevelSettings level2 = new CatacombLevelSettings(level0);
		level2.setTheme(Theme.getTheme(Theme.CRYPT));
		levels.put(2, level2);
		CatacombLevelSettings level3 = new CatacombLevelSettings(level0);
		level3.setTheme(Theme.getTheme(Theme.MOSSY));
		levels.put(3, level3);
		CatacombLevelSettings level4 = new CatacombLevelSettings(level0);
		level4.setTheme(Theme.getTheme(Theme.NETHER));
		levels.put(4, level4);
	}
}
