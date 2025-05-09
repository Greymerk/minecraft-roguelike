package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Wood;
import com.greymerk.roguelike.editor.blocks.WoodBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.RandomStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeSpruce extends ThemeBase implements ITheme {

	public ThemeSpruce() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.add(MetaBlock.of(Blocks.STONE_BRICKS), 40);
		walls.add(MetaBlock.of(Blocks.CRACKED_STONE_BRICKS), 20);
		walls.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 5);
		walls.add(MetaBlock.of(Blocks.COBBLESTONE), 3);
		
		RandomStair stair = new RandomStair()
				.add(Stair.of(Stair.STONEBRICK), 3)
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 1);
		
		MetaBlock logs = Wood.get(Wood.SPRUCE, WoodBlock.LOG);
		MetaBlock segmentWall = Wood.get(Wood.SPRUCE, WoodBlock.PLANK);
		MetaStair segmentStair = Stair.of(Stair.SPRUCE);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(walls)
				.stair(stair)
				.pillar(logs)
				.door(Door.of(DoorType.SPRUCE))
				.slab(Slab.get(Slab.STONEBRICK))
				.build();

		this.secondary = BlockSet.builder()
				.walls(segmentWall)
				.floor(segmentWall)
				.stair(segmentStair)
				.pillar(logs)
				.door(Door.of(DoorType.SPRUCE))
				.slab(Slab.get(Slab.SPRUCE))
				.build();
	}
	
	@Override
	public String getName() {
		return Theme.SPRUCE.name();
	}

}
