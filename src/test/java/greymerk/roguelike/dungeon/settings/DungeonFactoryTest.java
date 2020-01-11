package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.rooms.DungeonCorner;
import greymerk.roguelike.dungeon.rooms.DungeonsPit;
import greymerk.roguelike.dungeon.rooms.DungeonsWood;

import static org.assertj.core.api.Assertions.assertThat;

public class DungeonFactoryTest {

  @Before
  public void setUp() throws Exception {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void testEquals() {

    DungeonFactory base = new DungeonFactory();
    DungeonFactory other = new DungeonFactory();

    assert (base.equals(other));

    base.addSingle(DungeonRoom.BRICK);

    assert (!base.equals(other));

    other.addSingle(DungeonRoom.BRICK);

    assert (base.equals(other));

    base.addRandom(DungeonRoom.CRYPT, 2);

    assert (!base.equals(other));

    other.addRandom(DungeonRoom.CRYPT, 2);

    assert (base.equals(other));

    base.addRandom(DungeonRoom.DARKHALL, 3);

    assert (!base.equals(other));

    other.addRandom(DungeonRoom.DARKHALL, 1);

    assert (!base.equals(other));

    other.addRandom(DungeonRoom.DARKHALL, 3);

    assert (base.equals(other));
  }

  @Test
  public void testMerge() {

    DungeonFactory base = new DungeonFactory();
    DungeonFactory other = new DungeonFactory();

    DungeonFactory third = new DungeonFactory();
    base.addRandom(DungeonRoom.BLAZE, 5);
    base.addRandom(DungeonRoom.CAKE, 1);
    base.addRandom(DungeonRoom.SLIME, 2);

    DungeonFactory merge = new DungeonFactory(base, other);

    assert (!third.equals(merge));

    third.addRandom(DungeonRoom.CAKE, 1);

    assert (!third.equals(merge));

    third.addRandom(DungeonRoom.SLIME, 2);

    assert (!third.equals(merge));

    third.addRandom(DungeonRoom.BLAZE, 1);

    assert (!third.equals(merge));

    third.addRandom(DungeonRoom.BLAZE, 5);

    assert (third.equals(merge));

    base.addSingle(DungeonRoom.CREEPER, 3);
    merge = new DungeonFactory(base, other);

    assert (!third.equals(merge));

    third.addSingle(DungeonRoom.CREEPER, 1);

    assert (!third.equals(merge));

    third.addSingle(DungeonRoom.CREEPER, 2);

    assert (third.equals(merge));
  }

  @Test
  public void testGetSingle() {

    Random rand = new Random();

    DungeonFactory rooms = new DungeonFactory(DungeonRoom.CORNER);
    assertThat(rooms.get(rand)).isInstanceOf(DungeonCorner.class);

    rooms = new DungeonFactory(DungeonRoom.CORNER);
    rooms.addSingle(DungeonRoom.CAKE, 2);
    assertThat(rooms.get(rand)).isInstanceOf(DungeonsWood.class);
    assertThat(rooms.get(rand)).isInstanceOf(DungeonsWood.class);
    assertThat(rooms.get(rand)).isInstanceOf(DungeonCorner.class);

    rooms = new DungeonFactory(DungeonRoom.CORNER);
    rooms.addSingle(DungeonRoom.PIT);
    assertThat(rooms.get(rand)).isInstanceOf(DungeonsPit.class);
  }
}
