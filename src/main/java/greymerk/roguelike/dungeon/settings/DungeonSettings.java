package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;


public class DungeonSettings implements ISettings {

  public static final int MAX_NUM_LEVELS = 5;
  protected SettingIdentifier id;
  protected List<SettingIdentifier> inherit;
  protected boolean exclusive;
  protected TowerSettings towerSettings;
  protected Map<Integer, LevelSettings> levels;
  protected SpawnCriteria criteria;
  protected LootRuleManager lootRules;
  protected List<LootTableRule> lootTables;
  protected Set<SettingsType> overrides;

  public DungeonSettings() {
    this.inherit = new ArrayList<>();
    this.levels = new HashMap<>();
    this.exclusive = false;
    this.lootRules = new LootRuleManager();
    this.lootTables = new ArrayList<>();
    this.overrides = new HashSet<>();
  }

  public DungeonSettings(JsonObject root) throws Exception {
    this();

    if (!root.has("name")) {
      throw new Exception("Setting missing name");
    }

    if (root.has("namespace")) {
      String name = root.get("name").getAsString();
      String namespace = root.get("namespace").getAsString();
      this.id = new SettingIdentifier(namespace, name);
    } else {
      this.id = new SettingIdentifier(root.get("name").getAsString());
    }

    if (root.has("exclusive")) {
      this.exclusive = root.get("exclusive").getAsBoolean();
    }
    if (root.has("criteria")) {
      this.criteria = new SpawnCriteria(root.get("criteria").getAsJsonObject());
    }
    if (root.has("tower")) {
      this.towerSettings = new TowerSettings(root.get("tower"));
    }
    if (root.has("loot_rules")) {
      this.lootRules = new LootRuleManager(root.get("loot_rules"));
    }

    if (root.has("overrides")) {
      JsonArray overrides = root.get("overrides").getAsJsonArray();
      for (JsonElement e : overrides) {
        String type = e.getAsString();
        this.overrides.add(SettingsType.valueOf(type));
      }
    }

    if (root.has("inherit")) {
      JsonArray inherit = root.get("inherit").getAsJsonArray();
      for (JsonElement e : inherit) {
        this.inherit.add(new SettingIdentifier(e.getAsString()));
      }
    }

    // set up level settings objects
    for (int i = 0; i < MAX_NUM_LEVELS; ++i) {
      LevelSettings setting = new LevelSettings();
      this.levels.put(i, setting);
    }

    if (root.has("loot_tables")) {
      JsonArray arr = root.get("loot_tables").getAsJsonArray();
      for (JsonElement e : arr) {
        this.lootTables.add(new LootTableRule(e.getAsJsonObject()));
      }
    }

    if (root.has("num_rooms")) {
      JsonArray arr = root.get("num_rooms").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement e = arr.get(i);
        LevelSettings setting = this.levels.get(i);
        setting.setNumRooms(e.getAsInt());
      }
    }

    if (root.has("scatter")) {
      JsonArray arr = root.get("scatter").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement e = arr.get(i);
        LevelSettings setting = this.levels.get(i);
        setting.setScatter(e.getAsInt());
      }
    }

    // parse level layouts
    if (root.has("layouts")) {
      JsonArray layouts = root.get("layouts").getAsJsonArray();
      for (JsonElement e : layouts) {
        JsonObject layout = e.getAsJsonObject();
        if (layout.has("level")) {
          List<Integer> levels = this.parseLevels(layout.get("level"));
          for (Integer level : levels) {
            if (this.levels.containsKey(level)) {
              LevelSettings setting = this.levels.get(level);
              setting.setGenerator(LevelGenerator.valueOf(layout.get("type").getAsString().toUpperCase()));
            }
          }
        }
      }
    }

    // parse level rooms
    if (root.has("rooms")) {
      JsonArray roomArray = root.get("rooms").getAsJsonArray();
      for (int i : this.levels.keySet()) {
        LevelSettings level = this.levels.get(i);
        DungeonFactory rooms = new DungeonFactory();
        SecretFactory secrets = new SecretFactory();

        for (JsonElement e : roomArray) {
          JsonObject room = e.getAsJsonObject();
          if (room.has("level")) {
            List<Integer> levels = this.parseLevels(room.get("level"));
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

        List<Integer> lvls = this.parseLevels(entry.get("level"));

        for (int i : lvls) {
          if (this.levels.containsKey(i)) {
            LevelSettings settings = this.levels.get(i);
            ITheme theme = Theme.create(entry);
            settings.setTheme(theme);
          }
        }
      }
    }

    // parse segments
    if (root.has("segments")) {
      JsonArray arr = root.get("segments").getAsJsonArray();
      for (int lvl : this.levels.keySet()) {
        boolean hasEntry = false;
        SegmentGenerator segments = new SegmentGenerator();
        for (JsonElement e : arr) {
          JsonObject entry = e.getAsJsonObject();
          List<Integer> lvls = this.parseLevels(entry.get("level"));
          if (!lvls.contains(lvl)) {
            continue;
          }

          hasEntry = true;
          segments.add(entry);
        }

        if (hasEntry) {
          this.levels.get(lvl).setSegments(segments);
        }
      }
    }

    // parse spawner settings
    if (root.has("spawners")) {
      JsonArray arr = root.get("spawners").getAsJsonArray();
      for (JsonElement e : arr) {
        JsonObject entry = e.getAsJsonObject();
        List<Integer> lvls = this.parseLevels(entry.get("level"));
        for (int i : lvls) {
          if (this.levels.containsKey(i)) {
            LevelSettings level = this.levels.get(i);
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
        List<Integer> lvls = this.parseLevels(entry.get("level"));
        for (int i : lvls) {
          if (this.levels.containsKey(i)) {
            String name = entry.get("name").getAsString();
            Filter type = Filter.valueOf(name.toUpperCase());
            LevelSettings level = this.levels.get(i);
            level.addFilter(type);
          }
        }
      }
    }
  }

  public DungeonSettings(DungeonSettings base, DungeonSettings other) {
    this();

    if (other.overrides != null) {
      this.overrides.addAll(other.overrides);
    }

    this.lootRules = new LootRuleManager();
    if (!overrides.contains(SettingsType.LOOTRULES)) {
      this.lootRules.add(base.lootRules);
    }
    this.lootRules.add(other.lootRules);

    this.lootTables.addAll(base.lootTables);
    this.lootTables.addAll(other.lootTables);

    this.inherit.addAll(other.inherit);

    this.exclusive = other.exclusive;

    if (overrides.contains(SettingsType.TOWER) && other.towerSettings != null) {
      this.towerSettings = new TowerSettings(other.towerSettings);
    } else {
      if (base.towerSettings != null || other.towerSettings != null) {
        this.towerSettings = new TowerSettings(base.towerSettings, other.towerSettings);
      }
    }

    for (int i = 0; i < MAX_NUM_LEVELS; ++i) {
      levels.put(i, new LevelSettings(base.levels.get(i), other.levels.get(i), overrides));
    }
  }

  public DungeonSettings(DungeonSettings toCopy) {
    this();

    this.towerSettings = toCopy.towerSettings != null ? new TowerSettings(toCopy.towerSettings) : null;
    this.lootRules = toCopy.lootRules;
    this.lootTables.addAll(toCopy.lootTables);

    this.inherit.addAll(toCopy.inherit);

    this.exclusive = toCopy.exclusive;

    for (int i = 0; i < MAX_NUM_LEVELS; ++i) {
      LevelSettings level = toCopy.levels.get(i);
      if (level == null) {
        this.levels.put(i, new LevelSettings());
      } else {
        this.levels.put(i, new LevelSettings(level));
      }
    }

    if (toCopy.overrides != null) {
      this.overrides.addAll(toCopy.overrides);
    }
  }

  private List<Integer> parseLevels(JsonElement e) {

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

  public List<SettingIdentifier> getInherits() {
    return this.inherit != null ? this.inherit : new ArrayList<>();
  }

  public String getNameSpace() {
    return this.id.getNamespace();
  }

  public String getName() {
    return this.id.getName();
  }

  public void setCriteria(SpawnCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public boolean isValid(IWorldEditor editor, Coord pos) {
    if (this.criteria == null) {
      this.criteria = new SpawnCriteria();
    }
    ISpawnContext context = new SpawnContext(editor.getInfo(pos));
    return this.criteria.isValid(context);
  }

  @Override
  public LevelSettings getLevelSettings(int level) {
    return levels.get(level);
  }

  @Override
  public TowerSettings getTower() {
    if (this.towerSettings == null) {
      return new TowerSettings(Tower.ROGUE, Theme.getTheme(Theme.STONE));
    }

    return this.towerSettings;
  }

  @Override
  public int getNumLevels() {
    return MAX_NUM_LEVELS;
  }

  @Override
  public Set<SettingsType> getOverrides() {
    return this.overrides;
  }

  @Override
  public boolean isExclusive() {
    return this.exclusive;
  }

  @Override
  public void processLoot(Random rand, TreasureManager treasure) {
    this.lootRules.process(rand, treasure);

    if (!this.lootTables.isEmpty()) {
      for (LootTableRule table : this.lootTables) {
        table.process(treasure);
      }
    }
  }

  public LootRuleManager getLootRules() {
    return this.lootRules;
  }
}
