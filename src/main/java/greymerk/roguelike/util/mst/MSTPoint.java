package greymerk.roguelike.util.mst;

import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

public class MSTPoint {

  private Coord position;
  private Coord adjusted;
  private int rank;
  private MSTPoint parent;

  public MSTPoint(Coord pos, Random rand) {
    this.position = new Coord(pos);
    this.adjusted = new Coord(pos);
    this.adjusted.add(Cardinal.directions[rand.nextInt(Cardinal.directions.length)]);

    this.rank = 0;
    this.parent = this;
  }

  public double distance(MSTPoint other) {
    return adjusted.distance(other.adjusted);
  }

  public Coord getPosition() {
    return new Coord(position);
  }

  public int getRank() {
    return rank;
  }

  public void incRank() {
    ++rank;
  }

  public MSTPoint getParent() {
    return this.parent;
  }

  public void setParent(MSTPoint p) {
    this.parent = p;
  }

  public void scaleBy(double multiplier) {
    double x = this.position.getX();
    double y = this.position.getY();
    double z = this.position.getZ();

    x *= multiplier;
    z *= multiplier;

    x = Math.floor(x);
    z = Math.floor(z);

    this.position = new Coord((int) x, (int) y, (int) z);
  }
}
