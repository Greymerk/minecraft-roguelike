package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class DungeonSettingsParser {

  public static DungeonSettings parseFile(String content) throws Exception {
    try {
      return parseDungeonSettings((JsonObject) new JsonParser().parse(content));
    } catch (JsonSyntaxException e) {
      Throwable cause = e.getCause();
      throw new Exception(cause.getMessage());
    } catch (Exception e) {
      throw new Exception("An unknown error occurred while parsing json");
    }
  }

  public static DungeonSettings parseDungeonSettings(JsonObject root) throws Exception {
    DungeonSettings dungeonSettings = new DungeonSettings();
    if (!root.has("name")) {
      throw new Exception("Setting missing name");
    }

    if (root.has("namespace")) {
      String name = root.get("name").getAsString();
      String namespace = root.get("namespace").getAsString();
      dungeonSettings.setId(new SettingIdentifier(namespace, name));
    } else {
      dungeonSettings.setId(new SettingIdentifier(root.get("name").getAsString()));
    }

    if (root.has("exclusive")) {
      dungeonSettings.setExclusive(root.get("exclusive").getAsBoolean());
    }
    if (root.has("criteria")) {
      dungeonSettings.setCriteria(new SpawnCriteria(root.get("criteria").getAsJsonObject()));
    }
    if (root.has("tower")) {
      dungeonSettings.setTowerSettings(new TowerSettings(root.get("tower")));
    }
    if (root.has("loot_rules")) {
      dungeonSettings.setLootRules(new LootRuleManager(root.get("loot_rules")));
    }

    if (root.has("overrides")) {
      JsonArray overrides = root.get("overrides").getAsJsonArray();
      for (JsonElement e : overrides) {
        String type = e.getAsString();
        dungeonSettings.getOverrides().add(SettingsType.valueOf(type));
      }
    }

    if (root.has("inherit")) {
      JsonArray inherit = root.get("inherit").getAsJsonArray();
      for (JsonElement e : inherit) {
        dungeonSettings.getInherit().add(new SettingIdentifier(e.getAsString()));
      }
    }

    // set up level settings objects
    for (int i = 0; i < DungeonSettings.getMaxNumLevels(); ++i) {
      LevelSettings setting = new LevelSettings();
      dungeonSettings.getLevels().put(i, setting);
    }

    if (root.has("loot_tables")) {
      JsonArray arr = root.get("loot_tables").getAsJsonArray();
      for (JsonElement e : arr) {
        dungeonSettings.getLootTables().add(new LootTableRule(e.getAsJsonObject()));
      }
    }

    if (root.has("num_rooms")) {
      JsonArray arr = root.get("num_rooms").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement e = arr.get(i);
        LevelSettings setting = dungeonSettings.getLevels().get(i);
        setting.setNumRooms(e.getAsInt());
      }
    }

    if (root.has("scatter")) {
      JsonArray arr = root.get("scatter").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement e = arr.get(i);
        LevelSettings setting = dungeonSettings.getLevels().get(i);
        setting.setScatter(e.getAsInt());
      }
    }

    // parse level layouts
    if (root.has("layouts")) {
      JsonArray layouts = root.get("layouts").getAsJsonArray();
      for (JsonElement e : layouts) {
        JsonObject layout = e.getAsJsonObject();
        if (layout.has("level")) {
          List<Integer> levels = parseLevels(layout.get("level"));
          for (Integer level : levels) {
            if (dungeonSettings.getLevels().containsKey(level)) {
              LevelSettings setting = dungeonSettings.getLevels().get(level);
              setting.setGenerator(LevelGenerator.valueOf(layout.get("type").getAsString().toUpperCase()));
            }
          }
        }
      }
    }

    // parse level rooms
    if (root.has("rooms")) {
      JsonArray roomArray = root.get("rooms").getAsJsonArray();
      for (int i : dungeonSettings.getLevels().keySet()) {
        LevelSettings level = dungeonSettings.getLevels().get(i);
        DungeonFactory rooms = new DungeonFactory();
        SecretFactory secrets = new SecretFactory();

        for (JsonElement e : roomArray) {
          JsonObject room = e.getAsJsonObject();
          if (room.has("level")) {
            List<Integer> levels = parseLevels(room.get("level"));
            if (levels.contains(i)) {
              if (room.has("type")
                  && room.get("type").getAsString().equals("secret")) {
                secrets.add(room);
                continue;
              }
              rooms.add(room);
            }
          }
        }

        level.setRooms(rooms);
        level.setSecrets(secrets);
      }
    }

    // parse level themes
    if (root.has("themes")) {
      JsonArray arr = root.get("themes").getAsJsonArray();
      for (JsonElement e : arr) {
        JsonObject entry = e.getAsJsonObject();
        if (!entry.has("level")) {
          continue;
        }

        List<Integer> lvls = parseLevels(entry.get("level"));

        for (int i : lvls) {
          if (dungeonSettings.getLevels().containsKey(i)) {
            LevelSettings settings = dungeonSettings.getLevels().get(i);
            ITheme theme = Theme.create(entry);
            settings.setTheme(theme);
          }
        }
      }
    }

    // parse segments
    if (root.has("segments")) {
      JsonArray arr = root.get("segments").getAsJsonArray();
      for (int lvl : dungeonSettings.getLevels().keySet()) {
        boolean hasEntry = false;
        SegmentGenerator segments = new SegmentGenerator();
        for (JsonElement e : arr) {
          JsonObject entry = e.getAsJsonObject();
          List<Integer> lvls = parseLevels(entry.get("level"));
          if (!lvls.contains(lvl)) {
            continue;
          }

          hasEntry = true;
          segments.add(entry);
        }

        if (hasEntry) {
          dungeonSettings.getLevels().get(lvl).setSegments(segments);
        }
      }
    }

    // parse spawner settings
    if (root.has("spawners")) {
      JsonArray arr = root.get("spawners").getAsJsonArray();
      for (JsonElement e : arr) {
        JsonObject entry = e.getAsJsonObject();
        List<Integer> lvls = parseLevels(entry.get("level"));
        for (int i : lvls) {
          if (dungeonSettings.getLevels().containsKey(i)) {
            LevelSettings level = dungeonSettings.getLevels().get(i);
            SpawnerSettings spawners = level.getSpawners();
            spawners.add(entry);
          }
        }
      }
    }

    // parse filters
    if (root.has("filters")) {
      JsonArray arr = root.get("filters").getAsJsonArray();
      for (JsonElement e : arr) {
        JsonObject entry = e.getAsJsonObject();
        List<Integer> lvls = parseLevels(entry.get("level"));
        for (int i : lvls) {
          if (dungeonSettings.getLevels().containsKey(i)) {
            String name = entry.get("name").getAsString();
            Filter type = Filter.valueOf(name.toUpperCase());
            LevelSettings level = dungeonSettings.getLevels().get(i);
            level.addFilter(type);
          }
        }
      }
    }
    return dungeonSettings;
  }

  private static List<Integer> parseLevels(JsonElement e) {

    List<Integer> levels = new ArrayList<>();

    if (e.isJsonArray()) {
      JsonArray arr = e.getAsJsonArray();
      for (JsonElement i : arr) {
        levels.add(i.getAsInt());
      }
      return levels;
    }

    levels.add(e.getAsInt());
    return levels;
  }
}
