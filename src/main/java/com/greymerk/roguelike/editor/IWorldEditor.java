package com.greymerk.roguelike.editor;

import java.nio.file.Path;

import com.greymerk.roguelike.treasure.ITreasureChest;
import com.greymerk.roguelike.treasure.loot.Loot;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.math.random.Random;

public interface IWorldEditor {
	
	public boolean set(Coord pos, MetaBlock metaBlock, boolean fillAir, boolean replaceSolid);
	
	public boolean set(Coord pos, MetaBlock metaBlock);
	
	public MetaBlock getBlock(Coord pos);

	public boolean isAir(Coord pos);
	
	public BlockEntity getBlockEntity(Coord pos);
	
	public long getSeed();
	
	public Random getRandom(Coord pos);
			
	public Coord findSurface(Coord pos);
	
	public boolean isChunkLoaded(Coord pos);
	
	public boolean surroundingChunksLoaded(Coord pos);
	
	public boolean isGround(Coord pos);
	
	public boolean isOverworld();

	public boolean isSolid(Coord pos);
		
	public boolean isFaceFullSquare(Coord pos, Cardinal dir);
	
	public void fillChest(ITreasureChest chest, Random rand);
	
	public ItemStack getLootItem(Loot type, Random rand, int level);
	
	public int getMaxDepth();
	
	public DynamicRegistryManager getRegistryManager();
	
	public FeatureSet getFeatureSet();
	
	public Path getWorldDirectory();

}
