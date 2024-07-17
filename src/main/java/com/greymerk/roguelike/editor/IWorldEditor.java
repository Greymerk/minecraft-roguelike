package com.greymerk.roguelike.editor;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import com.greymerk.roguelike.state.RoguelikeState;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public interface IWorldEditor {
	
	public boolean set(Coord pos, MetaBlock metaBlock);
	
	public boolean set(Coord pos, MetaBlock block, Predicate<BlockContext> p);
		
	public MetaBlock getBlock(Coord pos);
	
	public boolean hasBlockEntity(Coord pos);
	
	public BlockEntity getBlockEntity(Coord pos);
	
	public long getSeed();
	
	public long getSeed(Coord pos);
	
	public Random getRandom(Coord pos);
			
	public Coord findSurface(Coord pos);
	
	public boolean isChunkLoaded(Coord pos);
	
	public boolean surroundingChunksLoaded(Coord pos);
	
	public boolean isOverworld();

	public boolean isSolid(Coord pos);
		
	public boolean isSupported(Coord pos);
	
	public boolean isFaceFullSquare(Coord pos, Cardinal dir);
	
	public DynamicRegistryManager getRegistryManager();
	
	public FeatureSet getFeatureSet();
	
	public Path getWorldDirectory();
	
	public GameRules getGameRules();
	
	public RegistryKey<World> getRegistryKey();
	
	public RoguelikeState getState();

	public boolean isAir(Coord pos);
	
	public Optional<Coord> getStructureLocation(RegistryKey<StructureSet> key, ChunkPos cpos);
}
