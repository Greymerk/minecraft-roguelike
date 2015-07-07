package greymerk.roguelike.dungeon.settings;

import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.dungeon.theme.Theme;
import greymerk.roguelike.dungeon.theme.ThemeOak;
import greymerk.roguelike.dungeon.towers.Tower;

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
