package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import greymerk.roguelike.worldgen.BlockProvider;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;

public class BlockSet implements IBlockSet {

	private IBlockFactory floor;
	private IBlockFactory walls;
	private IStair stair;
	private IBlockFactory pillar;
	
	public BlockSet(IBlockFactory floor, IBlockFactory walls, IStair stair, IBlockFactory pillar){
		this.floor = floor;
		this.walls = walls;
		this.stair = stair;
		this.pillar = pillar;
	}
	
	public BlockSet(IBlockFactory walls, IStair stair, IBlockFactory pillar){
		this(walls, walls, stair, pillar);
	}
	
	public BlockSet(JsonObject json){
		
		this.walls = BlockProvider.create(json.get("walls").getAsJsonObject());

		if(json.has("floor")){
			this.floor = BlockProvider.create(json.get("floor").getAsJsonObject());
		} else {
			this.floor = this.walls;
		}
		
		JsonObject stair = json.get("stair").getAsJsonObject();
		this.stair = stair.has("data")
				? new MetaStair(new MetaBlock(stair.get("data").getAsJsonObject()))
				: new MetaStair(new MetaBlock(stair));
		
		this.pillar = BlockProvider.create(json.get("pillar").getAsJsonObject());
	}
	
	@Override
	public IBlockFactory getFill() {
		return walls;
	}

	@Override
	public IStair getStair() {
		return stair;
	}

	@Override
	public IBlockFactory getPillar() {
		return pillar;
	}

	@Override
	public IBlockFactory getFloor() {
		return this.floor;
	}
}
