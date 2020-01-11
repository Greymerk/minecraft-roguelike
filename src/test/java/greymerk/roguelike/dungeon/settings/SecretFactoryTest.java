package greymerk.roguelike.dungeon.settings;

import org.junit.Test;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;

public class SecretFactoryTest {

  @Test
  public void testOverride() {

    SecretFactory secrets1 = new SecretFactory();
    secrets1.addRoom(DungeonRoom.BEDROOM);
    secrets1.addRoom(DungeonRoom.FIREWORK);

    SecretFactory secrets2 = new SecretFactory();
    secrets2.addRoom(DungeonRoom.BEDROOM);
    secrets2.addRoom(DungeonRoom.PRISON);

    SecretFactory test = new SecretFactory();
    test.addRoom(DungeonRoom.BEDROOM, 2);
    test.addRoom(DungeonRoom.FIREWORK);
    test.addRoom(DungeonRoom.PRISON);

    assert (test.equals(new SecretFactory(secrets1, secrets2)));

  }

  @Test
  public void testAdd() {

    SecretFactory threeBeds = new SecretFactory();
    threeBeds.addRoom(DungeonRoom.BEDROOM, 3);

    SecretFactory test = new SecretFactory();
    test.addRoom(DungeonRoom.BEDROOM);
    test.addRoom(DungeonRoom.BEDROOM);
    test.addRoom(DungeonRoom.BEDROOM);

    assert (test.equals(threeBeds));
  }


  @Test
  public void testEquals() {

    SecretFactory secrets1 = new SecretFactory();
    secrets1.addRoom(DungeonRoom.BEDROOM);

    SecretFactory secrets2 = new SecretFactory();
    secrets2.addRoom(DungeonRoom.BEDROOM, 1);

    assert (secrets1.equals(secrets2));

    SecretFactory secrets3 = new SecretFactory();
    secrets3.addRoom(DungeonRoom.BTEAM);

    assert (!secrets1.equals(secrets3));

    secrets1.addRoom(DungeonRoom.BTEAM);
    secrets3.addRoom(DungeonRoom.BEDROOM);

    assert (secrets1.equals(secrets3));
  }

  @Test
  public void testMerge() {

  }
}
