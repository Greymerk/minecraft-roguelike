package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Vine;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMossyArch extends SegmentBase {

  private boolean spawnHoleSet = false;

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal wallDirection, ITheme theme, Coord origin) {

    IStair stair = theme.getSecondary().getStair();
    stair.setOrientation(Cardinal.reverse(wallDirection), true);

    MetaBlock air = BlockType.get(BlockType.AIR);

    level.getSettings().getSecrets().genRoom(editor, rand, level.getSettings(), wallDirection, new Coord(origin));

    Coord cursor = new Coord(origin);
    cursor.add(wallDirection, 2);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    stair.set(editor, cursor);

    for (Cardinal orth : Cardinal.orthogonal(wallDirection)) {
      cursor = new Coord(origin);
      cursor.add(orth, 1);
      cursor.add(wallDirection, 2);
      theme.getSecondary().getPillar().set(editor, rand, cursor);
      cursor.add(Cardinal.UP, 1);
      theme.getSecondary().getPillar().set(editor, rand, cursor);
      cursor.add(Cardinal.UP, 1);
      theme.getSecondary().getWall().set(editor, rand, cursor);
      cursor.add(Cardinal.reverse(wallDirection), 1);
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(wallDirection, 2);
    cursor.add(Cardinal.DOWN, 1);
    BlockType.get(BlockType.WATER_FLOWING).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 3);
    cursor.add(wallDirection, 1);
    BlockType.get(BlockType.VINE).set(editor, cursor);

    if (!spawnHoleSet) {
      RectSolid.fill(editor, rand, new Coord(0, 2, 0).add(origin), new Coord(0, 5, 0).add(origin), BlockType.get(BlockType.AIR));
      Vine.fill(editor, new Coord(0, 3, 0).add(origin), new Coord(0, 5, 0).add(origin));

      if (!editor.isAirBlock(new Coord(0, 6, 0).add(origin))) {
        BlockType.get(BlockType.WATER_FLOWING).set(editor, new Coord(0, 7, 0).add(origin));
      }
      spawnHoleSet = true;
    }
  }

}
