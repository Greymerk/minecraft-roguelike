package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.theme.Theme;

public class CatacombSettingsTheme extends CatacombSettings{
	
	public CatacombSettingsTheme(){
		
		Theme[] themes = {Theme.OAK, Theme.SPRUCE, Theme.CRYPT, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}

	
}
