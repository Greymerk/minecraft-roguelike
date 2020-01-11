package greymerk.roguelike.worldgen.shapes;

import greymerk.roguelike.worldgen.Coord;

public enum Shape {

  RECTSOLID,
  RECTHOLLOW,
  RECTPYRAMID,
  RECTWIREFRAME,
  SPHERE;

  public static IShape get(Shape type, Coord start, Coord end) {
    switch (type) {
      case RECTSOLID:
        return new RectSolid(start, end);
      case RECTHOLLOW:
        return new RectHollow(start, end);
      case RECTPYRAMID:
        return new RectPyramid(start, end);
      case RECTWIREFRAME:
        return new RectWireframe(start, end);
      case SPHERE:
        return new Sphere(start, end);
      default:
        return new RectSolid(start, end);
    }
  }
}
