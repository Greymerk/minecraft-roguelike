package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeCrypt extends ThemeBase{

	public ThemeCrypt(){
	
		BlockJumble stone = new BlockJumble();
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK));
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(stone, 100);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 10);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);
		
		MetaBlock andesite = BlockType.get(BlockType.ANDESITE);
		MetaBlock smoothAndesite = BlockType.get(BlockType.ANDESITE_POLISHED);
		BlockFactoryCheckers pillar = new BlockFactoryCheckers(andesite, smoothAndesite);

		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;

	}
}
