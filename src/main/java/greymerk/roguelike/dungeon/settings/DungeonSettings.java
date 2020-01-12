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

  private static final int MAX_NUM_LEVELS = 5;
  private SettingIdentifier id;
  private List<SettingIdentifier> inherit = new ArrayList<>();
  private boolean exclusive;
  private TowerSettings towerSettings;
  private Map<Integer, LevelSettings> levels = new HashMap<>();
  private SpawnCriteria criteria;
  private LootRuleManager lootRules;
  private List<LootTableRule> lootTables = new ArrayList<>();
  private Set<SettingsType> overrides = new HashSet<>();

  public DungeonSettings() {
    setExclusive(false);
    setCriteria(new SpawnCriteria());
    setLootRules(new LootRuleManager());
  }

  public DungeonSettings(String id) {
    this(new SettingIdentifier(id));
  }

  public DungeonSettings(SettingIdentifier id) {
    this();
    setId(id);
  }

  public DungeonSettings(JsonObject root) throws Exception {
    this();

    if (!root.has("name")) {
      throw new Exception("Setting missing name");
    }

    if (root.has("namespace")) {
      String name = root.get("name").getAsString();
      String namespace = root.get("namespace").getAsString();
      setId(new SettingIdentifier(namespace, name));
    } else {
      setId(new SettingIdentifier(root.get("name").getAsString()));
    }

    if (root.has("exclusive")) {
      setExclusive(root.get("exclusive").getAsBoolean());
    }
    if (root.has("criteria")) {
      setCriteria(new SpawnCriteria(root.get("criteria").getAsJsonObject()));
    }
    if (root.has("tower")) {
      setTowerSettings(new TowerSettings(root.get("tower")));
    }
    if (root.has("loot_rules")) {
      setLootRules(new LootRuleManager(root.get("loot_rules")));
    }

    if (root.has("overrides")) {
      JsonArray overrides = root.get("overrides").getAsJsonArray();
      for (JsonElement e : overrides) {
        String type = e.getAsString();
        getOverrides().add(SettingsType.valueOf(type));
      }
    }

    if (root.has("inherit")) {
      JsonArray inherit = root.get("inherit").getAsJsonArray();
      for (JsonElement e : inherit) {
        getInherit().add(new SettingIdentifier(e.getAsString()));
      }
    }

    // set up level settings objects
    for (int i = 0; i < getMaxNumLevels(); ++i) {
      LevelSettings setting = new LevelSettings();
      getLevels().put(i, setting);
    }

    if (root.has("loot_tables")) {
      JsonArray arr = root.get("loot_tables").getAsJsonArray();
      for (JsonElement e : arr) {
        getLootTables().add(new LootTableRule(e.getAsJsonObject()));
      }
    }

    if (root.has("num_rooms")) {
      JsonArray arr = root.get("num_rooms").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement e = arr.get(i);
        LevelSettings setting = getLevels().get(i);
        setting.setNumRooms(e.getAsInt());
      }
    }

    if (root.has("scatter")) {
      JsonArray arr = root.get("scatter").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement e = arr.get(i);
        LevelSettings setting = getLevels().get(i);
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
            if (getLevels().containsKey(level)) {
              LevelSettings setting = getLevels().get(level);
              setting.setGenerator(LevelGenerator.valueOf(layout.get("type").getAsString().toUpperCase()));
            }
          }
        }
      }
    }

    // parse level rooms
    if (root.has("rooms")) {
      JsonArray roomArray = root.get("rooms").getAsJsonArray();
      for (int i : getLevels().keySet()) {
        LevelSettings level = getLevels().get(i);
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
          if (getLevels().containsKey(i)) {
            LevelSettings settings = getLevels().get(i);
            ITheme theme = Theme.create(entry);
            settings.setTheme(theme);
          }
        }
      }
    }

    // parse segments
    if (root.has("segments")) {
      JsonArray arr = root.get("segments").getAsJsonArray();
      for (int lvl : getLevels().keySet()) {
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
          getLevels().get(lvl).setSegments(segments);
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
          if (getLevels().containsKey(i)) {
            LevelSettings level = getLevels().get(i);
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
          if (getLevels().containsKey(i)) {
            String name = entry.get("name").getAsString();
            Filter type = Filter.valueOf(name.toUpperCase());
            LevelSettings level = getLevels().get(i);
            level.addFilter(type);
          }
        }
      }
    }
  }

  public DungeonSettings(DungeonSettings base, DungeonSettings other) {
    this();

    if (other.getOverrides() != null) {
      getOverrides().addAll(other.getOverrides());
    }

    setLootRules(new LootRuleManager());
    if (!getOverrides().contains(SettingsType.LOOTRULES)) {
      getLootRules().add(base.getLootRules());
    }
    getLootRules().add(other.getLootRules());

    getLootTables().addAll(base.getLootTables());
    getLootTables().addAll(other.getLootTables());

    getInherit().addAll(other.getInherit());

    setExclusive(other.isExclusive());

    if (getOverrides().contains(SettingsType.TOWER) && other.getTowerSettings() != null) {
      setTowerSettings(new TowerSettings(other.getTowerSettings()));
    } else {
      if (base.getTowerSettings() != null || other.getTowerSettings() != null) {
        setTowerSettings(new TowerSettings(base.getTowerSettings(), other.getTowerSettings()));
      }
    }

    for (int i = 0; i < getMaxNumLevels(); ++i) {
      getLevels().put(i, new LevelSettings(base.getLevels().get(i), other.getLevels().get(i), getOverrides()));
    }
  }

  public DungeonSettings(DungeonSettings toCopy) {
    this();

    setTowerSettings(toCopy.getTowerSettings() != null ? new TowerSettings(toCopy.getTowerSettings()) : null);
    setLootRules(toCopy.getLootRules());
    getLootTables().addAll(toCopy.getLootTables());

    getInherit().addAll(toCopy.getInherit());

    setExclusive(toCopy.isExclusive());

    for (int i = 0; i < getMaxNumLevels(); ++i) {
      LevelSettings level = toCopy.getLevels().get(i);
      if (level == null) {
        getLevels().put(i, new LevelSettings());
      } else {
        getLevels().put(i, new LevelSettings(level));
      }
    }

    if (toCopy.getOverrides() != null) {
      getOverrides().addAll(toCopy.getOverrides());
    }
  }

  public static int getMaxNumLevels() {
    return MAX_NUM_LEVELS;
  }

  public SettingIdentifier getId() {
    return id;
  }

  public void setId(SettingIdentifier id) {
    this.id = id;
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
    return getInherit() != null ? getInherit() : new ArrayList<>();
  }

  public String getNameSpace() {
    return getId().getNamespace();
  }

  public String getName() {
    return getId().getName();
  }

  public void setCriteria(SpawnCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public boolean isValid(IWorldEditor editor, Coord pos) {
    return getCriteria().isValid(new SpawnContext(editor.getInfo(pos)));
  }

  @Override
  public LevelSettings getLevelSettings(int level) {
    return getLevels().get(level);
  }

  @Override
  public TowerSettings getTower() {
    if (getTowerSettings() == null) {
      return new TowerSettings(Tower.ROGUE, Theme.STONE);
    }

    return getTowerSettings();
  }

  @Override
  public int getNumLevels() {
    return getMaxNumLevels();
  }

  @Override
  public Set<SettingsType> getOverrides() {
    return overrides;
  }

  @Override
  public boolean isExclusive() {
    return exclusive;
  }

  @Override
  public void processLoot(Random rand, TreasureManager treasure) {
    getLootRules().process(rand, treasure);

    if (!getLootTables().isEmpty()) {
      getLootTables().forEach(table -> table.process(treasure));
    }
  }

  public LootRuleManager getLootRules() {
    return lootRules;
  }

  public void setExclusive(boolean exclusive) {
    this.exclusive = exclusive;
  }

  public TowerSettings getTowerSettings() {
    return towerSettings;
  }

  public void setTowerSettings(TowerSettings towerSettings) {
    this.towerSettings = towerSettings;
  }

  public List<SettingIdentifier> getInherit() {
    return inherit;
  }

  public void setInherit(List<SettingIdentifier> inherit) {
    this.inherit = inherit;
  }

  public Map<Integer, LevelSettings> getLevels() {
    return levels;
  }

  public void setLevels(Map<Integer, LevelSettings> levels) {
    this.levels = levels;
  }

  public SpawnCriteria getCriteria() {
    return criteria;
  }

  public void setLootRules(LootRuleManager lootRules) {
    this.lootRules = lootRules;
  }

  public List<LootTableRule> getLootTables() {
    return lootTables;
  }

  public void setLootTables(List<LootTableRule> lootTables) {
    this.lootTables = lootTables;
  }

  public void setOverrides(Set<SettingsType> overrides) {
    this.overrides = overrides;
  }
}
