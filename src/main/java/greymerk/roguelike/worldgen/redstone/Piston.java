package greymerk.roguelike.worldgen.redstone;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public class Piston {

  public static void generate(IWorldEditor editor, Coord origin, Cardinal dir, boolean sticky) {

    MetaBlock piston = new MetaBlock(sticky ? Blocks.STICKY_PISTON : Blocks.PISTON);
    piston.withProperty(BlockPistonBase.FACING, Cardinal.facing(Cardinal.reverse(dir)));
    piston.set(editor, origin);
  }

}
