package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.theme.Theme;

public class SettingsTheme extends DungeonSettings{
	
	public SettingsTheme(){
		
		this.towerSettings = new TowerSettings(Tower.ROGUE, Theme.getTheme(Theme.TOWER));
		
		Theme[] themes = {Theme.OAK, Theme.SPRUCE, Theme.CRYPT, Theme.MOSSY, Theme.HELL};
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}

}
