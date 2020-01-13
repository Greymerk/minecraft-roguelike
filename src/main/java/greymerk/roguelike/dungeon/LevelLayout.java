package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.worldgen.IBounded;

public class LevelLayout implements ILevelLayout {

  private List<DungeonNode> nodes = new ArrayList<>();
  private List<DungeonTunnel> tunnels = new ArrayList<>();
  private DungeonNode start;
  private DungeonNode end;

  public LevelLayout() {
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

  private boolean anyTunnelsOverlap(DungeonNode node, int size) {
    return getTunnels().stream()
        .anyMatch(tunnel -> node.overlaps(size, tunnel));
  }

  private boolean anyNodesOverlap(DungeonNode node, int size) {
    return getNodes().stream()
        .anyMatch(other -> node.overlaps(size, other));
  }

  @Override
  public DungeonNode getBestFit(IDungeonRoom room) {
    return getNonOverlappingConnectingNode(room)
        .orElseGet(this::getConnectingNode);
  }

  private Optional<DungeonNode> getNonOverlappingConnectingNode(IDungeonRoom room) {
    return getNodes().stream()
        .filter(this::isConnectingNode)
        .filter(DungeonNode::isNotYetGenerated)
        .filter(node -> !overlaps(node, room.getSize()))
        .findFirst();
  }

  private DungeonNode getConnectingNode() {
    return getNodes().stream()
        .filter(this::isConnectingNode)
        .filter(DungeonNode::isNotYetGenerated)
        .findFirst()
        .orElse(null);
  }

  private boolean overlaps(DungeonNode node, int size) {
    return anyTunnelsOverlap(node, size) || anyNodesOverlap(node, size);
  }

  private boolean isConnectingNode(DungeonNode node) {
    return node != start && node != end;
  }

  @Override
  public boolean hasEmptyRooms() {
    return nodes.stream()
        .filter(this::isConnectingNode)
        .anyMatch(DungeonNode::isNotYetGenerated);
  }

  @Override
  public List<IBounded> getBoundingBoxes() {
    List<IBounded> boxes = new ArrayList<>();
    boxes.addAll(nodes);
    boxes.addAll(tunnels);
    return boxes;
  }
}
