package com.greymerk.roguelike.editor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

public class WorldEditor implements IWorldEditor{

	private World world;
	private RegistryKey<World> worldKey;
	private IWorldInfo worldInfo;
	private Statistics stats;
	
	public static WorldEditor of(World world) {
		return new WorldEditor(world);
	}
	
	public static WorldEditor of(ServerWorld world) {
		return new WorldEditor(world);
	}
	
	@Deprecated
	public WorldEditor(StructureWorldAccess world, RegistryKey<World> worldKey){}

	private WorldEditor(World world) {
		this.world = world;
		this.worldKey = world.getRegistryKey();
		this.worldInfo = WorldInfo.of(world, worldKey);
		this.stats = new Statistics();
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
		return this.world.getBlockEntity(pos.getBlockPos()) != null;
	}
	
	@Override
	public Optional<BlockEntity> getBlockEntity(Coord pos) {
		BlockEntity be = world.getBlockEntity(pos.getBlockPos());
		if(be == null) return Optional.empty();
		return Optional.of(be);
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
	public Coord findSurface(Coord pos) {

		Coord cursor = pos.withY(world.getTopYInclusive());
		int seaLevel = this.worldInfo.getSeaLevel();

		while(cursor.getY() > seaLevel - 3) {
			MetaBlock m = this.getBlock(cursor);
			if(m.isIn(List.of(BlockTags.LOGS, BlockTags.LEAVES))) {
				cursor.add(Cardinal.DOWN);
				continue;
			}
			
			if(!world.isAir(cursor.getBlockPos()) && !m.isPlant()) return cursor;
			cursor.add(Cardinal.DOWN);
		}
		
		return cursor;
	}

	@Override
	public int getDungeonEntryDepth(Coord origin) {
		Coord surface = this.findSurface(origin);
		return (surface.getY() - Math.floorMod(surface.getY(), 10)) - 10;
	}
	
	@Override
	public RegistryKey<World> getRegistryKey(){
		return this.worldKey;
	}

	@Override
	public IWorldInfo getInfo() {
		return this.worldInfo;
	}

	@Override
	public Statistics getStatistics() {
		return this.stats;
	}

	@Override
	public void clearStats() {
		this.stats = new Statistics();
	}
}
