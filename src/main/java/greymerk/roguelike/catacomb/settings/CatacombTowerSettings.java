package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.catacomb.tower.Tower;

public class CatacombTowerSettings {

	private Tower tower;
	private ITheme theme;
	
	public CatacombTowerSettings(Tower tower, ITheme theme){
		this.tower = tower;
		this.theme = theme;
	}
	
	public ITheme getTheme(){
		return this.theme;
	}
	
	public Tower getTower(){
		return this.tower;
	}
}
