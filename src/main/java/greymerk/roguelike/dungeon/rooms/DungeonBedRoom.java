package greymerk.roguelike.dungeon.rooms;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.Bed;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonBedRoom extends DungeonBase {

  public static void pillar(IWorldEditor editor, Random rand, Cardinal dir, ITheme theme, final Coord base) {
    Coord start = new Coord(base);
    Coord end = new Coord(base);

    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getPillar());
    IStair stair = theme.getSecondary().getStair();
    stair.setOrientation(Cardinal.reverse(dir), true);
    end.add(Cardinal.reverse(dir));
    stair.set(editor, end);
  }

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    ITheme theme = settings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal dir = entrances[0];

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(Cardinal.left(dir), 4);
    end.add(Cardinal.right(dir), 4);
    start.add(Cardinal.reverse(dir), 4);
    end.add(dir, 4);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.UP, 4);

    RectHollow.fill(editor, rand, start, end, theme.getPrimary().getWall(), false, true);

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 1);
    end.add(Cardinal.right(dir), 1);
    start.add(Cardinal.reverse(dir), 2);
    end.add(dir, 2);

    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      IStair stair = theme.getSecondary().getStair();
      stair.setOrientation(Cardinal.reverse(o), true);

      start = new Coord(origin);
      start.add(o, 3);
      end = new Coord(start);
      start.add(Cardinal.left(o), 2);
      end.add(Cardinal.right(o), 2);

      RectSolid.fill(editor, rand, start, end, stair);
      start.add(Cardinal.UP, 2);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, stair);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
      start.add(Cardinal.reverse(o));
      end.add(Cardinal.reverse(o));
      RectSolid.fill(editor, rand, start, end, stair, true, true);
    }

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(o, 3);
      pillar(editor, rand, o, theme, cursor);
      for (Cardinal p : Cardinal.orthogonal(o)) {
        Coord c = new Coord(cursor);
        c.add(p, 3);
        pillar(editor, rand, o, theme, c);
      }
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 3);
    cursor.add(Cardinal.reverse(dir), 3);

    for (int i = 0; i < 3; ++i) {
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.add(Cardinal.left(dir), 2);
      end.add(Cardinal.right(dir), 2);
      RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());
      cursor.add(dir, 3);
    }

    Cardinal side = rand.nextBoolean() ? Cardinal.left(dir) : Cardinal.right(dir);

    cursor = new Coord(origin);
    cursor.add(dir, 3);
    cursor.add(side, 1);
    DyeColor color = DyeColor.values()[rand.nextInt(DyeColor.values().length)];
    Bed.generate(editor, Cardinal.reverse(dir), cursor, color);
    cursor.add(side);
    BlockType.get(BlockType.SHELF).set(editor, cursor);
    cursor.add(Cardinal.UP);
    FlowerPot.generate(editor, rand, cursor);
    cursor.add(Cardinal.reverse(side), 3);
    cursor.add(Cardinal.DOWN);
    IStair stair = theme.getSecondary().getStair();
    stair.setOrientation(Cardinal.reverse(dir), true);
    stair.set(editor, cursor);
    cursor.add(Cardinal.UP);
    Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);

    side = Cardinal.orthogonal(dir)[rand.nextBoolean() ? 1 : 0];
    cursor = new Coord(origin);
    cursor.add(dir);
    cursor.add(side, 3);
    try {
      Treasure.generate(editor, rand, cursor, Treasure.STARTER, Dungeon.getLevel(cursor.getY()));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }
    cursor.add(Cardinal.reverse(side), 6);
    if (rand.nextBoolean()) {
      cursor.add(Cardinal.UP);
      Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
      cursor.add(Cardinal.DOWN);
      cursor.add(dir);
      BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);
    } else {
      BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);
      cursor.add(dir);
      cursor.add(Cardinal.UP);
      Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
      cursor.add(Cardinal.DOWN);
    }

    side = rand.nextBoolean() ? Cardinal.left(dir) : Cardinal.right(dir);
    cursor = new Coord(origin);
    cursor.add(Cardinal.reverse(dir));
    cursor.add(side, 3);
    if (rand.nextBoolean()) {
      cursor.add(Cardinal.reverse(dir));
    }
    Furnace.generate(editor, new ItemStack(Items.COAL, 2 + rand.nextInt(3)), true, Cardinal.reverse(side), cursor);


    return true;
  }

  @Override
  public int getSize() {
    return 5;
  }

  @Override
  public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    end = new Coord(start);
    start.add(Cardinal.reverse(dir), 5);
    end.add(dir, 5);
    start.add(Cardinal.left(dir), 5);
    end.add(Cardinal.right(dir), 5);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.UP, 3);

    for (Coord c : new RectHollow(start, end)) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }
}
