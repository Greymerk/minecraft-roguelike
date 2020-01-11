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
    this.nodes = new ArrayList<>();
    this.tunnels = new ArrayList<>();
  }

  @Override
  public DungeonNode getStart() {
    return this.start;
  }

  public void setStart(DungeonNode start) {
    this.start = start;
    this.addNode(start);
  }

  @Override
  public DungeonNode getEnd() {
    return this.end;
  }

  public void setEnd(DungeonNode end) {
    this.end = end;
    this.addNode(end);
  }

  public void addNode(DungeonNode node) {
    this.nodes.add(node);
  }

  public void addTunnel(DungeonTunnel tunnel) {
    this.tunnels.add(tunnel);
  }

  public void addTunnels(List<DungeonTunnel> tunnels) {
    this.tunnels.addAll(tunnels);
  }

  @Override
  public List<DungeonNode> getNodes() {
    return this.nodes;
  }

  @Override
  public List<DungeonTunnel> getTunnels() {
    return this.tunnels;
  }

  public void setStartEnd(Random rand, DungeonNode start) {
    this.start = start;

    int attempts = 0;
    do {
      end = this.nodes.get(rand.nextInt(this.nodes.size()));
      attempts++;
    } while (end == this.start || end.getPosition().distance(start.getPosition()) > (16 + attempts * 2));
  }

  public boolean overlaps(DungeonNode node, int size) {

    for (DungeonTunnel tunnel : this.getTunnels()) {
      if (node.connectsTo(tunnel)) {
        continue;
      }

      if (node.getBoundingBox(size).collide(tunnel)) {
        return true;
      }
    }

    for (DungeonNode n : this.getNodes()) {
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

    for (DungeonNode node : this.getNodes()) {
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

    for (DungeonNode node : this.getNodes()) {
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
    for (DungeonNode node : this.nodes) {
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
