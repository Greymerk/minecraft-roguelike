package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BoundingBox;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Shape;

public class DungeonNode implements IBounded {

  private Coord pos;
  private IDungeonRoom toGenerate;
  private Cardinal[] entrances;

  public DungeonNode(Cardinal[] entrances, Coord origin) {
    this.entrances = entrances;
    this.pos = new Coord(origin);
  }

  public void setDungeon(IDungeonRoom toGenerate) {
    this.toGenerate = toGenerate;
  }

  public int getSize() {
    if (toGenerate == null) {
      return 6;
    }

    return toGenerate.getSize();
  }

  public void encase(IWorldEditor editor, Random rand, ITheme theme) {
    int size = this.getSize();
    Coord s = new Coord(this.getPosition());
    Coord e = new Coord(s);
    s.add(Cardinal.NORTH, size);
    s.add(Cardinal.WEST, size);
    s.add(Cardinal.DOWN, 3);
    e.add(Cardinal.SOUTH, size);
    e.add(Cardinal.EAST, size);
    e.add(Cardinal.UP, 8);
    RectSolid.fill(editor, rand, s, e, theme.getPrimary().getWall());
  }

  public Cardinal[] getEntrances() {
    return this.entrances;
  }

  public Coord getPosition() {
    return new Coord(this.pos);
  }

  public IDungeonRoom getRoom() {
    return toGenerate;
  }

  public BoundingBox getBoundingBox(int size) {
    Coord start = new Coord(this.pos);
    Coord end = new Coord(this.pos);

    start.add(Cardinal.NORTH, size);
    start.add(Cardinal.WEST, size);
    start.add(Cardinal.DOWN, 1);

    end.add(Cardinal.SOUTH, size);
    end.add(Cardinal.EAST, size);
    end.add(Cardinal.UP, 8);

    return new BoundingBox(start, end);
  }

  public BoundingBox getBoundingBox() {
    return this.getBoundingBox(this.getSize());
  }

  public boolean connectsTo(DungeonTunnel tunnel) {
    return tunnel.hasEnd(this.pos);
  }

  @Override
  public boolean collide(IBounded other) {
    return this.getBoundingBox().collide(other);
  }

  @Override
  public IShape getShape(Shape type) {
    return this.getBoundingBox().getShape(type);
  }

  @Override
  public Coord getStart() {
    return this.getBoundingBox().getStart();
  }

  @Override
  public Coord getEnd() {
    return this.getBoundingBox().getEnd();
  }
}
