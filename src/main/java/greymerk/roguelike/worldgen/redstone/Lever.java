package greymerk.roguelike.worldgen.redstone;

import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLever.EnumOrientation;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public class Lever {

  public static void generate(IWorldEditor editor, Cardinal dir, Coord pos, boolean active) {

    MetaBlock lever = new MetaBlock(Blocks.LEVER);
    lever.withProperty(BlockLever.POWERED, active);
    if (dir == Cardinal.UP) {
      lever.withProperty(BlockLever.FACING, EnumOrientation.UP_X);
    } else if (dir == Cardinal.DOWN) {
      lever.withProperty(BlockLever.FACING, EnumOrientation.DOWN_X);
    } else {
      lever.withProperty(BlockLever.FACING, Cardinal.orientation(Cardinal.reverse(dir)));
    }
    lever.set(editor, pos);
  }

}
