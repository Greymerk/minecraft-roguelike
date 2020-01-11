package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBone;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.MetaBlock;

public enum BlockType {

  AIR,
  WATER_STILL,
  WATER_FLOWING,
  LAVA_STILL,
  LAVA_FLOWING,
  FIRE,
  IRON_BAR,
  STONE_SMOOTH,
  GRANITE,
  GRANITE_POLISHED,
  DIORITE,
  DIORITE_POLISHED,
  ANDESITE,
  ANDESITE_POLISHED,
  GRASS,
  DIRT,
  DIRT_COARSE,
  DIRT_PODZOL,
  COBBLESTONE,
  COBBLESTONE_WALL,
  BEDROCK,
  SAND,
  SAND_RED,
  GRAVEL,
  ORE_GOLD,
  ORE_IRON,
  ORE_COAL,
  GLASS,
  ORE_LAPIS,
  ORE_EMERALD,
  LAPIS_BLOCK,
  SANDSTONE,
  SANDSTONE_CHISELED,
  SANDSTONE_SMOOTH,
  GOLD_BLOCK,
  IRON_BLOCK,
  BRICK,
  COBBLESTONE_MOSSY,
  OBSIDIAN,
  ORE_DIAMOND,
  DIAMOND_BLOCK,
  FARMLAND,
  ORE_REDSTONE,
  ICE,
  SNOW,
  CLAY,
  NETHERRACK,
  SOUL_SAND,
  GLOWSTONE,
  STONE_BRICK,
  STONE_BRICK_MOSSY,
  STONE_BRICK_CRACKED,
  STONE_BRICK_CHISELED,
  MYCELIUM,
  NETHERBRICK,
  END_STONE,
  EMERALD_BLOCK,
  ORE_QUARTZ,
  PRISMITE,
  PRISMARINE,
  PRISMARINE_DARK,
  SEA_LANTERN,
  COAL_BLOCK,
  ICE_PACKED,
  SANDSTONE_RED,
  SANDSTONE_RED_CHISELED,
  SANDSTONE_RED_SMOOTH,
  QUARTZ,
  REDSTONE_BLOCK,
  PRESSURE_PLATE_STONE,
  PRESSURE_PLATE_WOODEN,
  SHELF,
  REDSTONE_WIRE,
  COCAO,
  REEDS,
  CRAFTING_TABLE,
  NOTEBLOCK,
  REDSTONE_LAMP,
  REDSTONE_LAMP_LIT,
  JUKEBOX,
  FENCE,
  TNT,
  ENCHANTING_TABLE,
  FENCE_NETHER_BRICK,
  WEB,
  PUMPKIN_LIT,
  VINE,
  PURPUR_BLOCK,
  PURPUR_PILLAR,
  PURPUR_STAIR,
  PURPUR_DOUBLE_SLAB,
  PURPUR_SLAB,
  ENDER_BRICK,
  MAGMA,
  RED_NETHERBRICK,
  NETHER_WART_BLOCK,
  BONE_BLOCK;

  public static MetaBlock get(BlockType type) {

    MetaBlock block;

    switch (type) {
      case AIR:
        return new MetaBlock(Blocks.AIR);
      case WATER_STILL:
        return new MetaBlock(Blocks.WATER);
      case WATER_FLOWING:
        return new MetaBlock(Blocks.FLOWING_WATER);
      case LAVA_STILL:
        return new MetaBlock(Blocks.LAVA);
      case LAVA_FLOWING:
        return new MetaBlock(Blocks.FLOWING_LAVA);
      case FIRE:
        return new MetaBlock(Blocks.FIRE);
      case IRON_BAR:
        return new MetaBlock(Blocks.IRON_BARS);
      case STONE_SMOOTH:
        return new MetaBlock(Blocks.STONE);
      case GRANITE:
        block = new MetaBlock(Blocks.STONE);
        block.withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE);
        return block;
      case GRANITE_POLISHED:
        block = new MetaBlock(Blocks.STONE);
        block.withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE_SMOOTH);
        return block;
      case DIORITE:
        block = new MetaBlock(Blocks.STONE);
        block.withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE);
        return block;
      case DIORITE_POLISHED:
        block = new MetaBlock(Blocks.STONE);
        block.withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE_SMOOTH);
        return block;
      case ANDESITE:
        block = new MetaBlock(Blocks.STONE);
        block.withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
        return block;
      case ANDESITE_POLISHED:
        block = new MetaBlock(Blocks.STONE);
        block.withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE_SMOOTH);
        return block;
      case GRASS:
        return new MetaBlock(Blocks.GRASS);
      case DIRT:
        return new MetaBlock(Blocks.DIRT);
      case DIRT_COARSE:
        block = new MetaBlock(Blocks.DIRT);
        block.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
        return block;
      case DIRT_PODZOL:
        block = new MetaBlock(Blocks.DIRT);
        block.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
        return block;
      case COBBLESTONE:
        return new MetaBlock(Blocks.COBBLESTONE);
      case COBBLESTONE_WALL:
        return new MetaBlock(Blocks.COBBLESTONE_WALL);
      case BEDROCK:
        return new MetaBlock(Blocks.BEDROCK);
      case SAND:
        return new MetaBlock(Blocks.SAND);
      case SAND_RED:
        block = new MetaBlock(Blocks.SAND);
        block.withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
        return block;
      case GRAVEL:
        return new MetaBlock(Blocks.GRAVEL);
      case ORE_GOLD:
        return new MetaBlock(Blocks.GOLD_ORE);
      case ORE_IRON:
        return new MetaBlock(Blocks.IRON_ORE);
      case GLASS:
        return new MetaBlock(Blocks.GLASS);
      case ORE_LAPIS:
        return new MetaBlock(Blocks.LAPIS_ORE);
      case LAPIS_BLOCK:
        return new MetaBlock(Blocks.LAPIS_BLOCK);
      case ORE_EMERALD:
        return new MetaBlock(Blocks.EMERALD_ORE);
      case SANDSTONE:
        return new MetaBlock(Blocks.SANDSTONE);
      case SANDSTONE_CHISELED:
        block = new MetaBlock(Blocks.SANDSTONE);
        block.withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.CHISELED);
        return block;
      case SANDSTONE_SMOOTH:
        block = new MetaBlock(Blocks.SANDSTONE);
        block.withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH);
        return block;
      case GOLD_BLOCK:
        return new MetaBlock(Blocks.GOLD_BLOCK);
      case IRON_BLOCK:
        return new MetaBlock(Blocks.IRON_BLOCK);
      case BRICK:
        return new MetaBlock(Blocks.BRICK_BLOCK);
      case COBBLESTONE_MOSSY:
        return new MetaBlock(Blocks.MOSSY_COBBLESTONE);
      case OBSIDIAN:
        return new MetaBlock(Blocks.OBSIDIAN);
      case ORE_DIAMOND:
        return new MetaBlock(Blocks.DIAMOND_ORE);
      case DIAMOND_BLOCK:
        return new MetaBlock(Blocks.DIAMOND_BLOCK);
      case FARMLAND:
        return new MetaBlock(Blocks.FARMLAND);
      case ORE_REDSTONE:
        return new MetaBlock(Blocks.REDSTONE_ORE);
      case ICE:
        return new MetaBlock(Blocks.ICE);
      case SNOW:
        return new MetaBlock(Blocks.SNOW);
      case CLAY:
        return new MetaBlock(Blocks.CLAY);
      case NETHERRACK:
        return new MetaBlock(Blocks.NETHERRACK);
      case SOUL_SAND:
        return new MetaBlock(Blocks.SOUL_SAND);
      case GLOWSTONE:
        return new MetaBlock(Blocks.GLOWSTONE);
      case STONE_BRICK:
        return new MetaBlock(Blocks.STONEBRICK);
      case STONE_BRICK_MOSSY:
        block = new MetaBlock(Blocks.STONEBRICK);
        block.withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
        return block;
      case STONE_BRICK_CRACKED:
        block = new MetaBlock(Blocks.STONEBRICK);
        block.withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
        return block;
      case STONE_BRICK_CHISELED:
        block = new MetaBlock(Blocks.STONEBRICK);
        block.withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
        return block;
      case MYCELIUM:
        return new MetaBlock(Blocks.MYCELIUM);
      case NETHERBRICK:
        return new MetaBlock(Blocks.NETHER_BRICK);
      case END_STONE:
        return new MetaBlock(Blocks.END_STONE);
      case EMERALD_BLOCK:
        return new MetaBlock(Blocks.EMERALD_BLOCK);
      case ORE_QUARTZ:
        return new MetaBlock(Blocks.QUARTZ_ORE);
      case PRISMITE:
        block = new MetaBlock(Blocks.PRISMARINE);
        block.withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.ROUGH);
        return block;
      case PRISMARINE:
        block = new MetaBlock(Blocks.PRISMARINE);
        block.withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.BRICKS);
        return block;
      case PRISMARINE_DARK:
        block = new MetaBlock(Blocks.PRISMARINE);
        block.withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.DARK);
        return block;
      case SEA_LANTERN:
        return new MetaBlock(Blocks.SEA_LANTERN);
      case COAL_BLOCK:
        return new MetaBlock(Blocks.COAL_BLOCK);
      case ICE_PACKED:
        return new MetaBlock(Blocks.PACKED_ICE);
      case SANDSTONE_RED:
        return new MetaBlock(Blocks.RED_SANDSTONE);
      case SANDSTONE_RED_CHISELED:
        block = new MetaBlock(Blocks.RED_SANDSTONE);
        block.withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED);
        return block;
      case SANDSTONE_RED_SMOOTH:
        block = new MetaBlock(Blocks.RED_SANDSTONE);
        block.withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH);
        return block;
      case QUARTZ:
        return new MetaBlock(Blocks.QUARTZ_BLOCK);
      case REDSTONE_BLOCK:
        return new MetaBlock(Blocks.REDSTONE_BLOCK);
      case PRESSURE_PLATE_STONE:
        return new MetaBlock(Blocks.STONE_PRESSURE_PLATE);
      case PRESSURE_PLATE_WOODEN:
        return new MetaBlock(Blocks.WOODEN_PRESSURE_PLATE);
      case SHELF:
        return new MetaBlock(Blocks.BOOKSHELF);
      case REDSTONE_WIRE:
        return new MetaBlock(Blocks.REDSTONE_WIRE);
      case COCAO:
        return new MetaBlock(Blocks.COCOA);
      case REEDS:
        return new MetaBlock(Blocks.REEDS);
      case CRAFTING_TABLE:
        if (!RogueConfig.getBoolean(RogueConfig.FURNITURE)) {
          return Wood.getPlank(Wood.OAK);
        }
        return new MetaBlock(Blocks.CRAFTING_TABLE);
      case NOTEBLOCK:
        return new MetaBlock(Blocks.NOTEBLOCK);
      case REDSTONE_LAMP:
        return new MetaBlock(Blocks.REDSTONE_LAMP);
      case REDSTONE_LAMP_LIT:
        return new MetaBlock(Blocks.LIT_REDSTONE_LAMP.getDefaultState());
      case JUKEBOX:
        return new MetaBlock(Blocks.JUKEBOX);
      case FENCE:
        return new MetaBlock(Blocks.OAK_FENCE);
      case TNT:
        return new MetaBlock(Blocks.TNT);
      case ENCHANTING_TABLE:
        return new MetaBlock(Blocks.ENCHANTING_TABLE);
      case FENCE_NETHER_BRICK:
        return new MetaBlock(Blocks.NETHER_BRICK_FENCE);
      case WEB:
        return new MetaBlock(Blocks.WEB);
      case PUMPKIN_LIT:
        return new MetaBlock(Blocks.LIT_PUMPKIN);
      case VINE:
        return new MetaBlock(Blocks.VINE);
      case PURPUR_BLOCK:
        return new MetaBlock(Blocks.PURPUR_BLOCK);
      case PURPUR_PILLAR:
        return new MetaBlock(Blocks.PURPUR_PILLAR);
      case PURPUR_STAIR:
        return new MetaBlock(Blocks.PURPUR_STAIRS);
      case PURPUR_DOUBLE_SLAB:
        return new MetaBlock(Blocks.PURPUR_DOUBLE_SLAB);
      case PURPUR_SLAB:
        return new MetaBlock(Blocks.PURPUR_SLAB);
      case ENDER_BRICK:
        return new MetaBlock(Blocks.END_BRICKS);
      case MAGMA:
        return new MetaBlock(Blocks.MAGMA);
      case RED_NETHERBRICK:
        return new MetaBlock(Blocks.RED_NETHER_BRICK);
      case NETHER_WART_BLOCK:
        return new MetaBlock(Blocks.NETHER_WART_BLOCK);
      case BONE_BLOCK:
        block = new MetaBlock(Blocks.BONE_BLOCK);
        block.withProperty(BlockBone.AXIS, EnumFacing.UP.getAxis());
        return block;
      default:
        return new MetaBlock(Blocks.AIR);
    }
  }

  public static ItemStack getItem(BlockType type) {

    MetaBlock block = BlockType.get(type);
    Block b = block.getBlock();
    Item i = Item.getItemFromBlock(b);
    ItemStack item = new ItemStack(i);

    return item;
  }
}
