package greymerk.roguelike.worldgen;

import org.junit.Before;
import org.junit.Test;

public class BoxTest {

  @Before
  public void setUp() {
  }


  @Test
  public void collide1D() {
    BoundingBox box = new BoundingBox(new Coord(6, 0, 0), new Coord(9, 0, 0));
    BoundingBox box2 = new BoundingBox(new Coord(1, 0, 0), new Coord(4, 0, 0));

    assert (!box.collide(box2));

    box = new BoundingBox(new Coord(4, 0, 0), new Coord(9, 0, 0));
    box2 = new BoundingBox(new Coord(1, 0, 0), new Coord(6, 0, 0));

    assert (box.collide(box2));

  }

  @Test
  public void collide2D() {
    BoundingBox box = new BoundingBox(new Coord(1, 0, 1), new Coord(3, 0, 3));
    BoundingBox box2 = new BoundingBox(new Coord(2, 0, 2), new Coord(4, 0, 4));

    assert (box.collide(box2));
  }

  @Test
  public void collide3D() {
    BoundingBox box = new BoundingBox(new Coord(1, 1, 1), new Coord(3, 5, 3));
    BoundingBox box2 = new BoundingBox(new Coord(2, 3, 2), new Coord(4, 7, 4));

    assert (box.collide(box2));
  }

}
