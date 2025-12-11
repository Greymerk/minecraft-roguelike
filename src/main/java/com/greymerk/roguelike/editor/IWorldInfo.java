package com.greymerk.roguelike.editor;

import java.nio.file.Path;
import java.util.Optional;

import com.greymerk.roguelike.state.RoguelikeState;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.rule.GameRules;

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
	
	public DynamicRegistryManager getRegistryManager();
	
	public FeatureSet getFeatureSet();
	
	public Path getWorldDirectory();
	
	public RoguelikeState getState();
	
	public GameRules getGameRules();
	
	public RegistryKey<World> getRegistryKey();
	
	public long getSeed();
	
	public Optional<Coord> getStructureLocation(RegistryKey<StructureSet> key, ChunkPos cpos);

	/**
	 * This is the first levelY that would appear below sea level
	 * {@code sealevel - (sealevel % 10 - 10)}
	 */
	int getFirstFloorDepth();
	
}
