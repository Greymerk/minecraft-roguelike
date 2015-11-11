package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public enum BlockType {

	AIR,
	SMOOTH_STONE, GRANITE, GRANITE_POLISHED, DIORITE, DIORITE_POLISHED, ANDESITE, 
	ANDESITE_POLISHED, GRASS, DIRT, DIRT_COARSE, DIRT_PODZOL, COBBLESTONE, BEDROCK,
	SAND, SAND_RED, GRAVEL, ORE_GOLD, ORE_IRON, ORE_COAL, GLASS, ORE_LAPIS, LAPIS_BLOCK,
	SANDSTONE, SANDSTONE_CHISELED, SANDSTONE_SMOOTH, GOLD_BLOCK, IRON_BLOCK,
	BRICK, COBBLESTONE_MOSSY, OBSIDIAN, ORE_DIAMOND, DIAMOND_BLOCK, FARMLAND, 
	ORE_REDSTONE, ICE, SNOW, CLAY, NETHERRACK, SOUL_SAND, GLOWSTONE,
	STONE_BRICK, STONE_BRICK_MOSSY, STONE_BRICK_CRACKED, STONE_BRICK_CHISELED,
	MYCELIUM, NETHERBRICK, END_STONE, EMERALD_BLOCK, ORE_QUARTZ,
	PRISMITE, PRISMARINE, PRISMARINE_DARK, SEA_LANTERN, COAL_BLOCK, ICE_PACKED,
	SANDSTONE_RED, SANDSTONE_RED_CHISELED, SANDSTONE_RED_SMOOTH;
	
	public static MetaBlock get(BlockType type){
		
		MetaBlock block;
		
		switch(type){
		case AIR: return new MetaBlock(Blocks.air);
		case SMOOTH_STONE: return new MetaBlock(Blocks.stone);
		case GRANITE:
			block = new MetaBlock(Blocks.stone);
			block.withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.GRANITE);
			return block;
		case GRANITE_POLISHED:
			block = new MetaBlock(Blocks.stone);
			block.withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.GRANITE_SMOOTH);
			return block;
		case DIORITE:
			block = new MetaBlock(Blocks.stone);
			block.withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.DIORITE);
			return block;
		case DIORITE_POLISHED:
			block = new MetaBlock(Blocks.stone);
			block.withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.DIORITE_SMOOTH);
			return block;
		case ANDESITE:
			block = new MetaBlock(Blocks.stone);
			block.withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.ANDESITE);
			return block;
		case ANDESITE_POLISHED:
			block = new MetaBlock(Blocks.stone);
			block.withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.ANDESITE_SMOOTH);
			return block;
		case GRASS: return new MetaBlock(Blocks.grass);
		case DIRT: return new MetaBlock(Blocks.dirt);
		case DIRT_COARSE:
			block = new MetaBlock(Blocks.dirt);
			block.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
			return block;
		case DIRT_PODZOL:
			block = new MetaBlock(Blocks.dirt);
			block.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
			return block;
		case COBBLESTONE: return new MetaBlock(Blocks.cobblestone);
		case BEDROCK: return new MetaBlock(Blocks.bedrock);
		case SAND: return new MetaBlock(Blocks.sand);
		case SAND_RED:
			block = new MetaBlock(Blocks.sand);
			block.withProperty(BlockSand.VARIANT_PROP, BlockSand.EnumType.RED_SAND);
			return block;
		case GRAVEL: return new MetaBlock(Blocks.gravel);
		case ORE_GOLD: return new MetaBlock(Blocks.gold_ore);
		case ORE_IRON: return new MetaBlock(Blocks.iron_ore);
		case GLASS: return new MetaBlock(Blocks.glass);
		case ORE_LAPIS: return new MetaBlock(Blocks.lapis_ore);
		case LAPIS_BLOCK: return new MetaBlock(Blocks.lapis_block);
		case SANDSTONE: return new MetaBlock(Blocks.sandstone);
		case SANDSTONE_CHISELED:
			block = new MetaBlock(Blocks.sandstone);
			block.withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.CHISELED);
			return block;
		case SANDSTONE_SMOOTH:
			block = new MetaBlock(Blocks.sandstone);
			block.withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.SMOOTH);
			return block;
		case GOLD_BLOCK: return new MetaBlock(Blocks.gold_block);
		case IRON_BLOCK: return new MetaBlock(Blocks.iron_block);
		case BRICK: return new MetaBlock(Blocks.brick_block);
		case COBBLESTONE_MOSSY: return new MetaBlock(Blocks.mossy_cobblestone);
		case OBSIDIAN: return new MetaBlock(Blocks.obsidian);
		case ORE_DIAMOND: return new MetaBlock(Blocks.diamond_ore);
		case DIAMOND_BLOCK: return new MetaBlock(Blocks.diamond_block);
		case FARMLAND: return new MetaBlock(Blocks.farmland);
		case ORE_REDSTONE: return new MetaBlock(Blocks.redstone_ore);
		case ICE: return new MetaBlock(Blocks.ice);
		case SNOW: return new MetaBlock(Blocks.snow);
		case CLAY: return new MetaBlock(Blocks.clay);
		case NETHERRACK: return new MetaBlock(Blocks.netherrack);
		case SOUL_SAND: return new MetaBlock(Blocks.soul_sand);
		case GLOWSTONE: return new MetaBlock(Blocks.glowstone);
		case STONE_BRICK: return new MetaBlock(Blocks.stonebrick);
		case STONE_BRICK_MOSSY:
			block = new MetaBlock(Blocks.stonebrick);
			block.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
			return block;
		case STONE_BRICK_CRACKED:
			block = new MetaBlock(Blocks.stonebrick);
			block.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
			return block;
		case STONE_BRICK_CHISELED:
			block = new MetaBlock(Blocks.stonebrick);
			block.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CHISELED);
			return block;
		case MYCELIUM: return new MetaBlock(Blocks.mycelium);
		case NETHERBRICK: return new MetaBlock(Blocks.nether_brick);
		case END_STONE: return new MetaBlock(Blocks.end_stone);
		case EMERALD_BLOCK: return new MetaBlock(Blocks.emerald_block);
		case ORE_QUARTZ: return new MetaBlock(Blocks.quartz_ore);
		case PRISMITE: 
			block = new MetaBlock(Blocks.prismarine);
			block.withProperty(BlockPrismarine.VARIANTS, BlockPrismarine.EnumType.ROUGH);
		case PRISMARINE: 
			block = new MetaBlock(Blocks.prismarine);
			block.withProperty(BlockPrismarine.VARIANTS, BlockPrismarine.EnumType.BRICKS);
		case PRISMARINE_DARK: 
			block = new MetaBlock(Blocks.prismarine);
			block.withProperty(BlockPrismarine.VARIANTS, BlockPrismarine.EnumType.DARK);
		case SEA_LANTERN: return new MetaBlock(Blocks.sea_lantern);
		case COAL_BLOCK: return new MetaBlock(Blocks.coal_block);
		case ICE_PACKED: return new MetaBlock(Blocks.packed_ice);
		case SANDSTONE_RED: return new MetaBlock(Blocks.red_sandstone);
		case SANDSTONE_RED_CHISELED:
			block = new MetaBlock(Blocks.red_sandstone);
			block.withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED);
		case SANDSTONE_RED_SMOOTH:
			block = new MetaBlock(Blocks.red_sandstone);
			block.withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH);
		default: return new MetaBlock(Blocks.air);
		}
	}	
}
