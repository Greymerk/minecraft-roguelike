package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.worldgen.IBounded;

public class LevelLayout implements ILevelLayout {

  private List<DungeonNode> nodes;
  private List<DungeonTunnel> tunnels;
  private DungeonNode start;
  private DungeonNode end;

  public LevelLayout() {
    nodes = new ArrayList<>();
    tunnels = new ArrayList<>();
  }

  @Override
  public DungeonNode getStart() {
    return start;
  }

  public void setStart(DungeonNode start) {
    this.start = start;
    addNode(start);
  }

  @Override
  public DungeonNode getEnd() {
    return end;
  }

  public void setEnd(DungeonNode end) {
    this.end = end;
    addNode(end);
  }

  public void addNode(DungeonNode node) {
    nodes.add(node);
  }

  public void addTunnel(DungeonTunnel tunnel) {
    tunnels.add(tunnel);
  }

  public void addTunnels(List<DungeonTunnel> tunnels) {
    this.tunnels.addAll(tunnels);
  }

  @Override
  public List<DungeonNode> getNodes() {
    return nodes;
  }

  @Override
  public List<DungeonTunnel> getTunnels() {
    return tunnels;
  }

  public void setStartEnd(Random rand, DungeonNode start) {
    this.start = start;

    int attempts = 0;
    do {
      end = nodes.get(rand.nextInt(nodes.size()));
      attempts++;
    } while (end == this.start || end.getPosition().distance(start.getPosition()) > (16 + attempts * 2));
  }

  public boolean overlaps(DungeonNode node, int size) {

    for (DungeonTunnel tunnel : getTunnels()) {
      if (node.connectsTo(tunnel)) {
        continue;
      }

      if (node.getBoundingBox(size).collide(tunnel)) {
        return true;
      }
    }

    for (DungeonNode n : getNodes()) {
      if (node == n) {
        continue;
      }

      if (node.getBoundingBox(size).collide(n)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public DungeonNode getBestFit(IDungeonRoom room) {

    for (DungeonNode node : getNodes()) {
      if (node == start || node == end) {
        continue;
      }

      if (node.getRoom() != null) {
        continue;
      }

      if (overlaps(node, room.getSize())) {
        continue;
      }

      return node;
    }

    for (DungeonNode node : getNodes()) {
      if (node == start || node == end) {
        continue;
      }

      if (node.getRoom() != null) {
        continue;
      }
      return node;
    }

    return null;
  }

  @Override
  public boolean hasEmptyRooms() {
    for (DungeonNode node : nodes) {
      if (node == start || node == end) {
        continue;
      }

      if (node.getRoom() == null) {
        return true;
      }
    }

    return false;
  }

  @Override
  public List<IBounded> getBoundingBoxes() {
    List<IBounded> boxes = new ArrayList<>();
    boxes.addAll(nodes);
    boxes.addAll(tunnels);
    return boxes;
  }
}
