package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.util.graph.Edge;
import greymerk.roguelike.util.graph.Graph;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class LevelGeneratorMST implements ILevelGenerator {

  IWorldEditor editor;
  Random rand;
  LevelSettings settings;
  private LevelLayout layout;
  private int length;
  private int scatter;


  public LevelGeneratorMST(IWorldEditor editor, Random rand, LevelSettings settings) {
    this.editor = editor;
    this.rand = rand;
    this.settings = settings;
    this.length = (int) Math.ceil(Math.sqrt(settings.getNumRooms()));
    this.scatter = settings.getScatter() % 2 == 0 ? settings.getScatter() + 1 : settings.getScatter();
    this.layout = new LevelLayout();
  }

  @Override
  public void generate(Coord start) {
    MinimumSpanningTree mst = new MinimumSpanningTree(rand, length, scatter, new Coord(start));
    Graph<Coord> layout = mst.getGraph();
    List<Edge<Coord>> edges = layout.getEdges();
    List<Coord> vertices = layout.getPoints();
    List<Edge<Coord>> used = new ArrayList<Edge<Coord>>();

    for (Coord c : vertices) {
      for (Edge<Coord> e : edges) {
        if (used.contains(e)) {
          continue;
        }
        Coord[] ends = new Coord[]{e.getStart(), e.getEnd()};
        for (Coord p : ends) {
          if (p.equals(c)) {
            Coord tStart = ends[0];
            Coord tEnd = ends[1];
            this.layout.addTunnel(new DungeonTunnel(tStart, tEnd));
            used.add(e);
          }
        }
      }
    }

    DungeonNode startDungeonNode = null;

    for (Coord c : vertices) {
      List<Cardinal> entrances = new ArrayList<Cardinal>();
      for (DungeonTunnel tunnel : this.layout.getTunnels()) {
        Coord[] ends = tunnel.getEnds();
        if (ends[0].equals(c)) {
          entrances.add(ends[0].dirTo(ends[1]));
        } else if (ends[1].equals(c)) {
          entrances.add(ends[1].dirTo(ends[0]));
        }
      }

      Cardinal[] ents = new Cardinal[entrances.size()];
      DungeonNode toAdd = new DungeonNode(entrances.toArray(ents), c);
      this.layout.addNode(toAdd);

      if (c.equals(start)) {
        startDungeonNode = toAdd;
      }
    }

    this.layout.setStartEnd(rand, startDungeonNode);

  }

  @Override
  public LevelLayout getLayout() {
    return this.layout;
  }

}
