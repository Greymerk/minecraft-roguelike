package greymerk.roguelike.worldgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public enum BlockFactory {
	
	METABLOCK, WEIGHTED, CHECKERS, JUMBLE, STRIPES;

	public static IBlockFactory create(String type, JsonElement blockJson) throws Exception {
		switch(BlockFactory.valueOf(type)){
		case METABLOCK: return new MetaBlock(blockJson);
		case WEIGHTED: return new BlockWeightedRandom(blockJson);
		case CHECKERS: return new BlockFactoryCheckers(blockJson);
		case JUMBLE: return new BlockJumble(blockJson);
		case STRIPES: return new BlockStripes(blockJson);
		default: return null;
		}
	}

}
