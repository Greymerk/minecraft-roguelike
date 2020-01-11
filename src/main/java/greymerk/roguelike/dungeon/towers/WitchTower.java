package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WitchTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    IBlockFactory blocks = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);
    MetaBlock glass = ColorBlock.get(ColorBlock.GLASS, DyeColor.BLACK);

    Coord main = Tower.getBaseCoord(editor, origin);

    Coord cursor;
    Coord start;
    Coord end;

    // main floor

    start = new Coord(main);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.WEST, 3);
    start.add(Cardinal.DOWN);
    end = new Coord(main);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.EAST, 3);
    end.add(Cardinal.UP, 3);

    RectHollow.fill(editor, rand, start, end, blocks);

    for (Cardinal dir : Cardinal.directions) {

      Cardinal[] orth = Cardinal.orthogonal(dir);

      start = new Coord(main);
      start.add(dir, 3);
      start.add(orth[0], 3);
      start.add(Cardinal.DOWN);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);

      RectSolid.fill(editor, rand, start, end, pillar);

      for (Cardinal o : orth) {
        start = new Coord(main);
        start.add(dir, 4);
        start.add(o, 2);
        start.add(Cardinal.DOWN);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);

        RectSolid.fill(editor, rand, start, end, pillar);
        cursor = new Coord(end);
        cursor.add(dir);
        stair.setOrientation(dir, true).set(editor, cursor);
        for (Cardinal d : orth) {
          cursor = new Coord(end);
          cursor.add(d);
          stair.setOrientation(d, true).set(editor, cursor);
        }
      }
    }

    // second floor

    Coord secondFloor = new Coord(main);
    secondFloor.add(Cardinal.UP, 4);

    start = new Coord(secondFloor);
    start.add(Cardinal.NORTH, 4);
    start.add(Cardinal.WEST, 4);
    start.add(Cardinal.DOWN);
    end = new Coord(secondFloor);
    end.add(Cardinal.SOUTH, 4);
    end.add(Cardinal.EAST, 4);
    end.add(Cardinal.UP, 6);

    RectHollow.fill(editor, rand, start, end, blocks);

    for (Cardinal dir : Cardinal.directions) {

      Cardinal[] orth = Cardinal.orthogonal(dir);

      start = new Coord(secondFloor);
      start.add(dir, 4);
      start.add(Cardinal.UP);
      end = new Coord(start);
      start.add(orth[0]);
      end.add(orth[1]);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, glass);

      start = new Coord(secondFloor);
      start.add(dir, 4);
      start.add(Cardinal.DOWN);
      start.add(orth[0], 4);
      end = new Coord(start);
      end.add(Cardinal.UP, 4);
      RectSolid.fill(editor, rand, start, end, air);

      for (Cardinal o : orth) {

        start = new Coord(secondFloor);
        start.add(Cardinal.DOWN);
        start.add(dir, 4);
        start.add(o, 3);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);

        start = new Coord(secondFloor);
        start.add(Cardinal.DOWN);
        start.add(dir, 5);
        start.add(o, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor = new Coord(end);
        cursor.add(dir);
        stair.setOrientation(dir, true).set(editor, cursor);
        for (Cardinal d : orth) {
          cursor = new Coord(end);
          cursor.add(d);
          stair.setOrientation(d, true).set(editor, cursor);
        }
      }
    }

    // third floor

    Coord thirdFloor = new Coord(secondFloor);
    thirdFloor.add(Cardinal.UP, 7);

    start = new Coord(thirdFloor);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.WEST, 3);
    start.add(Cardinal.DOWN);
    end = new Coord(thirdFloor);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.EAST, 3);
    end.add(Cardinal.UP, 4);

    RectHollow.fill(editor, rand, start, end, blocks);

    for (Cardinal dir : Cardinal.directions) {

      Cardinal[] orth = Cardinal.orthogonal(dir);

      cursor = new Coord(thirdFloor);
      cursor.add(dir, 3);
      cursor.add(Cardinal.UP);
      window(editor, rand, theme, dir, cursor);

      start = new Coord(thirdFloor);
      start.add(dir, 2);
      end = new Coord(start);
      end.add(dir, 4);
      end.add(Cardinal.DOWN);
      RectSolid.fill(editor, rand, start, end, blocks);

      start = new Coord(thirdFloor);
      start.add(dir, 5);
      start.add(Cardinal.DOWN, 2);
      end = new Coord(start);
      start.add(orth[0]);
      end.add(orth[1]);
      end.add(Cardinal.DOWN);
      RectSolid.fill(editor, rand, start, end, blocks);

      start = new Coord(thirdFloor);
      start.add(dir, 3);
      start.add(orth[0], 3);
      start.add(Cardinal.DOWN, 2);
      end = new Coord(start);
      end.add(Cardinal.UP, 5);
      RectSolid.fill(editor, rand, start, end, pillar);

      cursor = new Coord(end);
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, cursor);

      cursor = new Coord(end);
      cursor.add(orth[0]);
      stair.setOrientation(orth[0], true).set(editor, cursor);

      for (Cardinal o : orth) {

        start = new Coord(thirdFloor);
        start.add(dir, 4);
        start.add(Cardinal.DOWN);
        start.add(o, 3);
        end = new Coord(start);
        end.add(o);
        end.add(Cardinal.DOWN);
        RectSolid.fill(editor, rand, start, end, air);

        for (int i = 0; i < 4; ++i) {
          start = new Coord(thirdFloor);
          start.add(dir, 4);
          start.add(o, i + 1);
          start.add(Cardinal.DOWN, i);
          end = new Coord(start);
          end.add(dir, 2);
          stair.setOrientation(o, false);
          RectSolid.fill(editor, rand, start, end, stair);

          if (i < 3) {
            start = new Coord(thirdFloor);
            start.add(dir, 4);
            start.add(o, i + 1);
            start.add(Cardinal.DOWN, i + 1);
            end = new Coord(start);
            end.add(dir, 2);
            RectSolid.fill(editor, rand, start, end, blocks);
          }

          start = new Coord(thirdFloor);
          start.add(dir, 4);
          start.add(o, 2);
          start.add(Cardinal.DOWN, 3);
          end = new Coord(start);
          end.add(dir, 2);
          RectSolid.fill(editor, rand, start, end, blocks);

          cursor = new Coord(thirdFloor);
          cursor.add(dir, 6);
          cursor.add(o);
          cursor.add(Cardinal.DOWN, 2);
          stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
        }
      }
    }


    for (int i = thirdFloor.getY() - 1; i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(origin.getX(), i, origin.getZ()), theme.getPrimary().getStair(), theme.getPrimary().getPillar());
    }

    // attic

    Coord attic = new Coord(thirdFloor);
    attic.add(Cardinal.UP, 5);

    start = new Coord(attic);
    start.add(Cardinal.NORTH, 2);
    start.add(Cardinal.WEST, 2);
    start.add(Cardinal.DOWN);
    end = new Coord(attic);
    end.add(Cardinal.SOUTH, 2);
    end.add(Cardinal.EAST, 2);
    end.add(Cardinal.UP, 3);

    RectHollow.fill(editor, rand, start, end, blocks);

    start = new Coord(attic);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    start.add(Cardinal.NORTH);
    start.add(Cardinal.WEST);
    end.add(Cardinal.SOUTH);
    end.add(Cardinal.EAST);
    RectHollow.fill(editor, rand, start, end, blocks);

    start = new Coord(attic);
    start.add(Cardinal.UP, 5);
    end = new Coord(start);
    end.add(Cardinal.UP, 2);
    RectHollow.fill(editor, rand, start, end, blocks);

    for (Cardinal dir : Cardinal.directions) {

      Cardinal[] orth = Cardinal.orthogonal(dir);

      cursor = new Coord(attic);
      cursor.add(dir, 2);
      cursor.add(Cardinal.UP);
      window(editor, rand, theme, dir, cursor);

      stair.setOrientation(dir, false);

      start = new Coord(attic);
      start.add(dir, 3);
      end = new Coord(start);
      start.add(orth[0], 3);
      end.add(orth[1], 3);

      RectSolid.fill(editor, rand, start, end, stair);

      start = new Coord(attic);
      start.add(dir, 4);
      start.add(Cardinal.DOWN);
      end = new Coord(start);
      start.add(orth[0], 4);
      end.add(orth[1], 4);

      RectSolid.fill(editor, rand, start, end, stair);

      start = new Coord(attic);
      start.add(dir, 3);
      start.add(Cardinal.UP, 3);
      end = new Coord(start);
      start.add(orth[0], 3);
      end.add(orth[1], 3);

      RectSolid.fill(editor, rand, start, end, stair);

      start = new Coord(attic);
      start.add(dir, 2);
      start.add(Cardinal.UP, 4);
      end = new Coord(start);
      start.add(orth[0], 2);
      end.add(orth[1], 2);

      RectSolid.fill(editor, rand, start, end, stair);

    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(main);
      cursor.add(dir, 4);
      if (editor.isAirBlock(cursor)) {
        cursor = new Coord(main);
        cursor.add(dir, 3);
        theme.getPrimary().getDoor().generate(editor, cursor, dir);
        cursor.add(dir);
        start = new Coord(cursor);
        end = new Coord(start);
        end.add(Cardinal.UP);
        end.add(dir, 3);
        RectSolid.fill(editor, rand, start, end, air);

        cursor = new Coord(main);
        cursor.add(dir, 4);
        cursor.add(Cardinal.DOWN);
        step(editor, rand, theme, dir, cursor);
        break;
      }
    }


  }

  private void window(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    Coord cursor;

    IStair stair = theme.getPrimary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    cursor = new Coord(origin);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(o);
      stair.setOrientation(Cardinal.reverse(o), false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
    }
  }

  private void step(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    if (editor.getBlock(origin).isOpaqueCube()) {
      return;
    }

    Coord start;
    Coord end;

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory blocks = theme.getPrimary().getWall();

    Cardinal[] orth = Cardinal.orthogonal(dir);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(orth[0]);
    end.add(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.fill(editor, rand, start, end, blocks);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(orth[0]);
    end.add(orth[1]);
    stair.setOrientation(dir, false);
    RectSolid.fill(editor, rand, start, end, stair);

    origin.add(Cardinal.DOWN);
    origin.add(dir);
    step(editor, rand, theme, dir, origin);
  }
}
