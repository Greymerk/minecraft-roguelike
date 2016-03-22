package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;

public class SettingsMesaTheme extends DungeonSettings{
	
	public SettingsMesaTheme(){
		
		this.criteria = new SpawnCriteria();
		this.criteria.addBiome("mesa");
		
		this.towerSettings = new TowerSettings(Tower.ETHO, Theme.getTheme(Theme.ETHOTOWER));
		
		Theme[] themes = {Theme.ETHOTOWER, Theme.ETHOTOWER, Theme.CRYPT, Theme.CRYPT, Theme.NETHER};
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}
}
