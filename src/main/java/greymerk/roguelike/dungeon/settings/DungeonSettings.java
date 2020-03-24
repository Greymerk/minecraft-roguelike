package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import static com.google.common.collect.Sets.newHashSet;
import static greymerk.roguelike.dungeon.settings.SettingsContainer.DEFAULT_NAMESPACE;
import static java.util.Optional.ofNullable;


public class DungeonSettings implements ISettings {

  private static final int MAX_NUM_LEVELS = 5;
  private SettingIdentifier id;
  private List<SettingIdentifier> inherit = new ArrayList<>();
  private boolean exclusive;
  private TowerSettings towerSettings;
  private Map<Integer, LevelSettings> levels = new HashMap<>();
  private SpawnCriteria spawnCriteria = new SpawnCriteria();
  private LootRuleManager lootRules = new LootRuleManager();
  private List<LootTableRule> lootTables = new ArrayList<>();
  private Set<SettingsType> overrides = new HashSet<>();

  public DungeonSettings() {
  }

  public DungeonSettings(String id) {
    this(new SettingIdentifier(id));
  }

  public DungeonSettings(SettingIdentifier id) {
    setId(id);
  }

  public DungeonSettings(DungeonSettings parent, DungeonSettings child) {
    getOverrides().addAll(ofNullable(child.getOverrides()).orElse(newHashSet()));

    setLootRules(new LootRuleManager());
    if (!getOverrides().contains(SettingsType.LOOTRULES)) {
      getLootRules().add(parent.getLootRules());
      getLootTables().addAll(parent.getLootTables());
    }
    getLootRules().add(child.getLootRules());
    getLootTables().addAll(child.getLootTables());
    getInherit().addAll(child.getInherit());

    setExclusive(child.isExclusive());

    setTowerSettings(getTowerSettings(parent, child));

    IntStream.range(0, getMaxNumLevels())
        .forEach(i -> getLevels().put(i, new LevelSettings(parent.getLevels().get(i), child.getLevels().get(i), getOverrides())));
  }

  private TowerSettings getTowerSettings(DungeonSettings parent, DungeonSettings child) {
    if (getOverrides().contains(SettingsType.TOWER) && child.getTowerSettings() != null) {
      return new TowerSettings(child.getTowerSettings());
    } else if (parent.getTowerSettings() != null || child.getTowerSettings() != null) {
      return new TowerSettings(parent.getTowerSettings(), child.getTowerSettings());
    } else {
      return null;
    }
  }

  public DungeonSettings(DungeonSettings toCopy) {
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

  public List<SettingIdentifier> getInherits() {
    return getInherit() != null ? getInherit() : new ArrayList<>();
  }

  public String getNamespace() {
    return ofNullable(getId().getNamespace()).orElse(DEFAULT_NAMESPACE);
  }

  public String getName() {
    return getId().getName();
  }

  public void setSpawnCriteria(SpawnCriteria spawnCriteria) {
    this.spawnCriteria = spawnCriteria;
  }

  @Override
  public boolean isValid(IWorldEditor editor, Coord pos) {
    return getSpawnCriteria().isValid(new SpawnContext(editor.getInfo(pos)));
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

  public SpawnCriteria getSpawnCriteria() {
    return spawnCriteria;
  }

  public void setLootRules(LootRuleManager lootRules) {
    this.lootRules = lootRules;
  }

  public List<LootTableRule> getLootTables() {
    return lootTables;
  }

}
