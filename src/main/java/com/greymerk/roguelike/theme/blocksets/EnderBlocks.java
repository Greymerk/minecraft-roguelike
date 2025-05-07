package com.greymerk.roguelike.theme.blocksets;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlackStone;
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

import net.minecraft.block.Blocks;

public class EnderBlocks extends BlockSet implements IBlockSet {

	public EnderBlocks() {
		this.walls = new BlockWeightedRandom()
			.add(BlackStone.get(BlackStone.STONE), 20)
			.add(BlackStone.get(BlackStone.BRICK), 3)
			.add(BlackStone.get(BlackStone.CRACKED_BRICK), 7)
			.add(BlackStone.get(BlackStone.GILDED), 1);
		
		this.pillar = new BlockJumble()
			.add(this.walls)
			.add(BlackStone.get(BlackStone.POLISHED));
		
		this.floor = BlockFloor.of(BlockCheckers.of(MetaBlock.of(Blocks.POLISHED_DIORITE), BlackStone.get(BlackStone.POLISHED)));

		this.stair = MetaStair.of(BlackStone.fromType(BlackStone.STONE_STAIR));
		this.door = Door.of(DoorType.CRIMSON);
		this.slab = Slab.get(Slab.BLACK_STONE);
		
		this.lightblock = MetaBlock.of(Blocks.GLOWSTONE);
		this.liquid = MetaBlock.of(Blocks.WATER);
	}
}
