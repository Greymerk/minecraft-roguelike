package com.greymerk.roguelike.editor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.greymerk.roguelike.state.RoguelikeState;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
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

	RegistryKey<World> worldKey;
	WorldAccess world;
	
	
	public WorldEditor(ServerWorld world) {
		this.world = world;
		this.worldKey = world.getRegistryKey();
		
	}
	
	public WorldEditor(StructureWorldAccess world, RegistryKey<World> key) {
		this.world = world;
		this.worldKey = key;
	}

	@Override
	public boolean set(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid) {
		if(this.hasBlockEntity(pos)) return false;
		
		if(!fillAir && this.isAir(pos)) return false;
		if(!replaceSolid && this.isSolid(pos))	return false;
		
		try{
			world.setBlockState(pos.getBlockPos(), block.getState(), block.getFlag());
		} catch(NullPointerException npe){
			//ignore it.
		}
		
		return true;
	}
	
	@Override
	public boolean set(Coord pos, MetaBlock metaBlock) {
		return this.set(pos, metaBlock, true, true);
	}
	
	@Override
	public MetaBlock getBlock(Coord pos) {
		BlockState state = world.getBlockState(pos.getBlockPos());
		return new MetaBlock(state);
	}

	@Override
	public boolean isAir(Coord pos) {
		return world.isAir(pos.getBlockPos());
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

	public boolean isChunkLoaded(Coord pos) {
		ChunkPos cp = pos.getChunkPos();
		Chunk c = this.world.getChunk(pos.getBlockPos());
		ChunkStatus status = c.getStatus();
		if(status != ChunkStatus.FULL) return false;
		return world.isChunkLoaded(cp.x, cp.z);
	}
	
	public boolean surroundingChunksLoaded(Coord pos) {
		ChunkPos cpos = pos.getChunkPos();
		for(int x = cpos.x - 1; x <= cpos.x + 1; x++) {
			for(int z = cpos.z - 1; z <= cpos.z + 1; z++) {
				if(!this.isChunkLoaded(Coord.of(new ChunkPos(x, z)))) return false;
			}
		}
		
		return true;
		
	}

	public Coord findSurface(Coord pos) {
		
		Coord cursor = new Coord(pos.getX(), world.getTopY(), pos.getZ());
		
		while(cursor.getY() > 60) {
			MetaBlock m = this.getBlock(cursor);
			if(m.getState().isIn(BlockTags.LOGS)) continue;
			if(m.getState().isIn(BlockTags.LEAVES)) continue;
			
			if(!isAir(cursor) && !isPlant(cursor)) return cursor;
			cursor.add(Cardinal.DOWN);
		}
		
		return cursor;
	}
	
	@Override
	public boolean isSolid(Coord pos) {
		return this.world.getBlockState(pos.getBlockPos()).isSolidBlock(world, pos.getBlockPos());
	}
	
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
	
	public boolean isPlant(Coord pos) {
		BlockState bs = getBlock(pos).getState();
		if(bs.isIn(BlockTags.LOGS)) return true;
		if(bs.isIn(BlockTags.SWORD_EFFICIENT)) return true;
		return false;
	}
	
	public boolean isGround(Coord pos) {
		if(isPlant(pos)) return false;
		if(this.isAir(pos)) return false;
		
		List<TagKey<Block>> tags = new ArrayList<TagKey<Block>>();
		tags.add(BlockTags.BASE_STONE_OVERWORLD);
		tags.add(BlockTags.DIRT);
		tags.add(BlockTags.SAND);
		tags.add(BlockTags.SNOW);
		tags.add(BlockTags.STONE_ORE_REPLACEABLES);
		tags.add(BlockTags.TERRACOTTA);
		tags.add(BlockTags.SHOVEL_MINEABLE);
		
		MetaBlock m = getBlock(pos);
		
		for(TagKey<Block> tag : tags) {
			if(m.getState().isIn(tag)) return true;
		}
		return false;
	}
	
	public boolean isOverworld() {
		MinecraftServer mcServer = world.getServer();
		ServerWorld sw = mcServer.getWorld(worldKey);
		return sw.getDimensionKey().equals(DimensionTypes.OVERWORLD);
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
	public int getMaxDepth() {
		return world.getBottomY();
	}
	
	public DynamicRegistryManager getRegistryManager() {
		DynamicRegistryManager reg = this.world.getRegistryManager();
		return reg;
	}
	
	public FeatureSet getFeatureSet() {
		return this.world.getEnabledFeatures();
	}
	
	public Path getWorldDirectory() {
		return this.world.getServer().getSavePath(WorldSavePath.ROOT);
	}
	
	public GameRules getGameRules() {
		MinecraftServer server = world.getServer();
		GameRules rules = server.getGameRules();
		return rules;
	}
	
	public RoguelikeState getState() {
		MinecraftServer server = world.getServer();
		return RoguelikeState.getServerState(worldKey, server);
	}

	@Override
	public RegistryKey<World> getKey() {
		return this.worldKey;
	}
}
