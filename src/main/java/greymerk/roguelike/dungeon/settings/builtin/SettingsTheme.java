package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.theme.Theme;

public class SettingsTheme extends DungeonSettings{
	
	public SettingsTheme(){
		
		Theme[] themes = {Theme.OAK, Theme.OAK, Theme.CRYPT, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}

	
}
