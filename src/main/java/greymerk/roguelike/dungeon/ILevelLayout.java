package greymerk.roguelike.dungeon;

import java.util.List;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.worldgen.IBounded;

public interface ILevelLayout {

  List<IBounded> getBoundingBoxes();

  List<DungeonNode> getNodes();

  List<DungeonTunnel> getTunnels();

  DungeonNode getStart();

  DungeonNode getEnd();

  boolean hasEmptyRooms();

  DungeonNode getBestFit(IDungeonRoom room);

}
