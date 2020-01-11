package greymerk.roguelike.dungeon.segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;

public class SegmentGeneratorBase implements ISegmentGenerator {

  protected Segment arch;
  protected WeightedRandomizer<Segment> segments;

  public SegmentGeneratorBase() {
    this.segments = new WeightedRandomizer<>();
    this.segments.add(new WeightedChoice<>((Segment.SHELF), 1));
    this.segments.add(new WeightedChoice<>((Segment.INSET), 1));
    this.segments.add(new WeightedChoice<>((Segment.DOOR), 1));
    this.segments.add(new WeightedChoice<>((Segment.FIREPLACE), 1));

    this.arch = Segment.ARCH;
  }

  public void add(Segment toAdd, int weight) {
    this.segments.add(new WeightedChoice<>(toAdd, weight));
  }

  @Override
  public List<ISegment> genSegment(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos) {

    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();

    List<ISegment> segs = new ArrayList<>();

    for (Cardinal orth : Cardinal.orthogonal(dir)) {
      ISegment seg = pickSegment(editor, rand, level, dir, pos);
      if (seg == null) {
        return segs;
      }
      seg.generate(editor, rand, level, orth, level.getSettings().getTheme(), new Coord(pos));
      segs.add(seg);
    }

    if (!level.hasNearbyNode(pos) && rand.nextInt(3) == 0) {
      addSupport(editor, rand, level.getSettings().getTheme(), x, y, z);
    }
    return segs;
  }

  private ISegment pickSegment(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos) {
    int x = pos.getX();
    int z = pos.getZ();

    if ((dir == Cardinal.NORTH || dir == Cardinal.SOUTH) && z % 3 == 0) {
      if (z % 6 == 0) {
        return Segment.getSegment(arch);
      }
      return Segment.getSegment(this.segments.get(rand));
    }

    if ((dir == Cardinal.WEST || dir == Cardinal.EAST) && x % 3 == 0) {
      if (x % 6 == 0) {
        return Segment.getSegment(arch);
      }
      return Segment.getSegment(this.segments.get(rand));
    }

    return null;
  }

  private void addSupport(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {
    if (!editor.isAirBlock(new Coord(x, y - 2, z))) {
      return;
    }

    editor.fillDown(rand, new Coord(x, y - 2, z), theme.getPrimary().getPillar());

    IStair stair = theme.getPrimary().getStair();
    stair.setOrientation(Cardinal.WEST, true);
    stair.set(editor, new Coord(x - 1, y - 2, z));

    stair.setOrientation(Cardinal.EAST, true);
    stair.set(editor, new Coord(x + 1, y - 2, z));

    stair.setOrientation(Cardinal.SOUTH, true);
    stair.set(editor, new Coord(x, y - 2, z + 1));

    stair.setOrientation(Cardinal.NORTH, true);
    stair.set(editor, new Coord(x, y - 2, z - 1));
  }
}
