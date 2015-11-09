package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.block.BlockSandStone;
import net.minecraft.init.Blocks;

public class ThemeSandstone extends ThemeBase{

	public ThemeSandstone(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.sandstone), 100);
		walls.addBlock(new MetaBlock(Blocks.sand), 5);
		
		MetaStair stair = new MetaStair(StairType.SANDSTONE);
		
		MetaBlock pillar = new MetaBlock(Blocks.sandstone);
		pillar.withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.SMOOTH);
		
		this.primary = new BlockSet(walls, stair, pillar);
		
		MetaBlock segmentWall = new MetaBlock(Blocks.sandstone);
		segmentWall.withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.CHISELED);
		
		this.secondary =  new BlockSet(segmentWall, stair, pillar);

	}
}
