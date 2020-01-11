package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.worldgen.filter.Filter;

public class LevelSettingsTest {

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void testEquals() {

    LevelSettings base = new LevelSettings();
    base.setGenerator(LevelGenerator.CLASSIC);

    LevelSettings other = new LevelSettings();
    assert (!base.equals(other));
    other.setGenerator(LevelGenerator.CLASSIC);
    assert (base.equals(other));

    DungeonFactory baseRooms = new DungeonFactory();
    DungeonFactory otherRooms = new DungeonFactory();

    baseRooms.addRandom(DungeonRoom.BRICK, 1);
    base.setRooms(baseRooms);
    assert (!base.equals(other));
    otherRooms.addRandom(DungeonRoom.BRICK, 1);
    other.setRooms(otherRooms);
    assert (base.equals(other));
    baseRooms.addRandom(DungeonRoom.CAKE, 2);
    assert (!base.equals(other));
    otherRooms.addRandom(DungeonRoom.CAKE, 2);
    assert (base.equals(other));


  }

  @Test
  public void testRoomsMerge() {

    LevelSettings base = new LevelSettings();
    LevelSettings other = new LevelSettings();

    DungeonFactory baseRooms = new DungeonFactory();
    DungeonFactory otherRooms = new DungeonFactory();

    base.setRooms(baseRooms);
    other.setRooms(otherRooms);

    LevelSettings merge;
    LevelSettings control = new LevelSettings();
    DungeonFactory controlRooms = new DungeonFactory();
    control.setRooms(controlRooms);
    Set<SettingsType> overrides = new HashSet<>();

    merge = new LevelSettings(base, other, overrides);

    assert (control.equals(merge));

    baseRooms.addSingle(DungeonRoom.CAKE);
    merge = new LevelSettings(base, other, overrides);
    assert (!control.equals(merge));

    controlRooms.addSingle(DungeonRoom.CAKE);
    assert (control.equals(merge));
  }

  @Test
  public void testSecretsMerge() {

    LevelSettings base = new LevelSettings();
    LevelSettings other = new LevelSettings();

    SecretFactory baseSecrets = new SecretFactory();
    SecretFactory otherSecrets = new SecretFactory();

    base.setSecrets(baseSecrets);
    other.setSecrets(otherSecrets);

    SecretFactory controlSecrets = new SecretFactory();
    LevelSettings control = new LevelSettings();
    control.setSecrets(controlSecrets);

    LevelSettings merge;
    Set<SettingsType> overrides = new HashSet<>();

    merge = new LevelSettings(base, other, overrides);

    assert (control.equals(merge));

    baseSecrets.addRoom(DungeonRoom.BEDROOM, 2);

    merge = new LevelSettings(base, other, overrides);
    assert (!control.equals(merge));

    controlSecrets.addRoom(DungeonRoom.BEDROOM, 2);
    assert (control.equals(merge));
  }

  @Test
  public void testThemeMerge() {
  }

  @Test
  public void testSegmentsMerge() {
  }

  @Test
  public void testSpawnerMerge() {
  }

  @Test
  public void testGeneratorMerge() {

    LevelSettings compare = new LevelSettings();
    compare.setGenerator(LevelGenerator.CLASSIC);

    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings base = new LevelSettings();
    LevelSettings other = new LevelSettings();

    LevelSettings control = new LevelSettings(base, other, overrides);

    assert (!control.equals(compare));

    other.setGenerator(LevelGenerator.CLASSIC);
    assert (other.equals(compare));

    LevelSettings merge = new LevelSettings(base, other, overrides);

    assert (merge.equals(compare));

    LevelSettings merge2 = new LevelSettings(other, base, overrides);

    assert (merge2.equals(compare));

  }

  @Test
  public void testFilterMerge() {
    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings compare = new LevelSettings();
    assert (compare.getFilters().isEmpty());
    compare.addFilter(Filter.VINE);
    assert (compare.getFilters().contains(Filter.VINE));

    LevelSettings base = new LevelSettings();
    base.addFilter(Filter.VINE);

    assert ((new LevelSettings(base)).getFilters().contains(Filter.VINE));

    LevelSettings other = new LevelSettings();

    assert (!(new LevelSettings(other, other, overrides)).getFilters().contains(Filter.VINE));

    assert ((new LevelSettings(base, other, overrides)).getFilters().contains(Filter.VINE));

    assert ((new LevelSettings(other, base, overrides)).getFilters().contains(Filter.VINE));

  }
}
