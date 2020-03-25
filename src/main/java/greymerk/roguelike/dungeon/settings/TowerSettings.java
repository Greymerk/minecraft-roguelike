package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.ThemeParser;
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

    tower = data.has("type") ? Tower.get(data.get("type").getAsString()) : null;
    theme = data.has("theme") ? ThemeParser.parse(data.get("theme").getAsJsonObject()) : null;

  }

  public TowerSettings(TowerSettings base, TowerSettings override) {
    if (base == null) {
      tower = override.tower;
      theme = override.theme;
      return;
    }

    if (override == null) {
      tower = base.tower;
      theme = base.theme;
      return;
    }

    tower = override.tower == null ? base.tower : override.tower;
    theme = override.theme == null ? base.theme : override.theme;

  }

  public TowerSettings(TowerSettings toCopy) {
    tower = toCopy.tower;
    theme = toCopy.theme;
  }

  public Tower getTower() {
    if (tower == null) {
      return Tower.ROGUE;
    }

    return tower;
  }

  public ITheme getTheme() {
    if (theme == null) {
      return new ThemeTower();
    }

    return theme;
  }


}
