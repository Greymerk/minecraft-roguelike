package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum StairType {

  COBBLE,
  STONEBRICK,
  BRICK,
  SANDSTONE,
  RED_SANDSTONE,
  QUARTZ,
  NETHERBRICK,
  OAK,
  SPRUCE,
  BIRCH,
  JUNGLE,
  ACACIA,
  DARKOAK,
  PURPUR;

  public static Block getBlock(StairType type) {
    switch (type) {
      case COBBLE:
        return Blocks.STONE_STAIRS;
      case STONEBRICK:
        return Blocks.STONE_BRICK_STAIRS;
      case BRICK:
        return Blocks.BRICK_STAIRS;
      case SANDSTONE:
        return Blocks.SANDSTONE_STAIRS;
      case RED_SANDSTONE:
        return Blocks.RED_SANDSTONE_STAIRS;
      case QUARTZ:
        return Blocks.QUARTZ_STAIRS;
      case NETHERBRICK:
        return Blocks.NETHER_BRICK_STAIRS;
      case OAK:
        return Blocks.OAK_STAIRS;
      case SPRUCE:
        return Blocks.SPRUCE_STAIRS;
      case BIRCH:
        return Blocks.BIRCH_STAIRS;
      case JUNGLE:
        return Blocks.JUNGLE_STAIRS;
      case ACACIA:
        return Blocks.ACACIA_STAIRS;
      case DARKOAK:
        return Blocks.DARK_OAK_STAIRS;
      case PURPUR:
        return Blocks.PURPUR_STAIRS;
      default:
        return Blocks.STONE_STAIRS;
    }
  }
}
