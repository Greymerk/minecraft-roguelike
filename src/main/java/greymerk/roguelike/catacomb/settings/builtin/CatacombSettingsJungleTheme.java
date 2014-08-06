package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.settings.SpawnCriteria;
import greymerk.roguelike.catacomb.theme.Theme;

import java.util.ArrayList;
import java.util.List;

public class CatacombSettingsJungleTheme extends CatacombSettings{
	
	public CatacombSettingsJungleTheme(){
		
		this.criteria = new SpawnCriteria();
		List<String> biomes = new ArrayList<String>();
		biomes.add("Jungle");
		biomes.add("Jungle Hills");
		biomes.add("Swampland");
		this.criteria.setbiomes(biomes);
		
		Theme[] themes = {Theme.JUNGLE, Theme.JUNGLE, Theme.MOSSY, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}

	
}
