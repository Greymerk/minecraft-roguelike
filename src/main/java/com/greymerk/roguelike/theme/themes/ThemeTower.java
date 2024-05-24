package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

public class ThemeTower extends ThemeBase implements ITheme {

	public ThemeTower(){
	
		BlockJumble stone = new BlockJumble();
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK));
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.AIR), 5);
		walls.addBlock(stone, 30);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 10);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);
		
		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(walls)
				.setStair(Stair.of(Stair.STONEBRICK))
				.setPillar(walls)
				.setDoor(Door.of(DoorType.SPRUCE))
				.setSlab(Slab.of(Slab.COBBLE));
		this.secondary = this.primary;
	}
	
	public String getName() {
		return Theme.TOWER.name();
	}
}
