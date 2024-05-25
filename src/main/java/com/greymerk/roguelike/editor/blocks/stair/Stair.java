package com.greymerk.roguelike.editor.blocks.stair;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.StairShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public enum Stair{

	STONE, COBBLE, MOSSY_COBBLE, STONEBRICK, MOSSY_STONEBRICK, 
	BRICK, SANDSTONE, RED_SANDSTONE, QUARTZ, NETHERBRICK,
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK, PURPUR,
	COBBLED_DEEPSLATE, TILED_DEEPSLATE, POLISHED_DEEPSLATE, DEEPSLATE_BRICK, 
	BLACKSTONE, BLACKSTONE_BRICK, POLISHED_BLACKSTONE,
	CRIMSON, WARPED;
	
	public static MetaStair of(Stair type){
		return get(type, Cardinal.EAST, false);
	}
	
	public static MetaStair get(Stair type, Cardinal dir, boolean upsideDown){
		MetaStair stair = new MetaStair(type);
		stair.setOrientation(dir, upsideDown);
		return stair;
	}
	
	public static Block getBlock(Stair type){
		switch(type){
		case STONE: return Blocks.STONE_STAIRS;
		case COBBLE: return Blocks.COBBLESTONE_STAIRS;
		case MOSSY_COBBLE: return Blocks.MOSSY_COBBLESTONE_STAIRS;
		case STONEBRICK: return Blocks.STONE_BRICK_STAIRS;
		case MOSSY_STONEBRICK: return Blocks.MOSSY_STONE_BRICK_STAIRS;
		case BRICK: return Blocks.BRICK_STAIRS;
		case SANDSTONE: return Blocks.SANDSTONE_STAIRS;
		case RED_SANDSTONE: return Blocks.RED_SANDSTONE_STAIRS;
		case QUARTZ: return Blocks.QUARTZ_STAIRS;
		case NETHERBRICK: return Blocks.NETHER_BRICK_STAIRS;
		case OAK: return Blocks.OAK_STAIRS;
		case SPRUCE: return Blocks.SPRUCE_STAIRS;
		case BIRCH: return Blocks.BIRCH_STAIRS;
		case JUNGLE: return Blocks.JUNGLE_STAIRS;
		case ACACIA: return Blocks.ACACIA_STAIRS;
		case DARKOAK: return Blocks.DARK_OAK_STAIRS;
		case PURPUR: return Blocks.PURPUR_STAIRS;
		case COBBLED_DEEPSLATE: return Blocks.COBBLED_DEEPSLATE_STAIRS;
		case TILED_DEEPSLATE: return Blocks.DEEPSLATE_TILE_STAIRS;
		case POLISHED_DEEPSLATE: return Blocks.POLISHED_DEEPSLATE_STAIRS;
		case DEEPSLATE_BRICK: return Blocks.DEEPSLATE_BRICK_STAIRS;
		case BLACKSTONE: return Blocks.BLACKSTONE_STAIRS;
		case BLACKSTONE_BRICK: return Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS;
		case POLISHED_BLACKSTONE: return Blocks.POLISHED_BLACKSTONE_STAIRS;
		case CRIMSON: return Blocks.CRIMSON_STAIRS;
		case WARPED: return Blocks.WARPED_STAIRS;
		default: return Blocks.STONE_STAIRS;
		}
	}
	
    public static StairShape getStairShape(BlockState state, IWorldEditor world, BlockPos pos) {
        Direction direction3;
        Direction direction2;
        Direction direction = state.get(StairsBlock.FACING);
        BlockState blockState = world.getBlock(Coord.of(pos.offset(direction))).getState();
        if (StairsBlock.isStairs(blockState) 
        		&& state.get(StairsBlock.HALF) == blockState.get(StairsBlock.HALF) 
        		&& (direction2 = blockState.get(StairsBlock.FACING)).getAxis() != state.get(StairsBlock.FACING).getAxis() 
        		&& isDifferentOrientation(state, world, pos, direction2.getOpposite())) {
            if (direction2 == direction.rotateYCounterclockwise()) {
                return StairShape.OUTER_LEFT;
            }
            return StairShape.OUTER_RIGHT;
        }
        BlockState blockState2 = world.getBlock(Coord.of(pos.offset(direction.getOpposite()))).getState();
        if (StairsBlock.isStairs(blockState2) && state.get(StairsBlock.HALF) == blockState2.get(StairsBlock.HALF) 
        		&& (direction3 = blockState2.get(StairsBlock.FACING)).getAxis() != state.get(StairsBlock.FACING).getAxis() 
        		&& isDifferentOrientation(state, world, pos, direction3)) {
            if (direction3 == direction.rotateYCounterclockwise()) {
                return StairShape.INNER_LEFT;
            }
            return StairShape.INNER_RIGHT;
        }
        return StairShape.STRAIGHT;
    }

    public static boolean isDifferentOrientation(BlockState state, IWorldEditor world, BlockPos pos, Direction dir) {
        BlockState blockState = world.getBlock(Coord.of(pos.offset(dir))).getState();
        return !StairsBlock.isStairs(blockState) 
        		|| blockState.get(StairsBlock.FACING) != state.get(StairsBlock.FACING) 
        		|| blockState.get(StairsBlock.HALF) != state.get(StairsBlock.HALF);
    }
}
