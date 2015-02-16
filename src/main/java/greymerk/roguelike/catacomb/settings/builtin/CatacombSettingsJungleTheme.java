package greymerk.roguelike.catacomb.settings.builtin;

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

public class CatacombSettingsJungleTheme extends CatacombSettings{
	
	public CatacombSettingsJungleTheme(){
		
		this.numLevels = 2;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.JUNGLE);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.JUNGLE, Theme.getTheme(Theme.JUNGLE));
		
		Theme[] themes = {Theme.JUNGLE, Theme.MOSSY, Theme.MOSSY, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setDifficulty(3);
			
			SegmentGenerator segments;
			segments = new SegmentGenerator(Segment.ARCH);
			segments.add(Segment.DOOR, 8);
			segments.add(Segment.FIREPLACE, 2);
			segments.add(Segment.ARROW, 1);
			segments.add(Segment.CHEST, 1);
			segments.add(Segment.JUNGLE, 3);
			segments.add(Segment.SKULL, 3);
			segments.add(Segment.SPAWNER, 2);
			segments.add(Segment.SILVERFISH, 1);
			level.setSegments(segments);
			
			level.setTheme(Theme.getTheme(themes[i]));
			
			LootSettings loot = new LootSettings(3);
			WeightedRandomizer<ItemStack> junk = new WeightedRandomizer<ItemStack>();
			junk.add(new ItemJunk(3, 3));
			junk.add(new WeightedRandomLoot(Items.emerald, 0, 1, 3, 1));
			junk.add(new WeightedRandomLoot(Items.gold_ingot, 0, 1, 3, 1));
			loot.set(Loot.JUNK, junk);
			level.setLoot(loot);
			
			SecretFactory secrets = new SecretFactory();
			level.setSecrets(secrets);
			
			levels.put(i, level);
		}
	}
}
