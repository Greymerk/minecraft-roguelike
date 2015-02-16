package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.catacomb.segment.SegmentGenerator;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.settings.CatacombTowerSettings;
import greymerk.roguelike.catacomb.settings.SpawnCriteria;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.catacomb.tower.Tower;
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

public class CatacombSettingsEthoTheme extends CatacombSettings{
	
	public CatacombSettingsEthoTheme(){
		
		this.numLevels = 1;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.FOREST);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ETHO, Theme.getTheme(Theme.ETHOTOWER));
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(Theme.ETHO));
			level.setDifficulty(3);
			
			if(i == 0){
				level.setScatter(16);
				level.setRange(60);
				level.setNumRooms(10);
				level.setDifficulty(4);
				level.setLoot(new LootSettings(4));
				
				DungeonFactory factory;
			
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.TREETHO);
				factory.addSingle(Dungeon.TREETHO);
				factory.addRandom(Dungeon.BRICK, 20);
				factory.addRandom(Dungeon.ETHO, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
				
				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 5);
				segments.add(Segment.WHEAT, 3);
				segments.add(Segment.FLOWERS, 2);
				level.setSegments(segments);
				
				SecretFactory secrets = new SecretFactory();
				List<Dungeon> rooms;
				rooms = new ArrayList<Dungeon>();
				rooms.add(Dungeon.BTEAM);
				rooms.add(Dungeon.AVIDYA);
				secrets.addRoom(rooms, 1);
				secrets.addRoom(Dungeon.FIREWORK);
				level.setSecrets(secrets);
				
				LootSettings loot = new LootSettings(3);
				WeightedRandomizer<ItemStack> junk = new WeightedRandomizer<ItemStack>();
				junk.add(new ItemJunk(5, 3));
				junk.add(new WeightedRandomLoot(Items.repeater, 1));
				junk.add(new WeightedRandomLoot(Items.redstone, 1));
				junk.add(new WeightedRandomLoot(Items.gunpowder, 1));
				junk.add(new WeightedRandomLoot(Items.comparator, 1));
				loot.set(Loot.JUNK, junk);
				level.setLoot(loot);
			}
			
			levels.put(i, level);
			
		}
	}
}