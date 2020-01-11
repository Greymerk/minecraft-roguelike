package greymerk.roguelike.worldgen.redstone;


import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public enum Torch {

  REDSTONE,
  WOODEN,
  REDSTONE_UNLIT;

  public static void generate(IWorldEditor editor, Torch type, Cardinal dir, Coord pos) {

    Block name;

    switch (type) {
      case WOODEN:
        name = Blocks.TORCH;
        break;
      case REDSTONE:
        name = Blocks.REDSTONE_TORCH;
        break;
      case REDSTONE_UNLIT:
        name = Blocks.UNLIT_REDSTONE_TORCH;
        break;
      default:
        name = Blocks.TORCH;
        break;
    }

    MetaBlock torch = new MetaBlock(name);
    if (dir == Cardinal.UP) {
      torch.withProperty(BlockTorch.FACING, EnumFacing.UP);
    } else if (dir == Cardinal.DOWN) {
      torch.withProperty(BlockTorch.FACING, EnumFacing.DOWN);
    } else {
      torch.withProperty(BlockTorch.FACING, Cardinal.facing(Cardinal.reverse(dir)));
    }


    torch.set(editor, pos);

  }

}
