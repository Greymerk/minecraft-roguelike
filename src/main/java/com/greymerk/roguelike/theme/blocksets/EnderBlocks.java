package com.greymerk.roguelike.theme.blocksets;

import com.greymerk.roguelike.editor.blocks.BlackStone;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.factories.BlockCheckers;
import com.greymerk.roguelike.editor.factories.BlockFloor;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.IBlockSet;

public class EnderBlocks extends BlockSet implements IBlockSet {

	public EnderBlocks() {
		BlockWeightedRandom wall = new BlockWeightedRandom();
		wall.addBlock(BlackStone.get(BlackStone.STONE), 20);
		wall.addBlock(BlackStone.get(BlackStone.BRICK), 3);
		wall.addBlock(BlackStone.get(BlackStone.CRACKED_BRICK), 7);
		wall.addBlock(BlackStone.get(BlackStone.GILDED), 1);
		this.walls = wall;
		
		BlockJumble pillar = new BlockJumble();
		pillar.addBlock(wall);
		pillar.addBlock(BlackStone.get(BlackStone.POLISHED));
		this.pillar = pillar;
		
		BlockCheckers floor = new BlockCheckers(BlockType.get(BlockType.DIORITE_POLISHED), BlackStone.get(BlackStone.POLISHED));
		this.floor = new BlockFloor(floor);

		this.stair = MetaStair.of(BlackStone.fromType(BlackStone.STONE_STAIR));
		this.door = Door.of(DoorType.CRIMSON);
		this.slab = Slab.get(Slab.BLACK_STONE);
		
		this.lightblock = BlockType.get(BlockType.GLOWSTONE);
		this.liquid = BlockType.get(BlockType.WATER_FLOWING);
	}
}
