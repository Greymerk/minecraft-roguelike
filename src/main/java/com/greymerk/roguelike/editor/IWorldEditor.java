package com.greymerk.roguelike.editor;

import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/** Interface for class that provides read/write methods for editing
 *  a world.
 *  
 *  Implemented by {@code WorldEditor} 
 */
public interface IWorldEditor {
	
	public boolean set(Coord pos, MetaBlock metaBlock);
	
	public boolean set(Coord pos, MetaBlock block, Predicate<BlockContext> p);
		
	public MetaBlock getBlock(Coord pos);
	
	public boolean hasBlockEntity(Coord pos);
	
	public Optional<BlockEntity> getBlockEntity(Coord pos);
	
	public long getSeed(Coord pos);
	
	public Random getRandom(Coord pos);
	
	public boolean isChunkLoaded(Coord pos);
	
	public boolean surroundingChunksLoaded(Coord pos);
	
	public boolean isSolid(Coord pos);
		
	public boolean isSupported(Coord pos);
	
	public boolean isFaceFullSquare(Coord pos, Cardinal dir);
	
	public RegistryKey<World> getRegistryKey();

	public boolean isAir(Coord pos);
	
	public IWorldInfo getInfo();

	Coord findSurface(Coord pos);

	int getDungeonEntryDepth(Coord origin);
	
}
