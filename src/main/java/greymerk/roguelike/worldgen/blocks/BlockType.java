package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.Block;
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

public enum BlockType {

	AIR, WATER_STILL, WATER_FLOWING, LAVA_STILL, LAVA_FLOWING, FIRE, IRON_BAR,
	STONE_SMOOTH, GRANITE, GRANITE_POLISHED, DIORITE, DIORITE_POLISHED, ANDESITE, 
	ANDESITE_POLISHED, GRASS, DIRT, DIRT_COARSE, DIRT_PODZOL, COBBLESTONE, COBBLESTONE_WALL, BEDROCK,
	SAND, SAND_RED, GRAVEL, ORE_GOLD, ORE_IRON, ORE_COAL, GLASS, ORE_LAPIS, ORE_EMERALD, LAPIS_BLOCK,
	SANDSTONE, SANDSTONE_CHISELED, SANDSTONE_SMOOTH, GOLD_BLOCK, IRON_BLOCK,
	BRICK, COBBLESTONE_MOSSY, OBSIDIAN, ORE_DIAMOND, DIAMOND_BLOCK, FARMLAND, 
	ORE_REDSTONE, ICE, SNOW, CLAY, NETHERRACK, SOUL_SAND, GLOWSTONE,
	STONE_BRICK, STONE_BRICK_MOSSY, STONE_BRICK_CRACKED, STONE_BRICK_CHISELED,
	MYCELIUM, NETHERBRICK, END_STONE, EMERALD_BLOCK, ORE_QUARTZ,
	PRISMITE, PRISMARINE, PRISMARINE_DARK, SEA_LANTERN, COAL_BLOCK, ICE_PACKED,
	SANDSTONE_RED, SANDSTONE_RED_CHISELED, SANDSTONE_RED_SMOOTH,
	QUARTZ, REDSTONE_BLOCK, PRESSURE_PLATE_STONE, PRESSURE_PLATE_WOODEN, SHELF, REDSTONE_WIRE,
	COCAO, REEDS, CRAFTING_TABLE, NOTEBLOCK, REDSTONE_LAMP, REDSTONE_LAMP_LIT, JUKEBOX, FENCE,
	TNT, ENCHANTING_TABLE, FENCE_NETHER_BRICK, WEB, PUMPKIN_LIT, VINE;
	
	
	public static MetaBlock get(BlockType type){
		
		MetaBlock block;
		
		switch(type){
		case AIR: return new MetaBlock(Blocks.air);
		case WATER_STILL: return new MetaBlock(Blocks.water);
		case WATER_FLOWING: return new MetaBlock(Blocks.flowing_water);
		case LAVA_STILL: return new MetaBlock(Blocks.lava);
		case LAVA_FLOWING: return new MetaBlock(Blocks.flowing_lava);
		case FIRE: return new MetaBlock(Blocks.fire);
		case IRON_BAR: return new MetaBlock(Blocks.iron_bars);
		case STONE_SMOOTH: return new MetaBlock(Blocks.stone);
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
		case COBBLESTONE_WALL: return new MetaBlock(Blocks.cobblestone_wall);
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
		case ORE_EMERALD: return new MetaBlock(Blocks.emerald_ore);
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
			return block;
		case PRISMARINE: 
			block = new MetaBlock(Blocks.prismarine);
			block.withProperty(BlockPrismarine.VARIANTS, BlockPrismarine.EnumType.BRICKS);
			return block;
		case PRISMARINE_DARK: 
			block = new MetaBlock(Blocks.prismarine);
			block.withProperty(BlockPrismarine.VARIANTS, BlockPrismarine.EnumType.DARK);
			return block;
		case SEA_LANTERN: return new MetaBlock(Blocks.sea_lantern);
		case COAL_BLOCK: return new MetaBlock(Blocks.coal_block);
		case ICE_PACKED: return new MetaBlock(Blocks.packed_ice);
		case SANDSTONE_RED: return new MetaBlock(Blocks.red_sandstone);
		case SANDSTONE_RED_CHISELED:
			block = new MetaBlock(Blocks.red_sandstone);
			block.withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED);
			return block;
		case SANDSTONE_RED_SMOOTH:
			block = new MetaBlock(Blocks.red_sandstone);
			block.withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH);
			return block;
		case QUARTZ: return new MetaBlock(Blocks.quartz_block);
		case REDSTONE_BLOCK: return new MetaBlock(Blocks.redstone_block);
		case PRESSURE_PLATE_STONE: return new MetaBlock(Blocks.stone_pressure_plate);
		case PRESSURE_PLATE_WOODEN: return new MetaBlock(Blocks.wooden_pressure_plate);
		case SHELF: return new MetaBlock(Blocks.bookshelf);
		case REDSTONE_WIRE: return new MetaBlock(Blocks.redstone_wire);
		case COCAO: return new MetaBlock(Blocks.cocoa);
		case REEDS: return new MetaBlock(Blocks.reeds);
		case CRAFTING_TABLE: return new MetaBlock(Blocks.crafting_table);
		case NOTEBLOCK: return new MetaBlock(Blocks.noteblock);
		case REDSTONE_LAMP: return new MetaBlock(Blocks.redstone_lamp);
		case REDSTONE_LAMP_LIT: return new MetaBlock(Blocks.lit_redstone_lamp);
		case JUKEBOX: return new MetaBlock(Blocks.jukebox);
		case FENCE: return new MetaBlock(Blocks.oak_fence);
		case TNT: return new MetaBlock(Blocks.tnt);
		case ENCHANTING_TABLE: return new MetaBlock(Blocks.enchanting_table);
		case FENCE_NETHER_BRICK: return new MetaBlock(Blocks.nether_brick_fence);
		case WEB: return new MetaBlock(Blocks.web);
		case PUMPKIN_LIT: return new MetaBlock(Blocks.lit_pumpkin);
		case VINE: return new MetaBlock(Blocks.vine);
		default: return new MetaBlock(Blocks.air);
		}
	}
	
	public static ItemStack getItem(BlockType type){
		
		MetaBlock block = BlockType.get(type);
		Block b = block.getBlock();
		Item i = Item.getItemFromBlock(b);
		ItemStack item = new ItemStack(i);
		
		return item;
	}
}
