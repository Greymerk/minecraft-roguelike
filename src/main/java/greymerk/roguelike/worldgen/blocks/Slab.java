package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.MetaBlock;

public enum Slab {

  STONE,
  STONEBRICK,
  COBBLE,
  BRICK,
  NETHERBRICK,
  QUARTZ,
  LEGACY_OAK,
  SANDSTONE,
  SANDSTONE_RED,
  OAK,
  SPRUCE,
  BIRCH,
  JUNGLE,
  ACACIA,
  DARKOAK;

  public static MetaBlock get(Slab type, boolean upsideDown, boolean full, boolean seamless) {

    MetaBlock slab = new MetaBlock(getBaseSlab(type, full));


    switch (type) {
      case STONE:
        slab.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.STONE);
        break;
      case SANDSTONE:
        slab.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND);
        break;
      case LEGACY_OAK:
        slab.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.WOOD);
        break;
      case COBBLE:
        slab.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE);
        break;
      case BRICK:
        slab.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.BRICK);
        break;
      case STONEBRICK:
        slab.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK);
        break;
      case NETHERBRICK:
        slab.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK);
        break;
      case QUARTZ:
        slab.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.QUARTZ);
        break;
      case SANDSTONE_RED:
        // no enumtype because only one type for red sandstone?
        break;
      case OAK:
        slab.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK);
        break;
      case SPRUCE:
        slab.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.SPRUCE);
        break;
      case BIRCH:
        slab.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.BIRCH);
        break;
      case JUNGLE:
        slab.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.JUNGLE);
        break;
      case ACACIA:
        slab.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.ACACIA);
        break;
      case DARKOAK:
        slab.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.DARK_OAK);
        break;
      default:
    }

    if (!full && upsideDown) {
      slab.withProperty(BlockWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP);
    }

    if (full && seamless) {
      slab.withProperty(BlockStoneSlab.SEAMLESS, true);
    }

    return slab;

  }

  public static MetaBlock get(Slab type) {
    return get(type, false, false, false);
  }

  public static Block getBaseSlab(Slab type, boolean full) {
    switch (type) {
      case STONE:
      case SANDSTONE:
      case LEGACY_OAK:
      case COBBLE:
      case BRICK:
      case STONEBRICK:
      case NETHERBRICK:
      case QUARTZ:
        return full ? Blocks.DOUBLE_STONE_SLAB : Blocks.STONE_SLAB;
      case SANDSTONE_RED:
        return full ? Blocks.DOUBLE_STONE_SLAB2 : Blocks.STONE_SLAB2;
      case OAK:
      case SPRUCE:
      case BIRCH:
      case JUNGLE:
      case ACACIA:
      case DARKOAK:
        return full ? Blocks.DOUBLE_WOODEN_SLAB : Blocks.WOODEN_SLAB;
      default:
        return Blocks.STONE_SLAB;
    }
  }

}
