package com.greymerk.roguelike.editor;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import com.greymerk.roguelike.state.RoguelikeState;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;

public class WorldInfo implements IWorldInfo {

	private WorldAccess world;
	RegistryKey<World> worldKey;
	
	public static WorldInfo of(WorldAccess world, RegistryKey<World> worldKey) {
		return new WorldInfo(world, worldKey);
	}
	
	private WorldInfo(WorldAccess world, RegistryKey<World> worldKey) {
		this.world = world;
		this.worldKey = worldKey;
	}
	
	@Override
	public int getSeaLevel() {
		if(this.isFlat()) return 64;
		
		return world.getSeaLevel();
	}

	@Override
	public int getTopYInclusive() {
		return world.getTopYInclusive();
	}
	
	@Override
	public int getBottomY() {
		return world.getBottomY();
	}
	
	@Override
	public boolean isOverworld() {
		MinecraftServer mcServer = world.getServer();
		ServerWorld sw = mcServer.getWorld(worldKey);
		return sw.getDimensionEntry().matchesKey(DimensionTypes.OVERWORLD);
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
	
	@Override
	public long getSeed() {
		MinecraftServer server = this.world.getServer();
		ServerWorld sw = server.getOverworld();
		return sw.getSeed();
	}
	
	@Override
	public Optional<Coord> getStructureLocation(RegistryKey<StructureSet> key, ChunkPos cpos){
		MinecraftServer mcServer = world.getServer();
		ServerWorld sw = mcServer.getWorld(worldKey);
		StructurePlacementCalculator calculator = sw.getChunkManager().getStructurePlacementCalculator();
		DynamicRegistryManager reg = world.getRegistryManager();
		Registry<StructureSet> structures = reg.getOrThrow(RegistryKeys.STRUCTURE_SET);
		StructureSet structure = structures.get(key);
		StructurePlacement placement = structure.placement(); 
		
		if(!placement.shouldGenerate(calculator, cpos.x, cpos.z)) return Optional.empty();
		return Optional.of(Coord.of(placement.getLocatePos(cpos)));
	}

	@Override
	public Coord findSurface(Coord pos) {

		Coord cursor = pos.withY(world.getTopYInclusive());

		int seaLevel = this.getSeaLevel();
		while(cursor.getY() > seaLevel - 5) {
			MetaBlock m = MetaBlock.of(world.getBlockState(cursor.getBlockPos()));
			if(m.isIn(List.of(BlockTags.LOGS, BlockTags.LEAVES))) continue;
			
			if(!world.isAir(cursor.getBlockPos()) && !m.isPlant()) return cursor;
			cursor.add(Cardinal.DOWN);
		}
		
		return cursor;
	}

	@Override
	public int getLastFloorDepth() {
		int minY = this.getBottomY();
		return minY - Math.floorMod(minY, 10) + 20;
	}

	@Override
	public int getDungeonEntryDepth(Coord origin) {
		Coord surface = this.findSurface(origin);
		return (surface.getY() - Math.floorMod(surface.getY(), 10)) - 10;
	}
	
	@Override
	public int getFirstFloorDepth() {
		int sl = this.getSeaLevel();
		return (sl - Math.floorMod(sl, 10)) - 10;
	}
	
	public boolean isFlat() {
		return this.world.getServer().getOverworld().isFlat();
	}
	
}
