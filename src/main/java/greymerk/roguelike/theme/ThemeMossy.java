package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeMossy extends ThemeBase{

	public ThemeMossy(){
	
		MetaBlock mossBrick = new MetaBlock(Blocks.stonebrick);
		mossBrick.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
		MetaBlock mossy = new MetaBlock(Blocks.mossy_cobblestone);
		MetaBlock cobble = new MetaBlock(Blocks.cobblestone);
		MetaBlock egg = new MetaBlock(Blocks.monster_egg);
		MetaBlock gravel = new MetaBlock(Blocks.gravel);
		
		egg.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.COBBLESTONE);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(cobble, 60);
		walls.addBlock(mossBrick, 30);
		walls.addBlock(egg, 5);
		walls.addBlock(mossy, 10);
		walls.addBlock(gravel, 15);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.addBlock(mossBrick, 20);
		pillar.addBlock(cobble, 5);
		pillar.addBlock(egg, 3);
		pillar.addBlock(mossy, 5);
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(mossy, 10);
		floor.addBlock(mossBrick, 4);
		floor.addBlock(cobble, 2);
		floor.addBlock(gravel, 1);
		
		MetaStair stair = new MetaStair(StairType.COBBLE);
		
		this.primary = new BlockSet(floor, walls, stair, walls);
		this.secondary = this.primary;
	}
}
