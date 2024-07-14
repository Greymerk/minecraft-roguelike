package com.greymerk.roguelike.editor;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;

import com.greymerk.roguelike.state.RoguelikeState;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionTypes;

public class WorldEditor implements IWorldEditor{

	WorldAccess world;
	RegistryKey<World> worldKey;
	
	public WorldEditor(StructureWorldAccess world, RegistryKey<World> worldKey){
		this.world = world;
		this.worldKey = worldKey;
	}

	public WorldEditor(World world) {
		this.world = world;
		this.worldKey = world.getRegistryKey();
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
	public long getSeed() {
		MinecraftServer server = this.world.getServer();
		ServerWorld sw = server.getOverworld();
		return sw.getSeed();
	}
	
	@Override
	public long getSeed(Coord pos) {
		return Objects.hash(getSeed(), pos.hashCode());
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
	public Coord findSurface(Coord pos) {
		
		Coord cursor = new Coord(pos.getX(), world.getTopY(), pos.getZ());
		
		while(cursor.getY() > 60) {
			MetaBlock m = this.getBlock(cursor);
			if(m.getState().isIn(BlockTags.LOGS)) continue;
			if(m.getState().isIn(BlockTags.LEAVES)) continue;
			
			if(!this.isAir(cursor) && !m.isPlant()) return cursor;
			cursor.add(Cardinal.DOWN);
		}
		
		return cursor;
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
	public boolean isOverworld() {
		MinecraftServer mcServer = world.getServer();
		ServerWorld sw = mcServer.getWorld(worldKey);
		return sw.getDimensionEntry().matchesKey(DimensionTypes.OVERWORLD);
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
	public DynamicRegistryManager getRegistryManager() {
		return this.world.getRegistryManager();
	}
	
	@Override
	public FeatureSet getFeatureSet() {
		return this.world.getEnabledFeatures();
	}
	
	@Override
	public Path getWorldDirectory() {
		return this.world.getServer().getSavePath(WorldSavePath.ROOT);
	}
	
	@Override
	public GameRules getGameRules() {
		return world.getServer().getGameRules();
	}
	
	@Override
	public RegistryKey<World> getRegistryKey(){
		return this.worldKey;
	}
	
	@Override
	public RoguelikeState getState() {
		return RoguelikeState.getServerState(worldKey, world.getServer());
	}
}
