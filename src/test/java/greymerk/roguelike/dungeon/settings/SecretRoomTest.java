package greymerk.roguelike.dungeon.settings;

import org.junit.Test;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretRoom;

public class SecretRoomTest {

  @Test
  public void testEquals() {

    SecretRoom test = new SecretRoom(DungeonRoom.BEDROOM, 1);
    SecretRoom other = new SecretRoom(DungeonRoom.BEDROOM, 1);

    assert (test.equals(other));

    SecretRoom third = new SecretRoom(DungeonRoom.CAKE, 1);

    assert (!test.equals(third));

    SecretRoom twoBeds = new SecretRoom(DungeonRoom.BEDROOM, 2);

    assert (!test.equals(twoBeds));

    SecretRoom twoCakes = new SecretRoom(DungeonRoom.CAKE, 2);

    assert (!twoBeds.equals(twoCakes));

  }
}
