package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

public class ThemeRedNether extends ThemeBase implements ITheme {

	public ThemeRedNether() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.RED_NETHERBRICK), 200);
		walls.addBlock(BlockType.get(BlockType.NETHERRACK), 20);
		walls.addBlock(BlockType.get(BlockType.ORE_QUARTZ), 20);
		walls.addBlock(BlockType.get(BlockType.SOUL_SAND), 15);
		walls.addBlock(BlockType.get(BlockType.NETHER_WART_BLOCK), 10);
		walls.addBlock(BlockType.get(BlockType.COAL_BLOCK), 5);

		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(walls, 1500);
		floor.addBlock(BlockType.get(BlockType.RED_NETHERBRICK), 500);
		floor.addBlock(BlockType.get(BlockType.REDSTONE_BLOCK), 50);
		floor.addBlock(BlockType.get(BlockType.GOLD_BLOCK), 3);
		floor.addBlock(BlockType.get(BlockType.DIAMOND_BLOCK), 2);
		
		MetaStair stair = Stair.of(Stair.NETHERBRICK);
		MetaBlock obsidian = BlockType.get(BlockType.OBSIDIAN);
		MetaBlock redBrick = BlockType.get(BlockType.RED_NETHERBRICK);
		
		IBlockFactory lightstone = BlockType.get(BlockType.GLOWSTONE);
		IBlockFactory liquid = BlockType.get(BlockType.LAVA_FLOWING);
		
		
		
		//this.primary = new BlockSet(floor, walls, obsidian, stair, Slab.get(Slab.NETHER_BRICK), door, lightstone, liquid);
		//this.secondary = new BlockSet(floor, redBrick, obsidian, stair, Slab.get(Slab.RED_NETHER_BRICK), door, lightstone, liquid);

		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(floor)
				.setPillar(obsidian)
				.setStair(stair)
				.setSlab(Slab.of(Slab.NETHER_BRICK))
				.setDoor(Door.of(DoorType.CRIMSON))
				.setLightBlock(lightstone)
				.setLiquid(liquid);
		
		this.secondary = new BlockSet()
				.setWall(redBrick)
				.setFloor(floor)
				.setPillar(obsidian)
				.setStair(stair)
				.setSlab(Slab.of(Slab.RED_NETHER_BRICK))
				.setDoor(Door.of(DoorType.CRIMSON))
				.setLightBlock(lightstone)
				.setLiquid(liquid);
	}
	
	@Override
	public String getName() {
		return Theme.REDNETHER.name();
	}
}
