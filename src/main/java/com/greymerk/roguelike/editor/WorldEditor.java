package com.greymerk.roguelike.editor;

import java.util.Objects;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

public class WorldEditor implements IWorldEditor{

	WorldAccess world;
	RegistryKey<World> worldKey;
	IWorldInfo worldInfo;
	
	public WorldEditor(StructureWorldAccess world, RegistryKey<World> worldKey){
		this.world = world;
		this.worldKey = worldKey;
		this.worldInfo = WorldInfo.of(world, worldKey);
	}

	public WorldEditor(World world) {
		this.world = world;
		this.worldKey = world.getRegistryKey();
		this.worldInfo = WorldInfo.of(world, worldKey);
	}

	@Override
	public boolean set(Coord pos, MetaBlock block, Predicate<BlockContext> p) {
		if(!p.and(Fill.IGNORE_BLOCK_ENTITIES).test(BlockContext.of(this, pos, block))) return false;
		return world.setBlockState(pos.getBlockPos(), block.getState(), block.getFlag());
	}
	
	@Override
	public boolean set(Coord pos, MetaBlock block) {
		if(!Fill.IGNORE_BLOCK_ENTITIES.test(BlockContext.of(this, pos, block))) return false;
		return world.setBlockState(pos.getBlockPos(), block.getState(), block.getFlag());
	}
	
	@Override
	public MetaBlock getBlock(Coord pos) {
		BlockState state = world.getBlockState(pos.getBlockPos());
		return MetaBlock.of(state);
	}

	@Override
	public boolean isAir(Coord pos) {
		return this.world.isAir(pos.getBlockPos());
	}

	
	@Override
	public long getSeed(Coord pos) {
		return Objects.hash(this.worldInfo.getSeed(), pos.hashCode());
	}
	
	@Override
	public Random getRandom(Coord pos) {
		return new CheckedRandom(getSeed(pos));
	}

	@Override
	public boolean isChunkLoaded(Coord pos) {
		ChunkPos cp = pos.getChunkPos();
		return world.isChunkLoaded(cp.x, cp.z);
	}
	
	@Override
	public boolean surroundingChunksLoaded(Coord pos) {
		ChunkPos cpos = pos.getChunkPos();
		for(int x = cpos.x - 1; x <= cpos.x + 1; x++) {
			for(int z = cpos.z - 1; z <= cpos.z + 1; z++) {
				if(!world.isChunkLoaded(x, z)) return false;
				Chunk chunk = world.getChunk(x, z);
				ChunkStatus status = chunk.getStatus();
				if(status != ChunkStatus.FULL) return false;
			}
		}
		
		return true;
		
	}

	@Override
	public boolean isSolid(Coord pos) {
		return this.world.getBlockState(pos.getBlockPos()).isSolidBlock(world, pos.getBlockPos());
	}
	
	@Override
	public boolean isSupported(Coord pos) {
		if(pos.getY() <= world.getBottomY()) return false;
		Coord under = pos.copy().add(Cardinal.DOWN);
		Block b = this.world.getBlockState(under.getBlockPos()).getBlock();
		if(b instanceof FallingBlock) {
			return isSupported(under);
		}
		
		if(!FallingBlock.canFallThrough(world.getBlockState(under.getBlockPos()))) return true;
		return false;
	}

	@Override
	public boolean hasBlockEntity(Coord pos) {
		return this.getBlockEntity(pos) != null;
	}
	
	@Override
	public BlockEntity getBlockEntity(Coord pos) {
		return world.getBlockEntity(pos.getBlockPos());
	}
	
	@Override
	public boolean isFaceFullSquare(Coord pos, Cardinal dir) {
		BlockState b = this.world.getBlockState(pos.getBlockPos());
		Direction facing = Cardinal.facing(dir);
		VoxelShape shape = b.getSidesShape(world, pos.getBlockPos());
		VoxelShape collision = b.getCollisionShape(world, pos.getBlockPos());
		boolean isShapeSquare = Block.isFaceFullSquare(shape, facing);
		boolean isCollisionSquare = Block.isFaceFullSquare(collision, facing);
		return isShapeSquare || isCollisionSquare;
	}
	
	@Override
	public RegistryKey<World> getRegistryKey(){
		return this.worldKey;
	}

	@Override
	public IWorldInfo getInfo() {
		return this.worldInfo;
	}
}
