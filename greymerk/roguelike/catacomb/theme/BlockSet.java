package greymerk.roguelike.catacomb.theme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.worldgen.BlockFactory;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public class BlockSet implements IBlockSet {

	private IBlockFactory fill;
	private MetaBlock stair;
	private IBlockFactory pillar;
	
	public BlockSet(IBlockFactory fill, MetaBlock stair, IBlockFactory pillar){
		this.fill = fill;		
		this.stair = stair;
		this.pillar = pillar;
	}
	
	public BlockSet(JsonObject json) throws Exception{
		
		JsonObject walls = json.get("walls").getAsJsonObject();
		String type = walls.get("type").getAsString();
		JsonElement data = walls.get("data");
		this.fill = BlockFactory.create(type, data);
				
		JsonObject stair = json.get("stair").getAsJsonObject();
		type = stair.get("type").getAsString();
		if(!type.equals(BlockFactory.METABLOCK.name())) throw new Exception("BlockSet stair must be a METABLOCK");
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
}
