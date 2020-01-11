package greymerk.roguelike.worldgen;

import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.Shape;

public interface IBounded {

  BoundingBox getBoundingBox();

  boolean collide(IBounded other);

  IShape getShape(Shape type);

  Coord getStart();

  Coord getEnd();

}
