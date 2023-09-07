package com.greymerk.roguelike.editor.theme;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.door.IDoor;
import com.greymerk.roguelike.editor.blocks.slab.ISlab;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.factories.BlockFloor;


public class BlockSet implements IBlockSet {

	protected BlockFloor floor;
	protected IBlockFactory walls;
	protected IBlockFactory pillar;
	protected IStair stair;
	protected ISlab slab;
	protected IDoor door;
	protected IBlockFactory lightblock;
	protected IBlockFactory liquid;
	
	public BlockSet(){
		
	}
	
	public BlockSet(IBlockFactory floor, IBlockFactory walls, IBlockFactory pillar,
			IStair stair, ISlab slab, IDoor door, IBlockFactory lightblock, IBlockFactory liquid){
		
		this.floor = new BlockFloor(floor);
		this.walls = walls;
		this.pillar = pillar;
		this.stair = stair;
		this.slab = slab;
		this.door = door;
		this.lightblock = lightblock;
		this.liquid = liquid;
		
	}

	public BlockSet(IBlockFactory floor, IBlockFactory walls, IStair stair,
			IBlockFactory pillar, IDoor door){
		this(floor, walls, pillar, stair, 
			Slab.get(Slab.STONE),
			door,
			new MetaBlock(BlockType.get(BlockType.GLOWSTONE)),
			new MetaBlock(BlockType.get(BlockType.WATER_FLOWING))
		);
	}
	
	public BlockSet(IBlockFactory floor, IBlockFactory walls, IStair stair, IBlockFactory pillar){
		this(floor, walls, stair, pillar,
			new Door(DoorType.get(DoorType.OAK))
		);
	}

	public BlockSet(IBlockFactory walls, IStair stair, IBlockFactory pillar){
		this(walls, walls, stair, pillar);
	}	
	
	@Override
	public IBlockFactory getWall() {
		return walls != null ? walls : BlockType.get(BlockType.STONE_BRICK);
	}

	@Override
	public IStair getStair() {
		return stair != null ? this.stair : new MetaStair(StairType.STONEBRICK);
	}

	@Override
	public IBlockFactory getPillar() {
		return pillar != null ? this.pillar : this.getWall();
	}

	@Override
	public BlockFloor getFloor() {
		return this.floor != null ? this.floor : new BlockFloor(this.getWall());
	}

	@Override
	public IDoor getDoor() {
		return this.door != null ? this.door : new Door(DoorType.OAK);
	}

	@Override
	public IBlockFactory getLightBlock() {
		return this.lightblock != null ? this.lightblock : BlockType.get(BlockType.GLOWSTONE);
	}

	@Override
	public IBlockFactory getLiquid() {
		return this.liquid != null ? this.liquid : BlockType.get(BlockType.WATER_FLOWING);
	}

	public void setSlab(ISlab slab) {
		this.slab = slab;
	}
	
	@Override
	public ISlab getSlab() {
		return this.slab != null ? this.slab : Slab.get(Slab.STONE);
	}
}
