package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.ThemeTower;

public class TowerSettings {

  private Tower tower;
  private ITheme theme;

  public TowerSettings(Tower tower, Theme theme) {
    this.tower = tower;
    this.theme = theme.getThemeBase();
  }

  public TowerSettings(JsonElement e) throws Exception {

    JsonObject data = e.getAsJsonObject();

    this.tower = data.has("type") ? Tower.get(data.get("type").getAsString()) : null;
    this.theme = data.has("theme") ? Theme.create(data.get("theme").getAsJsonObject()) : null;

  }

  public TowerSettings(TowerSettings base, TowerSettings override) {
    if (base == null) {
      this.tower = override.tower;
      this.theme = override.theme;
      return;
    }

    if (override == null) {
      this.tower = base.tower;
      this.theme = base.theme;
      return;
    }

    this.tower = override.tower == null ? base.tower : override.tower;
    this.theme = override.theme == null ? base.theme : override.theme;

  }

  public TowerSettings(TowerSettings toCopy) {
    this.tower = toCopy.tower;
    this.theme = toCopy.theme;
  }

  public Tower getTower() {
    if (this.tower == null) {
      return Tower.ROGUE;
    }

    return this.tower;
  }

  public ITheme getTheme() {
    if (this.theme == null) {
      return new ThemeTower();
    }

    return this.theme;
  }


}
