package greymerk.roguelike.util.graph;

public class Edge<P> implements Comparable<Edge<P>> {

  private P start;
  private P end;
  private double length;

  public Edge(P start, P end, double length) {
    this.start = start;
    this.end = end;
    this.length = length;
  }

  @Override
  public int compareTo(Edge<P> other) {
    return Double.compare(length, other.length);
  }

  public P getStart() {
    return start;
  }

  public P getEnd() {
    return end;
  }
}
