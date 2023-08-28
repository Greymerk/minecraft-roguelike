package com.greymerk.roguelike.editor.theme.themes;

import java.util.ArrayList;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.SilverfishBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.filter.IFilter;
import com.greymerk.roguelike.editor.filter.VineFilter;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeMossy extends ThemeBase implements ITheme {

	public ThemeMossy(){
				
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 60);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 30);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 10);
		walls.addBlock(SilverfishBlock.getJumble(), 5);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 10);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 15);
		
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
		
		MetaStair stair = new MetaStair(StairType.MOSSY_STONEBRICK);
		
		this.primary = new BlockSet(floor, walls, stair, walls, 
				new Door(DoorType.IRON)
				);
		this.secondary = this.primary;
		
		this.filters = new ArrayList<IFilter>();
		this.filters.add(new VineFilter());
	}

	@Override
	public String getName() {
		return Theme.MOSSY.name();
	}
}
