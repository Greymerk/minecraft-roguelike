package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Bed;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Cake;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.Stair;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class HouseTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    IBlockFactory walls = theme.getPrimary().getWall();
    IBlockFactory mainFloor = theme.getPrimary().getFloor();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Cardinal dir = Cardinal.directions[(floor.getY() + 2) % 4];

    Coord cursor;
    Coord start;
    Coord end;

    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    floor.add(Cardinal.UP);

    start = new Coord(floor);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    start.add(Cardinal.right(dir), 3);
    start.add(dir, 3);
    end.add(Cardinal.UP, 8);
    end.add(Cardinal.reverse(dir), 7);
    end.add(Cardinal.left(dir), 10);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(floor);
    start.add(Cardinal.right(dir), 2);
    start.add(Cardinal.DOWN);
    end = new Coord(floor);
    end.add(Cardinal.UP, 3);
    end.add(Cardinal.left(dir), 8);
    end.add(Cardinal.reverse(dir), 5);
    RectSolid.fill(editor, rand, new Coord(x - 2, floor.getY() + 3, z - 2), new Coord(x + 2, y + 10, z + 2), walls);
    RectHollow.fill(editor, rand, start, end, walls);

    cursor = new Coord(floor);
    cursor.add(Cardinal.left(dir), 6);
    cursor.add(Cardinal.reverse(dir), 6);
    door(editor, rand, theme, dir, cursor);

    start = new Coord(floor);
    start.add(Cardinal.DOWN);
    start.add(Cardinal.right(dir));
    start.add(Cardinal.reverse(dir));
    end = new Coord(floor);
    end.add(Cardinal.DOWN);
    end.add(Cardinal.reverse(dir), 4);
    end.add(Cardinal.left(dir), 7);
    RectSolid.fill(editor, rand, start, end, mainFloor);

    start = new Coord(floor);
    start.add(Cardinal.DOWN, 2);
    start.add(Cardinal.right(dir), 2);
    start.add(Cardinal.reverse(dir), 2);
    end = new Coord(floor.getX(), y + 10, floor.getZ());
    end.add(Cardinal.reverse(dir), 5);
    end.add(Cardinal.left(dir), 8);
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(floor);
    cursor.add(Cardinal.reverse(dir), 5);
    cursor.add(Cardinal.right(dir), 2);
    support(editor, rand, theme, new Cardinal[]{Cardinal.reverse(dir), Cardinal.right(dir)}, cursor);
    cursor.add(dir, 7);
    support(editor, rand, theme, new Cardinal[]{dir, Cardinal.right(dir)}, cursor);
    cursor.add(Cardinal.left(dir), 4);
    support(editor, rand, theme, new Cardinal[]{dir, Cardinal.left(dir)}, cursor);
    cursor.add(Cardinal.left(dir), 6);
    cursor.add(Cardinal.reverse(dir), 2);
    support(editor, rand, theme, new Cardinal[]{dir, Cardinal.left(dir)}, cursor);

    upperFloor(editor, rand, theme, dir, new Coord(x, floor.getY() + 3, z));
    roof(editor, rand, theme, dir, new Coord(x, floor.getY() + 4, z));
    upperWalls(editor, rand, theme, dir, new Coord(x, floor.getY() + 4, z));
    windows(editor, rand, theme, dir, floor);
    decor(editor, rand, theme, dir, floor);

    cursor = new Coord(floor);
    cursor.add(Cardinal.UP, 3);
    for (int i = floor.getY() + 3; i >= y; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getSecondary().getPillar());
    }
  }

  private void decor(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    IStair stair = Stair.get(StairType.OAK);
    MetaBlock slab = Slab.get(Slab.OAK, true, false, false);
    Cardinal[] orth = Cardinal.orthogonal(dir);
    Coord cursor;
    Coord start;
    Coord end;

    // downstairs table
    cursor = new Coord(origin);
    cursor.add(Cardinal.reverse(dir), 4);
    stair.setOrientation(orth[1], true).set(editor, cursor);
    cursor.add(orth[0]);
    slab.set(editor, cursor);
    cursor.add(orth[0]);
    stair.setOrientation(orth[0], true).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(orth[0], 4);
    cursor.add(Cardinal.reverse(dir));
    stair.setOrientation(orth[1], true).set(editor, cursor);
    cursor.add(orth[0]);
    slab.set(editor, cursor);
    cursor.add(orth[0]);
    stair.setOrientation(orth[0], true).set(editor, cursor);
    cursor.add(orth[1]);
    cursor.add(Cardinal.UP);
    Cake.get().set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(orth[0], 7);
    cursor.add(Cardinal.reverse(dir));
    slab.set(editor, cursor);
    cursor.add(Cardinal.UP);
    Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
    cursor.add(Cardinal.DOWN);
    cursor.add(Cardinal.reverse(dir));
    BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    Furnace.generate(editor, true, orth[1], cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    cursor.add(orth[1], 2);
    cursor.add(Cardinal.reverse(dir), 3);
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    FlowerPot.generate(editor, rand, cursor);
    cursor.add(dir);
    FlowerPot.generate(editor, rand, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    cursor.add(orth[0]);
    cursor.add(Cardinal.reverse(dir), 5);
    stair.setOrientation(orth[1], true).set(editor, cursor);
    cursor.add(orth[0]);
    slab.set(editor, cursor);
    cursor.add(orth[0]);
    stair.setOrientation(orth[0], true).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    cursor.add(orth[0], 8);
    try {
      Treasure.generate(editor, rand, cursor, Treasure.STARTER, 0);
    } catch (ChestPlacementException cpe) {
      // do nothing
    }
    cursor.add(Cardinal.reverse(dir));
    BlockType.get(BlockType.SHELF).set(editor, cursor);
    cursor.add(Cardinal.UP);
    FlowerPot.generate(editor, rand, cursor);
    cursor.add(Cardinal.DOWN);
    cursor.add(Cardinal.reverse(dir));
    Bed.generate(editor, orth[1], cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    cursor.add(Cardinal.reverse(dir));
    cursor.add(orth[0]);
    start = new Coord(cursor);
    end = new Coord(start);
    end.add(orth[0], 5);
    end.add(Cardinal.reverse(dir), 3);
    BlockStripes carpet = new BlockStripes();
    carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
    carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
    carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
    RectSolid.fill(editor, rand, start, end, carpet);


  }


  private void windows(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    MetaBlock pane = ColorBlock.get(ColorBlock.PANE, DyeColor.LIGHT_GRAY);
    Cardinal[] orth = Cardinal.orthogonal(dir);
    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    cursor.add(Cardinal.reverse(dir), 5);
    cursor.add(Cardinal.UP);
    pane.set(editor, cursor);
    cursor.add(orth[0], 2);
    pane.set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    cursor.add(orth[0], 8);
    cursor.add(Cardinal.reverse(dir), 2);
    pane.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir), 2);
    pane.set(editor, cursor);

    // upstairs
    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 5);
    cursor.add(orth[0]);
    cursor.add(dir, 3);
    pane.set(editor, cursor);
    cursor.add(orth[1], 2);
    pane.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir), 2);
    cursor.add(orth[1], 2);
    pane.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    pane.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir), 3);
    pane.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    pane.set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 5);
    cursor.add(orth[0], 9);
    cursor.add(Cardinal.reverse(dir));
    pane.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    pane.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir), 2);
    pane.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    pane.set(editor, cursor);
    cursor.add(Cardinal.UP, 2);
    cursor.add(dir);
    start = new Coord(cursor);
    end = new Coord(start);
    end.add(Cardinal.UP);
    end.add(dir, 2);
    RectSolid.fill(editor, rand, start, end, pane);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    cursor.add(orth[0], 5);
    cursor.add(Cardinal.reverse(dir), 7);
    start = new Coord(cursor);
    end = new Coord(start);
    end.add(orth[0], 2);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, pane);
  }

  private void roof(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory walls = theme.getSecondary().getWall();
    IStair stair = theme.getSecondary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    cursor.add(Cardinal.right(dir), 4);
    cursor.add(dir, 4);
    cursor.add(Cardinal.UP, 2);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.add(Cardinal.reverse(dir), 10);
    stair.setOrientation(Cardinal.right(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.left(dir));
    end.add(Cardinal.left(dir));
    stair.setOrientation(Cardinal.left(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    stair.setOrientation(Cardinal.right(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.left(dir));
    end.add(Cardinal.left(dir));
    stair.setOrientation(Cardinal.left(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    stair.setOrientation(Cardinal.right(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.left(dir));
    end.add(Cardinal.left(dir));
    end.add(dir);
    stair.setOrientation(Cardinal.left(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    stair.setOrientation(Cardinal.right(dir), false).fill(editor, rand, new RectSolid(start, end));

    start.add(Cardinal.left(dir));
    end.add(Cardinal.left(dir));
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.add(Cardinal.reverse(dir), 3);
    cursor.add(Cardinal.UP, 5);
    stair.setOrientation(Cardinal.left(dir), true).set(editor, cursor);
    cursor.add(dir);
    stair.setOrientation(Cardinal.left(dir), true).set(editor, cursor);
    cursor.add(Cardinal.DOWN);
    cursor.add(Cardinal.left(dir));
    cursor.add(dir);
    stair.setOrientation(Cardinal.right(dir), true).set(editor, cursor);
    cursor.add(Cardinal.DOWN);
    cursor.add(Cardinal.left(dir));
    cursor.add(dir);
    stair.setOrientation(Cardinal.right(dir), true).set(editor, cursor);

    start.add(Cardinal.left(dir));
    end.add(Cardinal.left(dir));
    end.add(dir, 5);
    stair.setOrientation(Cardinal.left(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.DOWN);
    end.add(Cardinal.DOWN);
    stair.setOrientation(Cardinal.right(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.left(dir));
    end.add(Cardinal.left(dir));
    end.add(dir);
    stair.setOrientation(Cardinal.left(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.DOWN);
    end.add(Cardinal.DOWN);
    stair.setOrientation(Cardinal.right(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.left(dir));
    end.add(Cardinal.left(dir));
    end.add(dir);
    stair.setOrientation(Cardinal.left(dir), false).fill(editor, rand, new RectSolid(start, end));

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 2);
    cursor.add(dir, 2);
    cursor.add(Cardinal.left(dir), 10);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.add(Cardinal.right(dir), 6);
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.reverse(dir));
    end.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    end.add(Cardinal.right(dir));
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.reverse(dir));
    end.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    end.add(Cardinal.right(dir));
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.reverse(dir));
    end.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    end.add(Cardinal.right(dir));
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.reverse(dir));
    end.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));

    start.add(Cardinal.reverse(dir));
    end.add(Cardinal.reverse(dir));
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(end);
    end.add(Cardinal.reverse(dir), 2);
    start.add(Cardinal.right(dir));
    end.add(Cardinal.right(dir));
    start.add(dir);
    end.add(dir);
    stair.setOrientation(Cardinal.right(dir), false).fill(editor, rand, new RectSolid(start, end));

    cursor.add(Cardinal.reverse(dir), 10);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.add(Cardinal.right(dir), 7);
    stair.setOrientation(Cardinal.reverse(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(dir);
    end.add(dir);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    end.add(Cardinal.right(dir), 5);
    stair.setOrientation(Cardinal.reverse(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(dir);
    end.add(dir);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    end.add(Cardinal.left(dir));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    stair.setOrientation(Cardinal.reverse(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(dir);
    end.add(dir);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    end.add(Cardinal.left(dir));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    stair.setOrientation(Cardinal.reverse(dir), false).fill(editor, rand, new RectSolid(start, end));
    start.add(dir);
    end.add(dir);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    end.add(Cardinal.left(dir));
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    stair.setOrientation(Cardinal.reverse(dir), false).fill(editor, rand, new RectSolid(start, end));
  }

  private void upperFloor(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory floor = theme.getPrimary().getFloor();
    Cardinal[] orth = Cardinal.orthogonal(dir);
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(orth[1], 3);
    start.add(dir, 3);
    end = new Coord(origin);
    end.add(orth[0], 3);
    end.add(Cardinal.reverse(dir), 6);
    RectSolid.fill(editor, rand, start, end, floor);

    start = new Coord(origin);
    start.add(orth[0], 3);
    start.add(dir);
    end = new Coord(origin);
    end.add(Cardinal.reverse(dir), 7);
    end.add(orth[0], 9);
    RectSolid.fill(editor, rand, start, end, floor);
  }

  private void upperWalls(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory walls = theme.getPrimary().getWall();
    Cardinal[] orth = Cardinal.orthogonal(dir);
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(orth[1], 3);
    start.add(dir, 2);
    end = new Coord(start);
    end.add(Cardinal.reverse(dir), 7);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.add(orth[1], 2);
    start.add(dir, 3);
    end = new Coord(start);
    end.add(orth[0], 4);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, walls);
    end.add(Cardinal.UP);
    end.add(orth[1]);
    start = new Coord(end);
    start.add(orth[1], 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.add(orth[0], 3);
    start.add(dir, 2);
    end = new Coord(start);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.add(orth[0], 4);
    start.add(dir);
    end = new Coord(start);
    end.add(orth[0], 4);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.add(orth[0], 9);
    end = new Coord(start);
    end.add(Cardinal.reverse(dir), 6);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, walls);
    end.add(Cardinal.UP);
    end.add(dir);
    start = new Coord(end);
    start.add(dir, 4);
    RectSolid.fill(editor, rand, start, end, walls);
    end.add(Cardinal.UP);
    end.add(dir);
    start = new Coord(end);
    start.add(dir, 2);
    RectSolid.fill(editor, rand, start, end, walls);


    start = new Coord(origin);
    start.add(Cardinal.reverse(dir), 7);
    start.add(orth[0], 4);
    end = new Coord(start);
    end.add(orth[0], 4);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.add(Cardinal.reverse(dir), 6);
    start.add(orth[1], 2);
    end = new Coord(start);
    end.add(orth[0], 4);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.add(orth[1], 3);
    cursor.add(dir, 3);
    pillar(editor, rand, theme, 3, cursor);
    cursor.add(orth[0], 6);
    pillar(editor, rand, theme, 3, cursor);
    cursor.add(Cardinal.reverse(dir), 2);
    pillar(editor, rand, theme, 3, cursor);
    cursor.add(orth[0], 6);
    pillar(editor, rand, theme, 3, cursor);
    cursor.add(Cardinal.reverse(dir), 8);
    pillar(editor, rand, theme, 3, cursor);
    cursor.add(orth[1], 6);
    pillar(editor, rand, theme, 3, cursor);
    cursor.add(dir);
    pillar(editor, rand, theme, 3, cursor);
    cursor.add(orth[1], 6);
    pillar(editor, rand, theme, 3, cursor);
  }

  private void pillar(IWorldEditor editor, Random rand, ITheme theme, int height, Coord start) {
    IBlockFactory pillar = theme.getPrimary().getPillar();
    Coord end;
    end = new Coord(start);
    end.add(Cardinal.UP, height - 1);
    RectSolid.fill(editor, rand, start, end, pillar);
  }

  private void support(IWorldEditor editor, Random rand, ITheme theme, Cardinal[] dirs, Coord origin) {
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, pillar);
    cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    editor.fillDown(rand, cursor, pillar);

    for (Cardinal dir : dirs) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 2);
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, cursor);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        stair.setOrientation(o, true).set(editor, rand, c, true, false);
      }
    }

  }

  private void door(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    IBlockFactory floor = theme.getPrimary().getFloor();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = Cardinal.orthogonal(dir);

    start = new Coord(origin);
    start.add(Cardinal.reverse(dir));
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    end.add(Cardinal.reverse(dir), 2);
    end.add(Cardinal.UP, 6);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(start);
    start.add(Cardinal.DOWN);
    start.add(orth[0]);
    end.add(Cardinal.UP, 2);
    end.add(orth[1]);
    RectSolid.fill(editor, rand, start, end, floor);

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(Cardinal.reverse(dir));
    end.add(dir);
    start.add(orth[0]);
    end.add(orth[1]);
    RectSolid.fill(editor, rand, start, end, floor);

    start = new Coord(origin);
    start.add(Cardinal.DOWN, 2);
    end = new Coord(start);
    start.add(Cardinal.reverse(dir));
    end.add(dir);
    start.add(orth[0]);
    end.add(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.fill(editor, rand, start, end, floor, true, false);

    theme.getPrimary().getDoor().generate(editor, origin, Cardinal.reverse(dir), false);

    for (Cardinal o : orth) {

      cursor = new Coord(origin);
      cursor.add(o, 2);
      cursor.add(Cardinal.UP, 2);
      editor.fillDown(rand, cursor, pillar);

      cursor = new Coord(end);
      cursor.add(o);
      stair.setOrientation(o, true).set(editor, cursor);
      cursor.add(Cardinal.reverse(dir));
      stair.setOrientation(o, true).set(editor, cursor);
      cursor.add(Cardinal.reverse(o));
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.reverse(o));
      stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 2);
    cursor.add(orth[0], 3);
    cursor.add(dir);
    stair.setOrientation(dir, true).set(editor, cursor);

    start = new Coord(origin);
    start.add(dir);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    cursor.add(Cardinal.reverse(dir), 2);
    step(editor, rand, theme, Cardinal.reverse(dir), cursor);
  }

  private void step(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    Coord start;
    Coord end;
    Coord cursor;

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory blocks = theme.getPrimary().getWall();

    cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    cursor.add(dir);
    if (editor.validGroundBlock(cursor)) {
      return;
    }
    if (cursor.getY() <= 60) {
      return;
    }

    Cardinal[] orth = Cardinal.orthogonal(dir);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(orth[0]);
    end.add(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.fill(editor, rand, start, end, blocks, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(orth[0]);
    end.add(orth[1]);
    stair.setOrientation(dir, false);
    RectSolid.fill(editor, rand, start, end, stair, true, true);

    origin.add(Cardinal.DOWN);
    origin.add(dir);
    step(editor, rand, theme, dir, origin);
  }
}
