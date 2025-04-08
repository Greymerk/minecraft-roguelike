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
	
	public static Builder builder() {
		return new Builder();
	}
	
	public BlockSet() {}
	
	private BlockSet(BlockFloor floor, 
			IBlockFactory walls,
			IBlockFactory pillar,
			IStair stair,
			ISlab slab,
			IDoor door,
			IBlockFactory lightblock,
			IBlockFactory liquid){
		this.floor = floor;
		this.walls = walls;
		this.pillar = pillar;
		this.stair = stair;
		this.slab = slab;
		this.door = door;
		this.lightblock = lightblock;
		this.liquid = liquid;
	}
	
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
	
	public void setLiquid(MetaBlock liquid) {
		this.liquid = liquid;
	}
	
	@Override
	public ISlab getSlab() {
		return this.slab != null ? this.slab : Slab.get(Slab.STONE);
	}
	
	public static class Builder {
		
		private BlockFloor floor;
		private IBlockFactory walls;
		private IBlockFactory pillar;
		private IStair stair;
		private ISlab slab;
		private IDoor door;
		private IBlockFactory lightblock;
		private IBlockFactory liquid;
		
		public Builder floor(IBlockFactory floor) {
			this.floor = BlockFloor.of(floor);
			return this;
		}
		
		public Builder walls(IBlockFactory walls) {
			this.walls = walls;
			return this;
		}
		
		public Builder pillar(IBlockFactory pillar) {
			this.pillar = pillar;
			return this;
		}
		
		public Builder stair(IStair stair) {
			this.stair = stair;
			return this;
		}
		
		public Builder slab(ISlab slab) {
			this.slab = slab;
			return this;
		}
		
		public Builder door(IDoor door) {
			this.door = door;
			return this;
		}
		
		public Builder lightblock(IBlockFactory lightblock) {
			this.lightblock = lightblock;
			return this;
		}
		
		public Builder liquid(IBlockFactory liquid) {
			this.liquid = liquid;
			return this;
		}
		
		public BlockSet build() {
			return new BlockSet(floor, walls, pillar, stair, slab, door, lightblock, liquid);
		}
	}
}
