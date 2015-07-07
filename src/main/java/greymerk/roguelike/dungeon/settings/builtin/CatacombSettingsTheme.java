package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.settings.CatacombSettings;
import greymerk.roguelike.dungeon.theme.Theme;

public class CatacombSettingsTheme extends CatacombSettings{
	
	public CatacombSettingsTheme(){
		
		Theme[] themes = {Theme.OAK, Theme.OAK, Theme.CRYPT, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}

	
}
