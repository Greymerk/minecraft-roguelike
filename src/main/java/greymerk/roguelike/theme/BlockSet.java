package greymerk.roguelike.theme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.worldgen.BlockProvider;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;

public class BlockSet implements IBlockSet {

	private IBlockFactory floor;
	private IBlockFactory fill;
	private IStair stair;
	private IBlockFactory pillar;
	
	public BlockSet(IBlockFactory floor, IBlockFactory fill, IStair stair, IBlockFactory pillar){
		this.floor = floor;
		this.fill = fill;
		this.stair = stair;
		this.pillar = pillar;
	}
	
	public BlockSet(IBlockFactory fill, IStair stair, IBlockFactory pillar){
		this.floor = fill;
		this.fill = fill;		
		this.stair = stair;
		this.pillar = pillar;
	}
	
	public BlockSet(JsonObject json){
		
		JsonObject walls = json.get("walls").getAsJsonObject();
		String type = walls.get("type").getAsString();
		JsonElement data = walls.get("data");
		this.fill = BlockProvider.create(type, data);

		if(json.has("floor")){
			JsonObject floor = json.get("floor").getAsJsonObject();
			type = floor.get("type").getAsString();
			data = floor.get("data");
			this.floor = BlockProvider.create(type, data);
		} else {
			this.floor = this.fill;
		}
		
		JsonObject stair = json.get("stair").getAsJsonObject();
		type = stair.get("type").getAsString();
		if(!type.equals(BlockProvider.METABLOCK.name()));
		data = stair.get("data").getAsJsonObject();
		this.stair = new MetaStair(new MetaBlock(data));
		
		JsonObject pillar = json.get("pillar").getAsJsonObject();
		type = pillar.get("type").getAsString();
		data = pillar.get("data").getAsJsonObject();
		this.pillar = BlockProvider.create(type, data);	
	}
	
	@Override
	public IBlockFactory getFill() {
		return fill;
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
