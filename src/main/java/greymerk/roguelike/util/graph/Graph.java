package greymerk.roguelike.util.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph<P> {

  private List<P> points;
  private List<Edge<P>> edges;

  public Graph() {
    this.points = new ArrayList<>();
    this.edges = new ArrayList<>();
  }

  public void addEdge(Edge<P> edge) {

    P start = edge.getStart();
    P end = edge.getEnd();

    if (!points.contains(start)) {
      points.add(start);
    }
    if (!points.contains(end)) {
      points.add(end);
    }

    this.edges.add(edge);
  }

  public List<P> getPoints() {
    return this.points;
  }

  public List<Edge<P>> getEdges() {
    return this.edges;
  }
}
