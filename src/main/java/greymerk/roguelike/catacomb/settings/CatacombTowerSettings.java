package greymerk.roguelike.catacomb.settings;

import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.catacomb.theme.ThemeOak;
import greymerk.roguelike.catacomb.tower.Tower;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CatacombTowerSettings {

	private Tower tower;
	private ITheme theme;
	
	public CatacombTowerSettings(Tower tower, ITheme theme){
		this.tower = tower;
		this.theme = theme;
	}
	
	public CatacombTowerSettings(JsonElement e) {

		JsonObject data = e.getAsJsonObject();
		this.tower = data.has("type") ? Tower.valueOf(data.get("type").getAsString()) : Tower.ROGUE;
		this.theme = data.has("theme") ? Theme.create(data.get("theme").getAsJsonObject()) : new ThemeOak();

	}

	public ITheme getTheme(){
		return this.theme;
	}
	
	public Tower getTower(){
		return this.tower;
	}
}
