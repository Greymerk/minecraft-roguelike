package greymerk.roguelike.worldgen.redstone;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.init.Blocks;

import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public class Repeater {

  public static void generate(IWorldEditor editor, Random rand, Cardinal dir, int delay, Coord pos) {
    generate(editor, rand, dir, delay, false, pos);
  }

  public static void generate(IWorldEditor editor, Random rand, Cardinal dir, int delay, boolean powered, Coord pos) {

    MetaBlock repeater = powered ? new MetaBlock(Blocks.POWERED_REPEATER) : new MetaBlock(Blocks.UNPOWERED_REPEATER);
    repeater.withProperty(BlockRedstoneRepeater.FACING, Cardinal.facing(dir));
    if (delay > 0 && delay <= 4) {
      repeater.withProperty(BlockRedstoneRepeater.DELAY, delay);
    }
    repeater.set(editor, pos);
  }

}
