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
import greymerk.roguelike.util.WeightedRandomizer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;

public class CatacombSettingsPyramidTheme extends CatacombSettings{
	
	public CatacombSettingsPyramidTheme(){
		
		this.depth = 1;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.SANDY);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.PYRAMID, Theme.getTheme(Theme.PYRAMID));
		
		SegmentGenerator segments;
		segments = new SegmentGenerator(Segment.SQUAREARCH);
		segments.add(Segment.INSET, 6);
		segments.add(Segment.SHELF, 6);
		segments.add(Segment.ANKH, 2);
		segments.add(Segment.CHEST, 1);
		segments.add(Segment.SKULL, 2);
		segments.add(Segment.TOMB, 3);
		
		DungeonFactory factory;
		factory = new DungeonFactory();
		factory.addSingle(Dungeon.PYRAMIDTOMB);
		factory.addSingle(Dungeon.PYRAMIDTOMB);
		factory.addRandom(Dungeon.BRICK, 3);
		factory.addRandom(Dungeon.CORNER, 3);
		
		CatacombLevelSettings level = new CatacombLevelSettings();
		level.setTheme(Theme.getTheme(Theme.SANDSTONE));
		level.setSegments(segments);
		level.setRooms(factory);
		level.setDifficulty(3);
		
		SecretFactory secrets = new SecretFactory();
		level.setSecrets(secrets);
		
		LootSettings loot = new LootSettings(3);
		WeightedRandomizer<ItemStack> junk = new WeightedRandomizer<ItemStack>();
		junk.add(new WeightedRandomLoot(Items.rotten_flesh, 20));
		junk.add(new WeightedRandomLoot(Items.bone, 20));
		junk.add(new WeightedRandomLoot(Items.dye, 4, 1, 5, 10));
		junk.add(new WeightedRandomLoot(Items.gold_nugget, 10));
		junk.add(new WeightedRandomLoot(Items.ender_eye, 5));
		junk.add(new WeightedRandomLoot(Items.golden_hoe, 3));
		junk.add(new WeightedRandomLoot(Items.emerald, 3));
		junk.add(new WeightedRandomLoot(Items.diamond, 3));
		loot.set(Loot.JUNK, junk);
		level.setLoot(loot);
		
		for(int i = 0; i < 5; ++i){
			levels.put(i, level);
		}
	}
}
