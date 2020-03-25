package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import static greymerk.roguelike.dungeon.settings.SettingsType.ROOMS;
import static greymerk.roguelike.dungeon.settings.SettingsType.SECRETS;
import static greymerk.roguelike.dungeon.settings.SettingsType.THEMES;
import static java.util.Optional.ofNullable;

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
    filters = new HashSet<>();
    levelDifficulty = -1;
  }

  public LevelSettings(LevelSettings toCopy) {
    init(toCopy);
  }

  public LevelSettings(LevelSettings parent, LevelSettings child, Set<SettingsType> overrides) {
    this();

    if (parent == null && child == null) {
      return;
    }

    if (parent == null) {
      init(child);
      return;
    }

    if (child == null) {
      init(parent);
      return;
    }

    numRooms = child.numRooms != parent.numRooms
        && child.numRooms != NUM_ROOMS
        ? child.numRooms : parent.numRooms;

    range = child.range != parent.range
        && child.range != LEVEL_RANGE
        ? child.range : parent.range;

    scatter = child.scatter != parent.scatter
        && child.scatter != SCATTER
        ? child.scatter : parent.scatter;

    levelDifficulty = (parent.levelDifficulty != child.levelDifficulty
        && child.levelDifficulty != -1) || parent.levelDifficulty == -1
        ? child.levelDifficulty : parent.levelDifficulty;

    if (overrides.contains(ROOMS)) {
      rooms = new DungeonFactory(child.rooms);
    } else {
      rooms = new DungeonFactory(parent.rooms, child.rooms);
    }

    if (overrides.contains(SECRETS)) {
      secrets = new SecretFactory(child.secrets);
    } else {
      secrets = new SecretFactory(parent.secrets, child.secrets);
    }

    setTheme(selectTheme(parent, child, overrides));

    if (parent.segments != null || child.segments != null) {
      segments = child.segments == null ? new SegmentGenerator(parent.segments) : new SegmentGenerator(child.segments);
    }

    spawners = new SpawnerSettings(parent.spawners, child.spawners);
    generator = child.generator == null ? parent.generator : child.generator;

    filters.addAll(parent.filters);
    filters.addAll(child.filters);
  }

  private ITheme selectTheme(LevelSettings parent, LevelSettings child, Set<SettingsType> overrides) {
    Optional<ITheme> parentTheme = ofNullable(parent.theme);
    Optional<ITheme> childTheme = ofNullable(child.theme);
    if (overrides.contains(THEMES) && childTheme.isPresent()) {
      return childTheme.get();
    } else if (parentTheme.isPresent() && childTheme.isPresent()) {
      return Theme.create(parentTheme.get(), childTheme.get());
    } else if (childTheme.isPresent()) {
      return childTheme.get();
    } else if (parentTheme.isPresent()) {
      return parentTheme.get();
    } else {
      return null;
    }
  }

  private void init(LevelSettings toCopy) {
    numRooms = toCopy.numRooms;
    range = toCopy.range;
    scatter = toCopy.scatter;
    levelDifficulty = toCopy.levelDifficulty;
    rooms = toCopy.rooms != null ? new DungeonFactory(toCopy.rooms) : null;
    secrets = toCopy.secrets != null ? new SecretFactory(toCopy.secrets) : null;
    theme = toCopy.theme;
    segments = toCopy.segments != null ? new SegmentGenerator(toCopy.segments) : null;
    spawners = new SpawnerSettings(toCopy.spawners);
    filters = new HashSet<>();
    filters.addAll(toCopy.filters);
    generator = toCopy.generator;
  }

  public LevelGenerator getGenerator() {
    return generator != null ? generator : LevelGenerator.CLASSIC;
  }

  public void setGenerator(LevelGenerator type) {
    generator = type;
  }

  public int getScatter() {
    return scatter;
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

    if (levelDifficulty == -1) {
      return Dungeon.getLevel(pos.getY());
    }

    return levelDifficulty;
  }

  public void setDifficulty(int num) {
    levelDifficulty = num;
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
    // return theme;
    // todo: not rely on this class to provide default as it's an inverted dependency
    return theme != null ? theme : Theme.STONE.getThemeBase();
  }

  public void setTheme(ITheme theme) {
    this.theme = theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme.getThemeBase();
  }

  public SpawnerSettings getSpawners() {
    return spawners;
  }

  public int getRange() {
    return range;
  }

  public void setRange(int range) {
    this.range = range;
  }

  public List<Filter> getFilters() {
    return new ArrayList<>(filters);
  }

  public void addFilter(Filter filter) {
    filters.add(filter);
  }


  @Override
  public boolean equals(Object o) {
    LevelSettings other = (LevelSettings) o;
    if (other.generator != generator) {
      return false;
    }
    if (!secrets.equals(other.secrets)) {
      return false;
    }
    return rooms.equals(other.rooms);
  }
}
