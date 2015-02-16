package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.settings.CatacombTowerSettings;
import greymerk.roguelike.catacomb.settings.SpawnCriteria;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.catacomb.tower.Tower;
import greymerk.roguelike.treasure.loot.LootSettings;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class CatacombSettingsSwampTheme extends CatacombSettings{
	
	public CatacombSettingsSwampTheme(){
		
		this.numLevels = 2;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.SWAMP);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.WITCH, Theme.getTheme(Theme.DARKOAK));
		
		Theme[] themes = {Theme.DARKHALL, Theme.MUDDY, Theme.MOSSY, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
			if(i == 0){
				level.setDifficulty(4);
				level.setLoot(new LootSettings(4));
				
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addRandom(Dungeon.BRICK, 5);
				factory.addRandom(Dungeon.CORNER, 5);
				factory.addRandom(Dungeon.DARKHALL, 1);
				factory.addSingle(Dungeon.LIBRARY);
				factory.addSingle(Dungeon.FIRE);
				level.setRooms(factory);
			}

			levels.put(i, level);
		}
	}
}
