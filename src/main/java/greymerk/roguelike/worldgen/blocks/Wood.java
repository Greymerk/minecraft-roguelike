package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.MetaBlock;

public enum Wood {

  OAK,
  SPRUCE,
  BIRCH,
  JUNGLE,
  ACACIA,
  DARKOAK;

  public static MetaBlock get(WoodBlock block) {
    return get(OAK, block);
  }

  public static MetaBlock get(Wood type, WoodBlock block) {
    switch (block) {
      case LOG:
        return Log.getLog(type);
      case PLANK:
        return getPlank(type);
      case FENCE:
        return getFence(type);
      default:
        return Log.getLog(OAK);
    }
  }

  public static MetaBlock getPlank(Wood type) {

    MetaBlock plank = new MetaBlock(Blocks.PLANKS);

    switch (type) {
      case OAK:
        plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK);
        break;
      case SPRUCE:
        plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE);
        break;
      case BIRCH:
        plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH);
        break;
      case JUNGLE:
        plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE);
        break;
      case ACACIA:
        plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA);
        break;
      case DARKOAK:
        plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK);
        break;
    }

    return plank;
  }

  public static MetaBlock getFence(Wood type) {

    MetaBlock fence;

    switch (type) {
      case OAK:
        fence = new MetaBlock(Blocks.OAK_FENCE);
        break;
      case SPRUCE:
        fence = new MetaBlock(Blocks.SPRUCE_FENCE);
        break;
      case BIRCH:
        fence = new MetaBlock(Blocks.BIRCH_FENCE);
        break;
      case JUNGLE:
        fence = new MetaBlock(Blocks.JUNGLE_FENCE);
        break;
      case ACACIA:
        fence = new MetaBlock(Blocks.ACACIA_FENCE);
        break;
      case DARKOAK:
        fence = new MetaBlock(Blocks.DARK_OAK_FENCE);
        break;
      default:
        fence = new MetaBlock(Blocks.OAK_FENCE);
        break;
    }

    return fence;
  }

  public static MetaBlock getSapling(Wood type) {
    MetaBlock sapling = new MetaBlock(Blocks.SAPLING);

    switch (type) {
      case OAK:
        sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.OAK);
        break;
      case SPRUCE:
        sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.SPRUCE);
        break;
      case BIRCH:
        sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.BIRCH);
        break;
      case JUNGLE:
        sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.JUNGLE);
        break;
      case ACACIA:
        sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.ACACIA);
        break;
      case DARKOAK:
        sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.DARK_OAK);
        break;
      default:
    }

    return sapling;
  }

}
