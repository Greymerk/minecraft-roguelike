package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockEnderChest;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;


public class EnderChest {
  public static void set(IWorldEditor editor, Cardinal dir, Coord pos) {

    EnumFacing facing = Arrays.asList(Cardinal.directions).contains(dir)
        ? Cardinal.facing(Cardinal.reverse(dir))
        : Cardinal.facing(Cardinal.SOUTH);

    MetaBlock chest = new MetaBlock(Blocks.ENDER_CHEST);
    chest.withProperty(BlockEnderChest.FACING, facing);
    chest.set(editor, pos);
  }
}
