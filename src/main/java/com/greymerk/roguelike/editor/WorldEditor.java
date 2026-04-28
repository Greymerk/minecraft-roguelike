package com.greymerk.roguelike.editor;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.greymerk.roguelike.config.Config;

public class WorldEditor implements IWorldEditor{

	private Level world;
	private ResourceKey<Level> worldKey;
	private IWorldInfo worldInfo;
	private Statistics stats;
	
	public static WorldEditor of(Level world) {
		return new WorldEditor(world);
	}
	
	public static WorldEditor of(ServerLevel world) {
		return new WorldEditor(world);
	}
	
	@Deprecated
	public WorldEditor(WorldGenLevel world, ResourceKey<Level> worldKey){}

	private WorldEditor(Level world) {
		this.world = world;
		this.worldKey = world.dimension();
		this.worldInfo = WorldInfo.of(world, worldKey);
		this.stats = new Statistics();
	}

	@Override
	public boolean set(Coord pos, MetaBlock block, Predicate<BlockContext> p) {
		if(!p.and(Fill.IGNORE_BLOCK_ENTITIES).test(BlockContext.of(this, pos, block))) return false;
		return world.setBlock(pos.getBlockPos(), block.getState(), block.getFlag());
	}
	
	@Override
	public boolean set(Coord pos, MetaBlock block) {
		if(!Fill.IGNORE_BLOCK_ENTITIES.test(BlockContext.of(this, pos, block))) return false;
		return world.setBlock(pos.getBlockPos(), block.getState(), block.getFlag());
	}
	
	@Override
	public MetaBlock getBlock(Coord pos) {
		BlockState state = world.getBlockState(pos.getBlockPos());
		return MetaBlock.of(state);
	}

	@Override
	public boolean isAir(Coord pos) {
		return this.world.isEmptyBlock(pos.getBlockPos());
	}

	
	@Override
	public long getSeed(Coord pos) {
		return Objects.hash(this.worldInfo.getSeed(), pos.hashCode());
	}
	
	@Override
	public RandomSource getRandom(Coord pos) {
		if(!Config.ofBoolean(Config.DETERMINISTIC)) {
			long time = Date.from(Instant.now()).getTime();
			long seed = Objects.hash(time, pos);
			return new LegacyRandomSource(seed);
		}
		return new LegacyRandomSource(getSeed(pos));
	}

	@Override
	public boolean isChunkLoaded(Coord pos) {
		ChunkPos cp = pos.getChunkPos();
		return world.hasChunk(cp.x(), cp.z());
	}
	
	@Override
	public boolean surroundingChunksLoaded(Coord pos) {
		ChunkPos cpos = pos.getChunkPos();
		for(int x = cpos.x() - 1; x <= cpos.x() + 1; x++) {
			for(int z = cpos.z() - 1; z <= cpos.z() + 1; z++) {
				if(!world.hasChunk(x, z)) return false;
				ChunkAccess chunk = world.getChunk(x, z);
				ChunkStatus status = chunk.getPersistedStatus();
				if(status != ChunkStatus.FULL) return false;
			}
		}
		
		return true;
		
	}

	@Override
	public boolean isSolid(Coord pos) {
		return this.world.getBlockState(pos.getBlockPos()).isRedstoneConductor(world, pos.getBlockPos());
	}
	
	@Override
	public boolean isSupported(Coord pos) {
		if(pos.getY() <= world.getMinY()) return false;
		Coord under = pos.copy().add(Cardinal.DOWN);
		Block b = this.world.getBlockState(under.getBlockPos()).getBlock();
		if(b instanceof FallingBlock) {
			return isSupported(under);
		}
		
		if(!FallingBlock.isFree(world.getBlockState(under.getBlockPos()))) return true;
		return false;
	}

	@Override
	public boolean hasBlockEntity(Coord pos) {
		return this.world.getBlockEntity(pos.getBlockPos()) != null;
	}
	
	private Optional<BlockEntity> getBlockEntity(Coord pos) {
		BlockEntity be = world.getBlockEntity(pos.getBlockPos());
		if(be == null) return Optional.empty();
		return Optional.of(be);
	}
	
	@Override
	public <T> Optional<T> getBlockEntity(Coord pos, Class<T> beClass){
		Optional<BlockEntity> obe = this.getBlockEntity(pos);
		if(obe.isEmpty()) return Optional.empty();
		
		BlockEntity be = obe.get();
		if(beClass.isInstance(be)) {
			return Optional.of(beClass.cast(be));
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public <T> Optional<T> setBlockEntity(Coord pos, MetaBlock block, Class<T> beClass){
		if(!this.set(pos, block)) return Optional.empty();
		return this.getBlockEntity(pos, beClass);
	}
	
	@Override
	public boolean isFaceFullSquare(Coord pos, Cardinal dir) {
		BlockState b = this.world.getBlockState(pos.getBlockPos());
		Direction facing = Cardinal.facing(dir);
		VoxelShape shape = b.getBlockSupportShape(world, pos.getBlockPos());
		VoxelShape collision = b.getCollisionShape(world, pos.getBlockPos());
		boolean isShapeSquare = Block.isFaceFull(shape, facing);
		boolean isCollisionSquare = Block.isFaceFull(collision, facing);
		return isShapeSquare || isCollisionSquare;
	}
	
	@Override
	public Coord findSurface(Coord pos) {

		Coord cursor = pos.withY(world.getMaxY());
		int seaLevel = this.worldInfo.getSeaLevel();

		while(cursor.getY() > seaLevel - 3) {
			MetaBlock m = this.getBlock(cursor);
			if(m.isIn(List.of(BlockTags.LOGS, BlockTags.LEAVES))) {
				cursor.add(Cardinal.DOWN);
				continue;
			}
			
			if(!world.isEmptyBlock(cursor.getBlockPos()) && !m.isPlant()) return cursor;
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
	public ResourceKey<Level> getRegistryKey(){
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
