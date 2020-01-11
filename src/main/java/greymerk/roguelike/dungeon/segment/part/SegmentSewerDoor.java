package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewerDoor extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
    MetaBlock leaves = Leaves.get(Wood.SPRUCE, false);
    MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = Cardinal.orthogonal(dir);

    cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    bars.set(editor, cursor);
    start = new Coord(cursor);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    stair.setOrientation(orth[0], true).set(editor, start);
    stair.setOrientation(orth[1], true).set(editor, end);
    cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    bars.set(editor, cursor);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, water);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 3);
    bars.set(editor, cursor);
    cursor.add(Cardinal.UP);
    leaves.set(editor, rand, cursor, false, true);
    cursor.add(dir);
    water.set(editor, rand, cursor, false, true);
    cursor.add(dir);
    glowstone.set(editor, rand, cursor, false, true);

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    start = new Coord(cursor);
    start.add(orth[0], 1);
    end = new Coord(cursor);
    end.add(orth[1], 1);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    SecretFactory secrets = level.getSettings().getSecrets();
    IDungeonRoom room = secrets.genRoom(editor, rand, level.getSettings(), dir, new Coord(origin));

    start.add(dir, 1);
    end.add(dir, 1);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);

    cursor.add(Cardinal.UP, 2);
    for (Cardinal d : orth) {
      Coord c = new Coord(cursor);
      c.add(d, 1);
      stair.setOrientation(Cardinal.reverse(d), true);
      stair.set(editor, rand, c);
    }

    if (room != null) {
      cursor = new Coord(origin);
      cursor.add(dir, 3);
      theme.getSecondary().getDoor().generate(editor, cursor, Cardinal.reverse(dir));
    }
  }
}
