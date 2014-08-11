package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.settings.SpawnCriteria;
import greymerk.roguelike.catacomb.theme.Theme;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class CatacombSettingsJungleTheme extends CatacombSettings{
	
	public CatacombSettingsJungleTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.JUNGLE);
		this.criteria.setBiomeTypes(biomes);
		
		Theme[] themes = {Theme.JUNGLE, Theme.JUNGLE, Theme.MOSSY, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}

	
}
