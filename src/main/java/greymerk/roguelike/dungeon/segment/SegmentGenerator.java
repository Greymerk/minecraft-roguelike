package greymerk.roguelike.dungeon.segment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

public class SegmentGenerator implements ISegmentGenerator {

  protected Segment arch;
  protected WeightedRandomizer<Segment> segments;

  public SegmentGenerator() {
    this(Segment.ARCH);
  }

  public SegmentGenerator(Segment arch) {
    this.segments = new WeightedRandomizer<>();
    this.arch = arch;
  }

  public SegmentGenerator(SegmentGenerator toCopy) {
    this.arch = toCopy.arch;
    this.segments = new WeightedRandomizer<>(toCopy.segments);
  }

  public SegmentGenerator(JsonObject json) {
    String archType = json.get("arch").getAsString();
    arch = Segment.valueOf(archType);

    this.segments = new WeightedRandomizer<>();
    JsonArray segmentList = json.get("segments").getAsJsonArray();
    for (JsonElement e : segmentList) {
      JsonObject segData = e.getAsJsonObject();
      this.add(segData);
    }
  }

  public static SegmentGenerator getRandom(Random rand, int count) {
    SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
    for (int i = 0; i < count; ++i) {
      segments.add(Segment.getRandom(rand), 1);
    }
    return segments;
  }

  public void add(JsonObject entry) {

    String segType = entry.get("type").getAsString();
    Segment type = Segment.valueOf(segType);

    if (entry.has("arch")) {
      boolean a = entry.get("arch").getAsBoolean();
      if (a) {
        this.arch = type;
      }
      return;
    }

    int weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;

    this.segments.add(new WeightedChoice<>(type, weight));
  }

  public void add(Segment toAdd, int weight) {
    this.segments.add(new WeightedChoice<>(toAdd, weight));
  }

  @Override
  public List<ISegment> genSegment(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos) {

    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();

    List<ISegment> segments = new ArrayList<>();

    for (Cardinal cardinal : Cardinal.orthogonal(dir)) {
      ISegment segment = pickSegment(rand, dir, pos);
      if (segment == null) {
        return segments;
      }
      segment.generate(editor, rand, level, cardinal, level.getSettings().getTheme(), new Coord(pos));
      segments.add(segment);
    }

    if (!level.hasNearbyNode(pos) && rand.nextInt(3) == 0) {
      addSupport(editor, rand, level.getSettings().getTheme(), x, y, z);
    }

    return segments;
  }

  private ISegment pickSegment(Random rand, Cardinal dir, Coord pos) {
    int x = pos.getX();
    int z = pos.getZ();

    if ((dir == Cardinal.NORTH || dir == Cardinal.SOUTH) && z % 3 == 0) {
      if (z % 6 == 0) {
        return Segment.getSegment(arch);
      }
      return this.segments.isEmpty()
          ? Segment.getSegment(Segment.WALL)
          : Segment.getSegment(this.segments.get(rand));
    }

    if ((dir == Cardinal.WEST || dir == Cardinal.EAST) && x % 3 == 0) {
      if (x % 6 == 0) {
        return Segment.getSegment(arch);
      }
      return this.segments.isEmpty()
          ? Segment.getSegment(Segment.WALL)
          : Segment.getSegment(this.segments.get(rand));
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
