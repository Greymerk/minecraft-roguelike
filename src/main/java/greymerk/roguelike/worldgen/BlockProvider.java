package greymerk.roguelike.worldgen;

import com.google.gson.JsonElement;

public enum BlockProvider {
	
	METABLOCK, WEIGHTED, CHECKERS, JUMBLE, STRIPES, LAYERS;

	public static IBlockFactory create(String type, JsonElement blockJson) {
		switch(BlockProvider.valueOf(type)){
		case METABLOCK: return new MetaBlock(blockJson);
		case WEIGHTED: return new BlockWeightedRandom(blockJson);
		case CHECKERS: return new BlockCheckers(blockJson);
		case JUMBLE: return new BlockJumble(blockJson);
		case STRIPES: return new BlockStripes(blockJson);
		case LAYERS: return new BlockLayers(blockJson);
		default: return null;
		}
	}
}
