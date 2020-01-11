package greymerk.roguelike.dungeon.rooms;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.Firework;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.redstone.Comparator;
import greymerk.roguelike.worldgen.redstone.Dispenser;
import greymerk.roguelike.worldgen.redstone.Dropper;
import greymerk.roguelike.worldgen.redstone.Hopper;
import greymerk.roguelike.worldgen.redstone.Lever;
import greymerk.roguelike.worldgen.redstone.Repeater;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonFirework extends DungeonBase {

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    MetaBlock breadboard = ColorBlock.get(ColorBlock.CLAY, DyeColor.GREEN);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal dir = entrances[0];
    start = new Coord(x, y, z);
    end = new Coord(start);
    start.add(Cardinal.reverse(dir), 9);
    end.add(dir, 9);
    start.add(Cardinal.left(dir), 4);
    end.add(Cardinal.right(dir), 4);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.UP, 3);
    RectHollow.fill(editor, rand, start, end, ColorBlock.get(ColorBlock.CLAY, DyeColor.ORANGE), false, true);

    start = new Coord(x, y, z);
    start.add(Cardinal.left(dir), 2);
    end = new Coord(start);
    start.add(Cardinal.reverse(dir), 3);
    end.add(dir, 7);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, breadboard);

    start.add(Cardinal.right(dir), 2);
    end.add(Cardinal.right(dir), 2);
    RectSolid.fill(editor, rand, start, end, breadboard, true, true);

    start.add(Cardinal.right(dir), 2);
    end.add(Cardinal.right(dir), 2);
    RectSolid.fill(editor, rand, start, end, breadboard, true, true);

    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.left(dir), 2);

    launcher(editor, rand, dir, cursor);
    cursor.add(Cardinal.right(dir), 2);
    launcher(editor, rand, dir, cursor);
    cursor.add(Cardinal.right(dir), 2);
    launcher(editor, rand, dir, cursor);
    cursor.add(dir, 6);
    launcher(editor, rand, dir, cursor);
    cursor.add(Cardinal.left(dir), 2);
    launcher(editor, rand, dir, cursor);
    cursor.add(Cardinal.left(dir), 2);
    launcher(editor, rand, dir, cursor);

    start = new Coord(x, y, z);
    start.add(dir, 4);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    end.add(dir, 2);
    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR), true, true);

    cursor = new Coord(x, y, z);
    cursor.add(dir, 2);
    Repeater.generate(editor, rand, dir, 0, cursor);
    cursor.add(Cardinal.left(dir), 2);
    Repeater.generate(editor, rand, dir, 0, cursor);
    cursor.add(Cardinal.right(dir), 4);
    Repeater.generate(editor, rand, dir, 0, cursor);

    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.reverse(dir), 3);
    cursor.add(Cardinal.left(dir));
    Repeater.generate(editor, rand, Cardinal.left(dir), 0, cursor);
    cursor.add(Cardinal.right(dir), 2);
    Repeater.generate(editor, rand, Cardinal.right(dir), 0, cursor);

    MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);

    start = new Coord(x, y, z);
    start.add(Cardinal.DOWN, 2);
    start.add(Cardinal.right(dir));
    start.add(Cardinal.reverse(dir), 2);
    end = new Coord(start);
    end.add(Cardinal.left(dir), 5);
    end.add(Cardinal.reverse(dir), 5);
    end.add(Cardinal.DOWN, 2);
    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.COBBLESTONE), true, true);

    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.reverse(dir), 3);
    cursor.add(Cardinal.DOWN);
    Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
    cursor.add(Cardinal.DOWN);
    breadboard.set(editor, cursor);
    cursor.add(Cardinal.left(dir));
    Torch.generate(editor, Torch.REDSTONE_UNLIT, Cardinal.left(dir), cursor);
    cursor.add(Cardinal.left(dir));
    wire.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    wire.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    wire.set(editor, cursor);
    cursor.add(Cardinal.right(dir));
    wire.set(editor, cursor);
    cursor.add(Cardinal.right(dir));
    wire.set(editor, cursor);
    cursor.add(dir);
    Repeater.generate(editor, rand, dir, 4, true, cursor);
    cursor.add(Cardinal.UP);
    cursor.add(Cardinal.reverse(dir));
    ColorBlock.get(ColorBlock.CLAY, DyeColor.RED).set(editor, cursor);
    cursor.add(Cardinal.UP);
    Lever.generate(editor, Cardinal.UP, cursor, true);

    MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);
    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.reverse(dir), 5);
    cursor.add(Cardinal.UP, 3);
    glowstone.set(editor, cursor);
    cursor.add(dir, 4);
    glowstone.set(editor, cursor);
    cursor.add(dir, 6);
    glowstone.set(editor, cursor);

    return false;
  }


  private void launcher(IWorldEditor editor, Random rand, Cardinal dir, Coord pos) {
    Coord cursor = new Coord(pos);
    BlockType.get(BlockType.REDSTONE_WIRE).set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    BlockType.get(BlockType.REDSTONE_WIRE).set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    Repeater.generate(editor, rand, dir, 0, cursor);
    cursor.add(Cardinal.reverse(dir));
    cursor.add(Cardinal.UP);

    Dropper dropper = new Dropper();
    dropper.generate(editor, Cardinal.UP, cursor);
    for (int i = 0; i < 8; ++i) {
      ItemStack stick = new ItemStack(Items.STICK, 1);
      Loot.setItemName(stick, Integer.toString(i));
      Loot.setItemLore(stick, "Random logic unit", TextFormat.DARKGRAY);
      dropper.add(editor, cursor, i, stick);
    }
    dropper.add(editor, cursor, 8, new ItemStack(Items.WOODEN_HOE));

    cursor.add(Cardinal.UP);
    Hopper.generate(editor, Cardinal.DOWN, cursor);
    cursor.add(dir);
    Comparator.generate(editor, rand, dir, false, cursor);
    cursor.add(dir);
    BlockType.get(BlockType.REDSTONE_WIRE).set(editor, cursor);
    cursor.add(dir);
    BlockType.get(BlockType.REDSTONE_WIRE).set(editor, cursor);
    cursor.add(dir);

    Coord top = new Coord(pos.getX(), 80, pos.getZ());
    while (top.getY() > pos.getY()) {
      top.add(Cardinal.DOWN);
      if (editor.getBlock(top).getMaterial().isSolid()) {
        break;
      }
    }

    if (top.getY() >= 100) {
      return;
    }

    Coord start = new Coord(cursor);
    start.add(Cardinal.UP);


    start.add(dir);
    Coord end = new Coord(start);

    MetaBlock breadboard = ColorBlock.get(ColorBlock.CLAY, DyeColor.GREEN);


    boolean torch = false;
    while (end.getY() < top.getY()) {
      if (torch) {
        Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
      } else {
        breadboard.set(editor, cursor);
      }
      torch = !torch;
      cursor.add(Cardinal.UP);
      end.add(Cardinal.UP);
    }

    if (torch) {
      cursor.add(Cardinal.DOWN);
    }

    Dispenser.generate(editor, Cardinal.UP, cursor);
    for (int i = 0; i < 9; i++) {
      Dispenser.add(editor, cursor, i, Firework.get(rand, 16 + rand.nextInt(16)));
    }

    cursor.add(Cardinal.UP);
    MetaBlock cob = BlockType.get(BlockType.COBBLESTONE);
    RectSolid.fill(editor, rand, start, end, cob, true, true);
    start.add(Cardinal.reverse(dir), 2);
    end.add(Cardinal.reverse(dir), 2);
    RectSolid.fill(editor, rand, start, end, cob, true, true);
    start.add(dir);
    end.add(dir);
    Coord above = new Coord(end);
    above.add(Cardinal.UP, 10);
    MetaBlock air = BlockType.get(BlockType.AIR);
    for (Coord c : new RectSolid(cursor, above)) {
      if (editor.getBlock(c).getMaterial().isSolid()) {
        air.set(editor, c);
      }
    }
    start.add(Cardinal.left(dir));
    end.add(Cardinal.left(dir));
    RectSolid.fill(editor, rand, start, end, cob, true, true);
    start.add(Cardinal.right(dir), 2);
    end.add(Cardinal.right(dir), 2);
    RectSolid.fill(editor, rand, start, end, cob, true, true);
  }


  @Override
  public int getSize() {
    return 10;
  }

  @Override
  public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    end = new Coord(start);
    start.add(Cardinal.reverse(dir), 9);
    end.add(dir, 9);
    Cardinal[] orth = Cardinal.orthogonal(dir);
    start.add(orth[0], 5);
    end.add(orth[1], 5);
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
