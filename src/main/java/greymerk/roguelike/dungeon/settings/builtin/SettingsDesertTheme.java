package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import net.minecraft.init.Items;

public class SettingsDesertTheme extends DungeonSettings{
	
	public SettingsDesertTheme(){
		
		this.criteria = new SpawnCriteria();
		this.criteria.addBiome("desert");
		
		this.towerSettings = new TowerSettings(Tower.PYRAMID, Theme.getTheme(Theme.PYRAMID));
		
		this.lootRules = new LootRuleManager();
		for(int i = 0; i < 5; ++i){
			this.lootRules.add(null, new WeightedRandomLoot(Items.gold_ingot, 0, 1, 1 + i, 1), i, false, 6);
		}
		
		Theme[] themes = {Theme.PYRAMID, Theme.SANDSTONE, Theme.SANDSTONERED, Theme.CRYPT, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
			if(i == 0){
				level.setDifficulty(2);
				SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
				segments.add(Segment.DOOR, 10);
				segments.add(Segment.ANKH, 5);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.TOMB, 1);
				level.setSegments(segments);
				
				DungeonFactory factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.PYRAMIDTOMB);
				factory.addRandom(DungeonRoom.PYRAMIDSPAWNER, 5);
				factory.addRandom(DungeonRoom.PYRAMIDCORNER, 3);
				level.setRooms(factory);
				
				SecretFactory secrets = new SecretFactory();
				secrets.addRoom(DungeonRoom.PYRAMIDTOMB);
				level.setSecrets(secrets);
				
				level.setGenerator(LevelGenerator.CLASSIC);
			}
			
			if(i == 1){
				level.setDifficulty(2);
				SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
				segments.add(Segment.SPAWNER, 1);
				segments.add(Segment.DOOR, 10);
				segments.add(Segment.INSET, 5);
				segments.add(Segment.SHELF, 5);
				segments.add(Segment.CHEST, 1);
				segments.add(Segment.ANKH, 1);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.TOMB, 1);
				level.setSegments(segments);
				
				DungeonFactory factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.PYRAMIDTOMB, 2);
				factory.addRandom(DungeonRoom.PYRAMIDSPAWNER, 10);
				factory.addRandom(DungeonRoom.PYRAMIDCORNER, 5);
				level.setRooms(factory);
				
				level.setGenerator(LevelGenerator.CLASSIC);
			}
			
			levels.put(i, level);
		}
	}
}