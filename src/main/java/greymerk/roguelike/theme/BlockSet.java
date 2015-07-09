package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockFactory;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BlockSet implements IBlockSet {

	private IBlockFactory floor;
	private IBlockFactory fill;
	private MetaBlock stair;
	private IBlockFactory pillar;
	
	public BlockSet(IBlockFactory floor, IBlockFactory fill, MetaBlock stair, IBlockFactory pillar){
		this.floor = floor;
		this.fill = fill;
		this.stair = stair;
		this.pillar = pillar;
	}
	
	public BlockSet(IBlockFactory fill, MetaBlock stair, IBlockFactory pillar){
		this.floor = fill;
		this.fill = fill;		
		this.stair = stair;
		this.pillar = pillar;
	}
	
	public BlockSet(JsonObject json){
		
		JsonObject walls = json.get("walls").getAsJsonObject();
		String type = walls.get("type").getAsString();
		JsonElement data = walls.get("data");
		this.fill = BlockFactory.create(type, data);

		if(json.has("floor")){
			JsonObject floor = json.get("floor").getAsJsonObject();
			type = floor.get("type").getAsString();
			data = floor.get("data");
			this.floor = BlockFactory.create(type, data);
		} else {
			this.floor = this.fill;
		}
		
		JsonObject stair = json.get("stair").getAsJsonObject();
		type = stair.get("type").getAsString();
		if(!type.equals(BlockFactory.METABLOCK.name()));
		data = stair.get("data").getAsJsonObject();
		this.stair = new MetaBlock(data);
		
		JsonObject pillar = json.get("pillar").getAsJsonObject();
		type = pillar.get("type").getAsString();
		data = pillar.get("data").getAsJsonObject();
		this.pillar = BlockFactory.create(type, data);	
	}
	
	@Override
	public IBlockFactory getFill() {
		return fill;
	}

	@Override
	public MetaBlock getStair() {
		return new MetaBlock(stair);
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
