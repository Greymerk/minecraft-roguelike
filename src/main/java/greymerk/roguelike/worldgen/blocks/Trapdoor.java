package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;

public enum Trapdoor {

  OAK,
  IRON;

  public static MetaBlock get(Trapdoor type, Cardinal dir, boolean bottom, boolean open) {

    MetaBlock block;

    switch (type) {
      case OAK:
        block = new MetaBlock(Blocks.TRAPDOOR);
        break;
      case IRON:
        block = new MetaBlock(Blocks.IRON_TRAPDOOR);
        break;
      default:
        block = new MetaBlock(Blocks.TRAPDOOR);
        break;
    }

    block.withProperty(BlockTrapDoor.FACING, Cardinal.facing(dir));

    if (bottom) {
      block.withProperty(BlockTrapDoor.HALF, BlockTrapDoor.DoorHalf.BOTTOM);
    }

    if (open) {
      block.withProperty(BlockTrapDoor.OPEN, true);
    }

    return block;

  }

}
