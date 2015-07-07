package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.settings.CatacombSettings;
import greymerk.roguelike.dungeon.settings.CatacombTowerSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.theme.Theme;
import greymerk.roguelike.dungeon.towers.Tower;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class CatacombSettingsTaigaTheme extends CatacombSettings{
	
	public CatacombSettingsTaigaTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.CONIFEROUS);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ROGUE, Theme.getTheme(Theme.SPRUCE));
		
		Theme[] themes = {Theme.SNOW, Theme.SPRUCE, Theme.CRYPT, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}
}
