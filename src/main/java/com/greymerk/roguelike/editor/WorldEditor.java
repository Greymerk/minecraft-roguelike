package com.greymerk.roguelike.editor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.greymerk.roguelike.state.RoguelikeState;
import com.greymerk.roguelike.treasure.ITreasureChest;
import com.greymerk.roguelike.treasure.loot.Loot;
import com.greymerk.roguelike.treasure.loot.rules.LootRuleManager;
import com.greymerk.roguelike.treasure.loot.rules.RoguelikeLootRules;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
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

public class WorldEditor implements IWorldEditor{

	WorldAccess world;
	private Map<Block, Integer> stats;
	private LootRuleManager loot;
	
	public WorldEditor(StructureWorldAccess world){
		this.world = world;
		stats = new HashMap<Block, Integer>();
		this.loot = RoguelikeLootRules.getLoot(world.getEnabledFeatures(), world.getRegistryManager());
	}

	public WorldEditor(World world) {
		this.world = world;
		stats = new HashMap<Block, Integer>();
		this.loot = RoguelikeLootRules.getLoot(world.getEnabledFeatures(), world.getRegistryManager());
	}

	@Override
	public boolean set(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid) {
		MetaBlock currentBlock = getBlock(pos);
		
		if(currentBlock.getBlock() == Blocks.CHEST) return false;
		if(currentBlock.getBlock() == Blocks.TRAPPED_CHEST) return false;
		if(currentBlock.getBlock() == Blocks.SPAWNER) return false;
		
		if(!fillAir && this.isAir(pos)) return false;
		if(!replaceSolid && this.isSolid(pos))	return false;
		
		try{
			world.setBlockState(pos.getBlockPos(), block.getState(), block.getFlag());
		} catch(NullPointerException npe){
			//ignore it.
		}
		
		Block type = block.getBlock();
		Integer count = stats.get(type);
		if(count == null){
			stats.put(type, 1);	
		} else {
			stats.put(type, count + 1);
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
	public Random getRandom(Coord pos) {
		return new CheckedRandom(Objects.hash(getSeed(), pos.hashCode()));
	}

	public boolean isChunkLoaded(Coord pos) {
		ChunkPos cp = pos.getChunkPos();
		return world.isChunkLoaded(cp.x, cp.z);
	}
	
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

	public Coord findSurface(Coord pos) {
		
		Coord cursor = new Coord(pos.getX(), 256, pos.getZ());
		
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
		
		MetaBlock m = getBlock(pos);
		
		for(TagKey<Block> tag : tags) {
			if(m.getState().isIn(tag)) return true;
		}
		return false;
	}
	
	public boolean isOverworld() {
		return this.world.getDimension().hasSkyLight();
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
	public void fillChest(ITreasureChest chest, Random rand) {
		this.loot.process(rand, chest);
	}
	
	public ItemStack getLootItem(Loot type, Random rand, int level) {
		return Loot.getProvider(type, level, this.world.getEnabledFeatures(), getRegistryManager()).get(rand);
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
		return RoguelikeState.getServerState(server);
	}
}
