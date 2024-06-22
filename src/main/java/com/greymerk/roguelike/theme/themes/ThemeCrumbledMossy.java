package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.SilverfishBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.RandomStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockLayers;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeCrumbledMossy extends ThemeBase implements ITheme {

	public ThemeCrumbledMossy() {
		BlockWeightedRandom mossy = new BlockWeightedRandom();
		mossy.addBlock(BlockType.get(BlockType.COBBLESTONE), 60);
		mossy.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 20);
		mossy.addBlock(BlockType.get(BlockType.STONE_BRICK), 10);
		mossy.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 10);
		mossy.addBlock(SilverfishBlock.getJumble(), 5);
		mossy.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 20);
		mossy.addBlock(BlockType.get(BlockType.GRAVEL), 15);
		
		BlockWeightedRandom tuff = new BlockWeightedRandom();
		tuff.addBlock(mossy, 4);
		tuff.addBlock(MetaBlock.of(Blocks.CHISELED_TUFF_BRICKS), 3);
		tuff.addBlock(MetaBlock.of(Blocks.CHISELED_TUFF), 1);
		
		BlockLayers layered = new BlockLayers(mossy);
		layered.addLayer(1, tuff);
		layered.addLayer(3, tuff);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 20);
		pillar.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
		pillar.addBlock(SilverfishBlock.getJumble(), 3);
		pillar.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 5);
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 10);
		floor.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 4);
		floor.addBlock(BlockType.get(BlockType.COBBLESTONE), 2);
		floor.addBlock(BlockType.get(BlockType.GRAVEL), 1);
		
		RandomStair stair = new RandomStair()
				.add(Stair.of(Stair.MOSSY_COBBLE), 10)
				.add(Stair.of(Stair.COBBLE), 5)
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 3)
				.add(Stair.of(Stair.STONEBRICK), 1);
		
		this.primary = new BlockSet()
				.setWall(layered)
				.setFloor(floor)
				.setStair(stair)
				.setPillar(mossy)
				.setDoor(Door.of(DoorType.JUNGLE))
				.setSlab(Slab.of(Slab.MOSSY_COBBLE));
		
		this.secondary = this.primary;
	}
	
	@Override
	public String getName() {
		return Theme.CRUMBLEDMOSSY.name();
	}
}
