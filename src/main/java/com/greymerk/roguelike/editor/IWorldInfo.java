package com.greymerk.roguelike.editor;

import java.nio.file.Path;
import java.util.Optional;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import com.greymerk.roguelike.state.RoguelikeState;

/**
 *  Interface for a class which provides a set of read only methods
 *  for getting information about the world
 *  
 *  implemented by {@code WorldInfo}
 */
public interface IWorldInfo {

	public int getSeaLevel();
	
	/**
	 * Returns the highest buildable Block Y
	 */
	public int getTopYInclusive();
	
	public int getBottomY();
	
	public int getLastFloorDepth();
		
	/**
	 * Is this an Overworld type dimension
	 */
	public boolean isOverworld();
	
	public RegistryAccess getRegistryManager();
	
	public FeatureFlagSet getFeatureSet();
	
	public Path getWorldDirectory();
	
	public RoguelikeState getState();
	
	public GameRules getGameRules();
	
	public ResourceKey<Level> getRegistryKey();
	
	public long getSeed();
	
	public Optional<Coord> getStructureLocation(ResourceKey<StructureSet> key, ChunkPos cpos);

	/**
	 * This is the first levelY that would appear below sea level
	 * {@code sealevel - (sealevel % 10 - 10)}
	 */
	int getFirstFloorDepth();
	
}
