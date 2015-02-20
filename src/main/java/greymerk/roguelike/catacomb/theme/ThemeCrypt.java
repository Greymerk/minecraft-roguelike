package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeCrypt extends ThemeBase{

	public ThemeCrypt(){
	
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		MetaBlock mossy = new MetaBlock(Blocks.stonebrick);
		mossy.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
		BlockJumble stone = new BlockJumble();
		stone.addBlock(new MetaBlock(Blocks.stonebrick));
		stone.addBlock(cracked);
		stone.addBlock(mossy);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(stone, 100);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 10);
		walls.addBlock(new MetaBlock(Blocks.gravel), 5);
		
		MetaBlock p = new MetaBlock(Blocks.stone.getStateFromMeta(5));
		MetaBlock p2 = new MetaBlock(Blocks.stone.getStateFromMeta(6));
		BlockFactoryCheckers pillar = new BlockFactoryCheckers(p, p2);

		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;

	}
}
