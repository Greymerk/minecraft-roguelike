package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.segment.ISegment;
import greymerk.roguelike.dungeon.segment.ISegmentGenerator;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BoundingBox;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Shape;

public class DungeonTunnel implements Iterable<Coord>, IBounded {

  private Coord start;
  private Coord end;
  private List<ISegment> segments;
  private List<Coord> tunnel;

  public DungeonTunnel(Coord start, Coord end) {
    this.start = start;
    this.end = end;
    this.tunnel = new RectSolid(start, end).get();
    this.segments = new ArrayList<ISegment>();
  }

  @Override
  public Iterator<Coord> iterator() {
    return tunnel.iterator();
  }

  public void encase(IWorldEditor editor, Random rand, ITheme theme) {
    Coord s;
    Coord e;
    Cardinal dir = this.getDirection();

    s = new Coord(this.start);
    e = new Coord(this.end);
    s.add(Cardinal.left(dir), 3);
    s.add(Cardinal.UP, 3);
    e.add(Cardinal.right(dir), 3);
    e.add(Cardinal.DOWN, 3);
    RectSolid.fill(editor, rand, s, e, theme.getPrimary().getWall());
  }

  public void construct(IWorldEditor editor, Random rand, LevelSettings settings) {

    MetaBlock air = BlockType.get(BlockType.AIR);

    IBlockFactory wallBlocks = settings.getTheme().getPrimary().getWall();
    IBlockFactory floor = settings.getTheme().getPrimary().getFloor();
    BlockJumble bridgeBlocks = new BlockJumble();
    Coord s;
    Coord e;

    bridgeBlocks.addBlock(floor);
    bridgeBlocks.addBlock(air);

    s = new Coord(this.start);
    s.add(Cardinal.NORTH);
    s.add(Cardinal.EAST);
    e = new Coord(this.end);
    e.add(Cardinal.SOUTH);
    e.add(Cardinal.WEST);
    e.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, s, e, air);

    s.add(Cardinal.NORTH);
    s.add(Cardinal.EAST);
    s.add(Cardinal.DOWN);
    e.add(Cardinal.SOUTH);
    e.add(Cardinal.WEST);
    e.add(Cardinal.UP);
    RectHollow.fill(editor, rand, s, e, wallBlocks, false, true);

    s = new Coord(this.start);
    s.add(Cardinal.NORTH);
    s.add(Cardinal.EAST);
    s.add(Cardinal.DOWN);
    e = new Coord(this.end);
    e.add(Cardinal.SOUTH);
    e.add(Cardinal.WEST);
    e.add(Cardinal.DOWN);
    RectSolid.fill(editor, rand, s, e, floor, false, true);
    RectSolid.fill(editor, rand, s, e, bridgeBlocks, true, false);

    Cardinal dir = this.getDirection();

    // end of the tunnel;
    Coord location = new Coord(end);
    location.add(dir, 1);

    Coord start = new Coord(location);
    Cardinal[] orth = Cardinal.orthogonal(dir);
    start.add(orth[0], 2);
    start.add(Cardinal.UP, 2);
    Coord end = new Coord(location);
    end.add(orth[1], 2);
    end.add(Cardinal.DOWN, 2);

    RectSolid.fill(editor, rand, start, end, wallBlocks, false, true);

  }

  public Coord[] getEnds() {
    Coord[] toReturn = new Coord[2];
    toReturn[0] = new Coord(start);
    toReturn[1] = new Coord(end);
    return toReturn;
  }

  public Cardinal getDirection() {
    return this.start.dirTo(this.end);
  }

  public void genSegments(IWorldEditor editor, Random rand, IDungeonLevel level) {
    LevelSettings settings = level.getSettings();
    ISegmentGenerator segGen = settings.getSegments();
    for (Coord c : this) {
      this.segments.addAll(segGen.genSegment(editor, rand, level, this.getDirection(), c));
    }

  }

  public List<ISegment> getSegments() {
    return this.segments;
  }

  public BoundingBox getBoundingBox() {
    Coord s;
    Coord e;
    Cardinal dir = this.getDirection();
    s = new Coord(this.start);
    e = new Coord(this.end);
    s.add(Cardinal.left(dir), 2);
    s.add(Cardinal.UP, 3);
    e.add(Cardinal.right(dir), 2);
    e.add(Cardinal.DOWN, 1);
    return new BoundingBox(s, e);
  }

  public boolean hasEnd(Coord pos) {
    return pos.equals(this.start) || pos.equals(this.end);
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
