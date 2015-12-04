package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.treasure.loot.provider.ItemJunk;
import greymerk.roguelike.util.WeightedRandomizer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;

public class SettingsEthoTheme extends DungeonSettings{
	
	public SettingsEthoTheme(){
		
		this.depth = 1;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.FOREST);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.ETHO, Theme.getTheme(Theme.ETHOTOWER));
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(Theme.ETHO));
			level.setDifficulty(3);
			
			if(i == 0){
				level.setScatter(16);
				level.setRange(60);
				level.setNumRooms(10);
				level.setDifficulty(4);
				
				DungeonFactory factory;
			
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.TREETHO);
				factory.addSingle(DungeonRoom.TREETHO);
				factory.addRandom(DungeonRoom.BRICK, 20);
				factory.addRandom(DungeonRoom.ETHO, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				level.setRooms(factory);
				
				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 5);
				segments.add(Segment.WHEAT, 3);
				segments.add(Segment.FLOWERS, 2);
				level.setSegments(segments);
				
				SecretFactory secrets = new SecretFactory();
				List<DungeonRoom> rooms;
				rooms = new ArrayList<DungeonRoom>();
				rooms.add(DungeonRoom.BTEAM);
				rooms.add(DungeonRoom.AVIDYA);
				secrets.addRoom(rooms, 1);
				secrets.addRoom(DungeonRoom.FIREWORK);
				level.setSecrets(secrets);
				
				LootSettings loot = new LootSettings(3);
				WeightedRandomizer<ItemStack> junk = new WeightedRandomizer<ItemStack>();
				junk.add(new ItemJunk(5, 3));
				junk.add(new WeightedRandomLoot(Items.repeater, 1));
				junk.add(new WeightedRandomLoot(Items.redstone, 1));
				junk.add(new WeightedRandomLoot(Items.gunpowder, 1));
				junk.add(new WeightedRandomLoot(Items.comparator, 1));
				loot.set(Loot.JUNK, junk);
			}
			
			levels.put(i, level);
			
		}
	}
}