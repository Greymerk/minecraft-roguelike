package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.settings.CatacombTowerSettings;
import greymerk.roguelike.catacomb.settings.SpawnCriteria;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.catacomb.tower.Tower;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class CatacombSettingsForestTheme extends CatacombSettings{
	
	public CatacombSettingsForestTheme(){
		
		this.depth = 1;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.FOREST);
		this.criteria.setWeight(7);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ROGUE, Theme.getTheme(Theme.ETHO));
		
		Theme[] themes = {Theme.ETHO, Theme.ETHOTOWER, Theme.CRYPT, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			
			if(i == 0){
				DungeonFactory factory = new DungeonFactory();
				factory.addSingle(Dungeon.CAKE);
				factory.addSingle(Dungeon.DARKHALL);
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
			}
			
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}
}