package com.greymerk.roguelike.editor.theme;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.door.IDoor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;


public class BlockSet implements IBlockSet {

	private IBlockFactory floor;
	private IBlockFactory walls;
	private IStair stair;
	private IBlockFactory pillar;
	private IDoor door;
	private IBlockFactory lightblock;
	private IBlockFactory liquid;
	
	public BlockSet(){
		
	}
	
	public BlockSet(IBlockFactory floor, IBlockFactory walls, IStair stair, IBlockFactory pillar,
					IDoor door, IBlockFactory lightblock, IBlockFactory liquid){
		
		this.floor = floor;
		this.walls = walls;
		this.stair = stair;
		this.pillar = pillar;
		this.door = door;
		this.lightblock = lightblock;
		this.liquid = liquid;
		
	}

	public BlockSet(IBlockFactory floor, IBlockFactory walls, IStair stair, IBlockFactory pillar,
			IDoor door){
		this(floor, walls, stair, pillar, door,
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
	
	public BlockSet(IBlockSet toCopy){
		this.walls = toCopy.getWall();
		this.floor = toCopy.getFloor();
		this.stair = toCopy.getStair();
		this.pillar = toCopy.getPillar();
		this.door = toCopy.getDoor();
		this.lightblock = toCopy.getLightBlock();
		this.liquid = toCopy.getLiquid();
	}
	
	public BlockSet(IBlockSet base, IBlockSet other){
		this.walls = other.getWall() != null ? other.getWall() : base.getWall();
		this.floor = other.getFloor() != null ? other.getFloor() : base.getFloor();
		this.stair = other.getStair() != null ? other.getStair() : base.getStair();
		this.pillar = other.getPillar() != null ? other.getPillar() : base.getPillar();
		this.door = other.getDoor() != null ? other.getDoor() : base.getDoor();
		this.lightblock = other.getLightBlock() != null ? other.getLightBlock() : base.getLightBlock();
		this.liquid = other.getLiquid() != null ? other.getLiquid() : base.getLiquid();
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
	public IBlockFactory getFloor() {
		return this.floor != null ? this.floor : this.getWall();
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
}
