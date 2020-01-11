package greymerk.roguelike.worldgen;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordTest {

  @Test
  public void addNothing() {
    Coord test = new Coord(0, 0, 0);
    test.add(new Coord(0, 0, 0));
    assertThat(test.equals(new Coord(0, 0, 0))).isTrue();

    test = new Coord(-10, 10, -10);
    test.add(new Coord(0, 0, 0));
    assertThat(test.equals(new Coord(-10, 10, -10))).isTrue();
  }

  @Test
  public void addDirection() {
    Coord test = new Coord(0, 0, 0);
    test.add(Cardinal.NORTH);
    assertThat(test.equals(new Coord(0, 0, -1))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.SOUTH);
    assertThat(test.equals(new Coord(0, 0, 1))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.WEST);
    assertThat(test.equals(new Coord(-1, 0, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.EAST);
    assertThat(test.equals(new Coord(1, 0, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.DOWN);
    assertThat(test.equals(new Coord(0, -1, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.UP);
    assertThat(test.equals(new Coord(0, 1, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.NORTH, 5);
    assertThat(test.equals(new Coord(0, 0, -5))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.SOUTH, 5);
    assertThat(test.equals(new Coord(0, 0, 5))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.NORTH, -5);
    assertThat(test.equals(new Coord(0, 0, 5))).isTrue();

    test = new Coord(0, 0, 0);
    test.add(Cardinal.SOUTH, -5);
    assertThat(test.equals(new Coord(0, 0, -5))).isTrue();
  }

  @Test
  public void addCoord() {
    Coord test;

    test = new Coord(0, 0, 0);
    test.add(new Coord(5, 5, 5));
    assertThat(test.equals(new Coord(5, 5, 5))).isTrue();

    test = new Coord(-10, 0, 0);
    test.add(new Coord(100, 0, 0));
    assertThat(test.equals(new Coord(90, 0, 0))).isTrue();

  }

  @Test
  public void sub() {
    Coord test;
    test = new Coord(0, 0, 0);
    test.sub(new Coord(5, 5, 5));
    assertThat(test.equals(new Coord(-5, -5, -5))).isTrue();

    test = new Coord(100, 0, 0);
    test.sub(new Coord(10, 0, 0));
    assertThat(test.equals(new Coord(90, 0, 0))).isTrue();

  }

  @Test
  public void dirTo() {
    Coord one = new Coord(0, 0, 0);

    Coord two = new Coord(one);
    two.add(Cardinal.NORTH);
    assert (one.dirTo(two) == Cardinal.NORTH);

    two = new Coord(one);
    two.add(Cardinal.SOUTH);
    assert (one.dirTo(two) == Cardinal.SOUTH);

    two = new Coord(one);
    two.add(Cardinal.WEST);
    assert (one.dirTo(two) == Cardinal.WEST);

    two = new Coord(one);
    two.add(Cardinal.EAST);
    assert (one.dirTo(two) == Cardinal.EAST);
  }

}
