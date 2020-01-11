package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.IDungeonFactory;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.ISegmentGenerator;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class LevelSettings {

  private static final int NUM_ROOMS = 12;
  private static final int LEVEL_RANGE = 80;
  private static final int SCATTER = 12;

  private int numRooms;
  private int range;
  private int scatter;
  private int levelDifficulty;
  private DungeonFactory rooms;
  private SecretFactory secrets;
  private ITheme theme;
  private SegmentGenerator segments;
  private SpawnerSettings spawners;
  private LevelGenerator generator;
  private Set<Filter> filters;

  public LevelSettings() {
    numRooms = NUM_ROOMS;
    range = LEVEL_RANGE;
    scatter = SCATTER;
    spawners = new SpawnerSettings();
    rooms = new DungeonFactory();
    secrets = new SecretFactory();
    this.filters = new HashSet<>();
    levelDifficulty = -1;
  }

  public LevelSettings(LevelSettings toCopy) {
    init(toCopy);
  }

  public LevelSettings(LevelSettings base, LevelSettings other, Set<SettingsType> overrides) {
    this();

    if (base == null && other == null) {
      return;
    }

    if (base == null) {
      init(other);
      return;
    }

    if (other == null) {
      init(base);
      return;
    }

    this.numRooms = other.numRooms != base.numRooms
        && other.numRooms != NUM_ROOMS
        ? other.numRooms : base.numRooms;

    this.range = other.range != base.range
        && other.range != LEVEL_RANGE
        ? other.range : base.range;

    this.scatter = other.scatter != base.scatter
        && other.scatter != SCATTER
        ? other.scatter : base.scatter;

    this.levelDifficulty = (base.levelDifficulty != other.levelDifficulty
        && other.levelDifficulty != -1) || base.levelDifficulty == -1
        ? other.levelDifficulty : base.levelDifficulty;

    if (overrides.contains(SettingsType.ROOMS)) {
      this.rooms = new DungeonFactory(base.rooms);
    } else {
      this.rooms = new DungeonFactory(base.rooms, other.rooms);
    }

    if (overrides.contains(SettingsType.SECRETS)) {
      this.secrets = new SecretFactory(other.secrets);
    } else {
      this.secrets = new SecretFactory(base.secrets, other.secrets);
    }

    if (other.theme != null) {
      if (base.theme == null || overrides.contains(SettingsType.THEMES)) {
        this.theme = Theme.create(other.theme);
      } else {
        this.theme = Theme.create(base.theme, other.theme);
      }
    } else if (base.theme != null) {
      this.theme = Theme.create(base.theme);
    }

    if (base.segments != null || other.segments != null) {
      this.segments = other.segments == null ? new SegmentGenerator(base.segments) : new SegmentGenerator(other.segments);
    }

    this.spawners = new SpawnerSettings(base.spawners, other.spawners);
    this.generator = other.generator == null ? base.generator : other.generator;

    this.filters.addAll(base.filters);
    this.filters.addAll(other.filters);
  }

  private void init(LevelSettings toCopy) {
    this.numRooms = toCopy.numRooms;
    this.range = toCopy.range;
    this.scatter = toCopy.scatter;
    this.levelDifficulty = toCopy.levelDifficulty;
    this.rooms = toCopy.rooms != null ? new DungeonFactory(toCopy.rooms) : null;
    this.secrets = toCopy.secrets != null ? new SecretFactory(toCopy.secrets) : null;
    this.theme = toCopy.theme;
    this.segments = toCopy.segments != null ? new SegmentGenerator(toCopy.segments) : null;
    this.spawners = new SpawnerSettings(toCopy.spawners);
    this.filters = new HashSet<>();
    this.filters.addAll(toCopy.filters);
    this.generator = toCopy.generator;
  }

  public LevelGenerator getGenerator() {
    return this.generator != null ? this.generator : LevelGenerator.CLASSIC;
  }

  public void setGenerator(LevelGenerator type) {
    this.generator = type;
  }

  public int getScatter() {
    return this.scatter;
  }

  public void setScatter(int scatter) {
    this.scatter = scatter;
  }

  public int getNumRooms() {
    return numRooms;
  }

  public void setNumRooms(int num) {
    numRooms = num;
  }

  public int getDifficulty(Coord pos) {

    if (this.levelDifficulty == -1) {
      return Dungeon.getLevel(pos.getY());
    }

    return levelDifficulty;
  }

  public void setDifficulty(int num) {
    this.levelDifficulty = num;
  }

  public IDungeonFactory getRooms() {
    return rooms != null ? rooms : new DungeonFactory();
  }

  public void setRooms(DungeonFactory rooms) {
    this.rooms = rooms;
  }

  public SecretFactory getSecrets() {
    return secrets != null ? secrets : new SecretFactory();
  }

  public void setSecrets(SecretFactory secrets) {
    this.secrets = secrets;
  }

  public ISegmentGenerator getSegments() {
    return segments != null ? segments : new SegmentGenerator();
  }

  public void setSegments(SegmentGenerator segments) {
    this.segments = segments;
  }

  public ITheme getTheme() {
    return theme != null ? theme : Theme.STONE.getThemeBase();
  }

  public void setTheme(ITheme theme) {
    this.theme = theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme.getThemeBase();
  }

  public SpawnerSettings getSpawners() {
    return this.spawners;
  }

  public int getRange() {
    return this.range;
  }

  public void setRange(int range) {
    this.range = range;
  }

  public List<Filter> getFilters() {
    return new ArrayList<>(this.filters);
  }

  public void addFilter(Filter filter) {
    this.filters.add(filter);
  }


  @Override
  public boolean equals(Object o) {
    LevelSettings other = (LevelSettings) o;
    if (other.generator != this.generator) {
      return false;
    }
    if (!this.secrets.equals(other.secrets)) {
      return false;
    }
    return this.rooms.equals(other.rooms);
  }
}
