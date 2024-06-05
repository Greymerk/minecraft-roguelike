package com.greymerk.roguelike.theme;

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
import com.greymerk.roguelike.editor.blocks.stair.Stair;
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
	
	public BlockSet(){}
	
	@Override
	public IBlockFactory getWall() {
		return walls != null ? walls : BlockType.get(BlockType.STONE_BRICK);
	}

	@Override
	public IStair getStair() {
		return stair != null ? this.stair : MetaStair.of(Stair.STONEBRICK);
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
		return this.door != null ? this.door : Door.of(DoorType.OAK);
	}

	@Override
	public IBlockFactory getLightBlock() {
		return this.lightblock != null ? this.lightblock : BlockType.get(BlockType.GLOWSTONE);
	}

	@Override
	public IBlockFactory getLiquid() {
		return this.liquid != null ? this.liquid : BlockType.get(BlockType.WATER_FLOWING);
	}

	@Override
	public IBlockSet setSlab(ISlab slab) {
		this.slab = slab;
		return this;
	}
	
	public void setLiquid(MetaBlock liquid) {
		this.liquid = liquid;
	}
	
	@Override
	public ISlab getSlab() {
		return this.slab != null ? this.slab : Slab.get(Slab.STONE);
	}

	@Override
	public IBlockSet setFloor(IBlockFactory floor) {
		this.floor = new BlockFloor(floor);
		return this;
	}

	@Override
	public IBlockSet setWall(IBlockFactory wall) {
		this.walls = wall;
		return this;
	}

	@Override
	public IBlockSet setPillar(IBlockFactory pillar) {
		this.pillar = pillar;
		return this;
	}

	@Override
	public IBlockSet setStair(IStair stair) {
		this.stair = stair;
		return this;
	}

	@Override
	public IBlockSet setDoor(IDoor door) {
		this.door = door;
		return this;
	}

	@Override
	public IBlockSet setLightBlock(IBlockFactory lightBlock) {
		this.lightblock = lightBlock;
		return this;
	}

	@Override
	public IBlockSet setLiquid(IBlockFactory liquid) {
		this.liquid = liquid;
		return this;
	}
}
