package com.greymerk.roguelike.editor;

import java.nio.file.Path;
import java.util.Optional;

import com.greymerk.roguelike.state.RoguelikeState;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.storage.LevelResource;

public class WorldInfo implements IWorldInfo {

	private Level world;
	ResourceKey<Level> worldKey;
	
	public static WorldInfo of(Level world, ResourceKey<Level> worldKey) {
		return new WorldInfo(world, worldKey);
	}
	
	private WorldInfo(Level world, ResourceKey<Level> worldKey) {
		this.world = world;
		this.worldKey = worldKey;
	}
	
	private ServerLevel getServerWorld() {
		return this.world.getServer().getLevel(this.worldKey);
	}
	
	@Override
	public int getSeaLevel() {
		if(this.isFlat()) return 64;
		
		return world.getSeaLevel();
	}

	@Override
	public int getTopYInclusive() {
		return world.getMaxY();
	}
	
	@Override
	public int getBottomY() {
		return world.getMinY();
	}
	
	@Override
	public boolean isOverworld() {
		MinecraftServer mcServer = world.getServer();
		ServerLevel sw = mcServer.getLevel(worldKey);
		return sw.dimensionTypeRegistration().is(BuiltinDimensionTypes.OVERWORLD);
	}
	
	@Override
	public RegistryAccess getRegistryManager() {
		return this.world.registryAccess();
	}
	
	@Override
	public FeatureFlagSet getFeatureSet() {
		return this.world.enabledFeatures();
	}
	
	@Override
	public Path getWorldDirectory() {
		return this.world.getServer().getWorldPath(LevelResource.ROOT);
	}
	
	@Override
	public GameRules getGameRules() {
		return this.getServerWorld().getGameRules();
	}
	
	@Override
	public ResourceKey<Level> getRegistryKey(){
		return this.worldKey;
	}
	
	@Override
	public RoguelikeState getState() {
		return RoguelikeState.getServerState(worldKey, world.getServer());
	}
	
	@Override
	public long getSeed() {
		MinecraftServer server = this.world.getServer();
		ServerLevel sw = server.overworld();
		return sw.getSeed();
	}
	
	@Override
	public Optional<Coord> getStructureLocation(ResourceKey<StructureSet> key, ChunkPos cpos){
		MinecraftServer mcServer = world.getServer();
		ServerLevel sw = mcServer.getLevel(worldKey);
		ChunkGeneratorStructureState calculator = sw.getChunkSource().getGeneratorState();
		RegistryAccess reg = world.registryAccess();
		Registry<StructureSet> structures = reg.lookupOrThrow(Registries.STRUCTURE_SET);
		StructureSet structure = structures.getValue(key);
		StructurePlacement placement = structure.placement(); 
		
		if(!placement.isStructureChunk(calculator, cpos.x(), cpos.z())) return Optional.empty();
		return Optional.of(Coord.of(placement.getLocatePos(cpos)));
	}



	@Override
	public int getLastFloorDepth() {
		int minY = this.getBottomY();
		return minY - Math.floorMod(minY, 10) + 20;
	}

	
	@Override
	public int getFirstFloorDepth() {
		int sl = this.getSeaLevel();
		return (sl - Math.floorMod(sl, 10)) - 10;
	}
	
	public boolean isFlat() {
		return this.world.getServer().overworld().isFlat();
	}
	
}
