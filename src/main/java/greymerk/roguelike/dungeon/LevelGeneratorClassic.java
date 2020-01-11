package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

public class LevelGeneratorClassic implements ILevelGenerator {

  private static final int MIN_ROOMS = 6;

  private Random rand;
  private LevelLayout layout;
  private LevelSettings settings;
  private Coord start;

  public LevelGeneratorClassic(Random rand, LevelSettings settings) {
    this.rand = rand;
    this.layout = new LevelLayout();
    this.settings = settings;
  }

  public void generate(Coord start) {
    this.start = start;
    List<Node> gNodes = new ArrayList<>();
    Node startNode = new Node(Cardinal.directions[rand.nextInt(Cardinal.directions.length)], start);
    gNodes.add(startNode);

    while (!this.isDone(gNodes)) {
      this.update(gNodes);
    }

    for (Node n : gNodes) {
      n.cull();
    }

    DungeonNode startDungeonNode = null;

    for (Node n : gNodes) {
      DungeonNode nToAdd = n.createNode();
      if (n == startNode) {
        startDungeonNode = nToAdd;
      }
      this.layout.addNode(nToAdd);
      this.layout.addTunnels(n.createTunnels());
    }

    this.layout.setStartEnd(rand, startDungeonNode);
  }

  public void update(List<Node> nodes) {
    if (!this.full(nodes)) {
      for (int i = 0; i < nodes.size(); i++) {
        nodes.get(i).update(nodes);
      }
    }
  }

  private boolean isDone(List<Node> nodes) {
    boolean allDone = true;

    for (Node node : nodes) {
      if (!node.isDone()) {
        allDone = false;
      }
    }

    return allDone || this.full(nodes);
  }

  private boolean full(List<Node> nodes) {
    return nodes.size() >= Math.max(this.settings.getNumRooms(), MIN_ROOMS);
  }

  public void spawnNode(List<Node> nodes, Tunneler tunneler) {
    Node toAdd = new Node(tunneler.getDirection(), tunneler.getPosition());
    nodes.add(toAdd);
  }

  public boolean hasNearbyNode(List<Node> nodes, Coord pos, int min) {
    for (Node node : nodes) {
      int dist = (int) node.getPos().distance(pos);
      if (dist < min) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ILevelLayout getLayout() {
    return this.layout;
  }

  private class Tunneler {

    private boolean done;
    private Cardinal dir;
    private Coord start;
    private Coord end;
    private int extend;

    public Tunneler(Cardinal dir, Coord start) {
      this.done = false;
      this.dir = dir;
      this.start = new Coord(start);
      this.end = new Coord(start);
      this.extend = settings.getScatter() * 2;
    }

    public void update(List<Node> nodes) {
      if (this.done) {
        return;
      }

      if (hasNearbyNode(nodes, end, settings.getScatter())) {
        end.add(dir);
      } else {
        if (rand.nextInt(extend) == 0) {
          spawnNode(nodes, this);
          this.done = true;
        } else {
          end.add(dir);
          extend--;
        }
      }
    }

    public boolean isDone() {
      return this.done;
    }

    public Cardinal getDirection() {
      return this.dir;
    }

    public Coord getPosition() {
      return new Coord(this.end);
    }

    public DungeonTunnel createTunnel() {
      return new DungeonTunnel(new Coord(this.start), new Coord(this.end));
    }
  }

  private class Node {

    private List<Tunneler> tunnelers;
    private Cardinal direction;
    private Coord pos;

    public Node(Cardinal direction, Coord pos) {
      this.tunnelers = new ArrayList<>();
      this.direction = direction;
      this.pos = pos;

      this.spawnTunnelers();
    }

    private void spawnTunnelers() {

      if (start.distance(pos) > settings.getRange()) {
        return;
      }

      for (Cardinal dir : Cardinal.directions) {

        if (dir.equals(Cardinal.reverse(this.direction))) {
          continue;
        }

        this.tunnelers.add(new Tunneler(dir, new Coord(this.pos)));
      }
    }

    public void update(List<Node> nodes) {
      for (Tunneler tunneler : tunnelers) {
        tunneler.update(nodes);
      }
    }

    public boolean isDone() {
      for (Tunneler tunneler : tunnelers) {
        if (!tunneler.isDone()) {
          return false;
        }
      }
      return true;
    }

    public Coord getPos() {
      return new Coord(this.pos);
    }

    public Cardinal[] getEntrances() {
      List<Cardinal> cardinal = new ArrayList<>();
      cardinal.add(Cardinal.reverse(direction));
      tunnelers.stream().map(tunneler -> tunneler.dir).forEach(cardinal::add);
      return cardinal.toArray(new Cardinal[0]);
    }

    public List<DungeonTunnel> createTunnels() {
      List<DungeonTunnel> tunnels = new ArrayList<>();
      for (Tunneler t : this.tunnelers) {
        tunnels.add(t.createTunnel());
      }
      return tunnels;
    }

    public DungeonNode createNode() {
      return new DungeonNode(this.getEntrances(), this.pos);
    }

    public void cull() {
      List<Tunneler> toKeep = new ArrayList<>();
      for (Tunneler t : this.tunnelers) {
        if (t.done) {
          toKeep.add(t);
        }
      }
      this.tunnelers = toKeep;
    }
  }
}
