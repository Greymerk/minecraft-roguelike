package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.MetaBlock;

public class Leaves {

  public static MetaBlock get(Wood type, boolean decay) {

    Block base = getBlockId(type);

    MetaBlock leaf = new MetaBlock(base);

    // Original minecraft leaves use a different variant property than
    // newer leaves like acacia and dark oak.
    if (base == Blocks.LEAVES) {
      leaf.withProperty(BlockOldLeaf.VARIANT, getType(type));
    } else {
      leaf.withProperty(BlockNewLeaf.VARIANT, getType(type));
    }

    leaf.withProperty(BlockLeaves.DECAYABLE, decay);

    return leaf;

  }

  public static Block getBlockId(Wood type) {
    switch (type) {
      case OAK:
        return Blocks.LEAVES;
      case SPRUCE:
        return Blocks.LEAVES;
      case BIRCH:
        return Blocks.LEAVES;
      case JUNGLE:
        return Blocks.LEAVES;
      case ACACIA:
        return Blocks.LEAVES2;
      case DARKOAK:
        return Blocks.LEAVES2;
      default:
        return Blocks.LOG;
    }
  }

  private static BlockPlanks.EnumType getType(Wood type) {

    switch (type) {
      case OAK:
        return BlockPlanks.EnumType.OAK;
      case SPRUCE:
        return BlockPlanks.EnumType.SPRUCE;
      case BIRCH:
        return BlockPlanks.EnumType.BIRCH;
      case JUNGLE:
        return BlockPlanks.EnumType.JUNGLE;
      case ACACIA:
        return BlockPlanks.EnumType.ACACIA;
      case DARKOAK:
        return BlockPlanks.EnumType.DARK_OAK;
      default:
        return BlockPlanks.EnumType.OAK;
    }
  }
}
