package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class EniTower implements ITower {

  public EniTower() {
  }

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    MetaBlock air = BlockType.get(BlockType.AIR);

    IBlockFactory blocks = theme.getPrimary().getWall();

    IStair stair = theme.getPrimary().getStair();

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    int x = dungeon.getX();
    int z = dungeon.getZ();

    RectSolid.fill(editor, rand, new Coord(x - 4, floor.getY(), z - 4), new Coord(x + 4, floor.getY() + 3, z + 4), air);
    RectSolid.fill(editor, rand, new Coord(x - 3, floor.getY() + 4, z - 3), new Coord(x + 3, floor.getY() + 12, z + 3), air);
    RectSolid.fill(editor, rand, new Coord(x - 2, floor.getY() + 13, z - 2), new Coord(x + 2, floor.getY() + 21, z + 2), air);
    RectSolid.fill(editor, rand, new Coord(x - 3, floor.getY() + 22, z - 3), new Coord(x + 3, floor.getY() + 28, z + 3), air);

    Coord start;
    Coord end;
    Coord cursor;

    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : Cardinal.orthogonal(dir)) {

        start = new Coord(floor);
        end = new Coord(start);
        end.add(dir, 4);
        end.add(orth, 2);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.add(dir, 5);
        end = new Coord(start);
        start.add(orth);
        end.add(Cardinal.reverse(orth));
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.add(dir, 4);
        start.add(orth, 2);
        end = new Coord(start);
        end.add(orth);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.add(dir, 3);
        start.add(orth, 3);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, blocks);

        cursor = new Coord(floor);
        cursor.add(dir, 5);
        cursor.add(Cardinal.UP, 3);
        blocks.set(editor, rand, cursor);
        cursor.add(orth);
        stair.setOrientation(orth, false).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(orth);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.add(orth);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(Cardinal.reverse(orth));
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);
        cursor.add(dir);
        cursor.add(Cardinal.reverse(orth));
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);

        // second section

        start = new Coord(floor);
        start.add(Cardinal.UP, 4);
        start.add(dir, 4);
        end = new Coord(start);
        start.add(orth);
        end.add(Cardinal.reverse(orth));
        end.add(Cardinal.UP, 8);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.add(Cardinal.UP, 4);
        start.add(dir, 3);
        start.add(orth, 2);
        end = new Coord(start);
        end.add(orth);
        end.add(Cardinal.UP, 8);
        RectSolid.fill(editor, rand, start, end, blocks);

        cursor = new Coord(floor);
        cursor.add(Cardinal.UP, 13);
        cursor.add(dir, 4);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.add(orth);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(orth);
        stair.setOrientation(dir, false).set(editor, cursor);

        // section 3

        start = new Coord(floor);
        start.add(Cardinal.UP, 13);
        start.add(dir, 3);
        end = new Coord(start);
        start.add(orth);
        end.add(Cardinal.reverse(orth));
        end.add(Cardinal.UP, 8);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.add(Cardinal.UP, 13);
        start.add(dir, 2);
        start.add(orth, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 8);
        RectSolid.fill(editor, rand, start, end, blocks);

        // section 4

        start = new Coord(floor);
        start.add(Cardinal.UP, 22);
        start.add(dir, 4);
        end = new Coord(start);
        start.add(orth, 2);
        end.add(Cardinal.reverse(orth), 2);
        end.add(Cardinal.UP, 6);
        RectSolid.fill(editor, rand, start, end, blocks, true, false);

        start = new Coord(floor);
        start.add(Cardinal.UP, 22);
        start.add(dir, 3);
        start.add(orth, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 6);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.add(Cardinal.UP, 22);
        end = new Coord(start);
        end.add(dir, 3);
        end.add(orth, 2);
        RectSolid.fill(editor, rand, start, end, blocks);

        cursor = new Coord(floor);
        cursor.add(Cardinal.UP, 20);
        cursor.add(dir, 3);
        cursor.add(orth, 2);
        stair.setOrientation(dir, true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        blocks.set(editor, rand, cursor);
        cursor.add(dir);
        stair.setOrientation(dir, true).set(editor, cursor);

        // section 4 roof

        IStair roof = theme.getSecondary().getStair();
        start = new Coord(floor);
        start.add(Cardinal.UP, 29);
        start.add(dir, 3);
        end = new Coord(start);
        end.add(dir, 2);
        RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());
        start.add(orth);
        end.add(orth);
        RectSolid.fill(editor, rand, start, end, roof.setOrientation(orth, false));
        start.add(orth);
        end.add(orth);
        start.add(Cardinal.DOWN);
        end.add(Cardinal.DOWN);
        RectSolid.fill(editor, rand, start, end, roof.setOrientation(orth, false));
        start.add(orth);
        end.add(orth);
        start.add(Cardinal.DOWN);
        end.add(Cardinal.DOWN);
        RectSolid.fill(editor, rand, start, end, roof.setOrientation(orth, false));
        cursor = new Coord(end);
        cursor.add(Cardinal.reverse(orth));
        roof.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);
        cursor.add(Cardinal.reverse(orth));
        cursor.add(Cardinal.UP);
        roof.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);

        cursor.add(Cardinal.reverse(dir), 3);
        cursor.add(orth);
        theme.getSecondary().getWall().set(editor, rand, cursor);

        // tower top
        start = new Coord(floor);
        start.add(Cardinal.UP, 29);
        end = new Coord(start);
        start.add(dir, 2);
        start.add(orth);
        end.add(dir, 2);
        end.add(Cardinal.reverse(orth));
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, blocks);


        cursor = new Coord(floor);
        cursor.add(Cardinal.UP, 33);
        cursor.add(dir, 3);
        roof.setOrientation(dir, false).set(editor, cursor);
        cursor.add(orth);
        roof.setOrientation(dir, false).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(orth);
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.add(Cardinal.reverse(orth));
        cursor.add(Cardinal.UP);
        roof.setOrientation(orth, false).set(editor, cursor);
        cursor.add(Cardinal.reverse(orth));
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.add(Cardinal.UP);
        roof.setOrientation(dir, false).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.add(Cardinal.DOWN);
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.add(orth);
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.add(Cardinal.UP);
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.add(Cardinal.UP);
        cursor.add(Cardinal.reverse(orth));
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.add(Cardinal.UP);
        roof.setOrientation(dir, false).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.add(Cardinal.UP);
        theme.getSecondary().getWall().set(editor, rand, cursor);
      }
    }

    // mid floors
    start = new Coord(floor);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.EAST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.WEST, 3);

    RectSolid.fill(editor, rand, start, end, blocks);
    start.add(Cardinal.UP, 3);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, blocks);
    start.add(Cardinal.UP, 3);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, blocks);


    for (Cardinal dir : Cardinal.directions) {

      // lower windows
      cursor = new Coord(floor);
      cursor.add(dir, 4);
      cursor.add(Cardinal.UP, 4);
      MetaBlock window = ColorBlock.get(ColorBlock.PANE, rand);
      for (int i = 0; i < 3; i++) {
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.add(Cardinal.UP);
        window.set(editor, cursor);
        cursor.add(Cardinal.UP);
        window.set(editor, cursor);
        cursor.add(Cardinal.UP);
      }

      // floor before slit windows
      cursor.add(Cardinal.reverse(dir), 2);
      start = new Coord(cursor);
      start.add(Cardinal.left(dir));
      end = new Coord(cursor);
      end.add(Cardinal.right(dir));
      RectSolid.fill(editor, rand, start, end, blocks);

      // slit windows
      cursor = new Coord(floor);
      cursor.add(Cardinal.UP, 14);
      cursor.add(dir, 3);
      cursor.add(Cardinal.left(dir));
      stair.setOrientation(Cardinal.right(dir), false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.right(dir), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(Cardinal.right(dir));
      stair.setOrientation(Cardinal.left(dir), false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.left(dir), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.right(dir), false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.right(dir), true).set(editor, cursor);
      cursor.add(Cardinal.right(dir));
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.left(dir), false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.left(dir), true).set(editor, cursor);

      // top windows

      cursor = new Coord(floor);
      cursor.add(Cardinal.UP, 23);
      cursor.add(dir, 4);
      window.set(editor, cursor);
      cursor.add(Cardinal.UP);
      window.set(editor, cursor);
      cursor.add(Cardinal.UP);
      window.set(editor, cursor);
      cursor.add(Cardinal.DOWN);
      cursor.add(Cardinal.left(dir));
      window.set(editor, cursor);
      cursor.add(Cardinal.right(dir), 2);
      window.set(editor, cursor);

      // top ceiling
      cursor = new Coord(floor);
      cursor.add(Cardinal.UP, 26);
      cursor.add(dir, 3);
      start = new Coord(cursor);
      start.add(Cardinal.left(dir));
      end = new Coord(cursor);
      end.add(Cardinal.right(dir));
      RectSolid.fill(editor, rand, start, end, blocks);
      start.add(Cardinal.reverse(dir));
      end.add(Cardinal.reverse(dir));
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, blocks);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, blocks);

      cursor.add(Cardinal.reverse(dir));
      cursor.add(Cardinal.left(dir), 2);
      blocks.set(editor, rand, cursor);
    }

    start = new Coord(x - 4, 60, z - 4);
    end = new Coord(x + 4, floor.getY(), z + 4);
    RectSolid.fill(editor, rand, start, end, blocks);

    for (int i = (floor.getY() + 22); i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getPrimary().getPillar());
    }


    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(floor);
      cursor.add(Cardinal.UP);
      cursor.add(dir, 6);
      if (editor.isAirBlock(cursor)) {
        cursor = new Coord(floor);
        cursor.add(Cardinal.UP);
        cursor.add(dir, 5);
        theme.getPrimary().getDoor().generate(editor, cursor, dir, false);
        cursor.add(dir);
        start = new Coord(cursor);
        end = new Coord(start);
        end.add(Cardinal.UP);
        end.add(dir, 3);
        RectSolid.fill(editor, rand, start, end, air);
        break;
      }
    }


  }
}
