package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.init.Blocks;

public class ThemeChecker extends ThemeBase{

	public ThemeChecker(){
	
		MetaBlock one = new MetaBlock(Blocks.obsidian);
		MetaBlock two = new MetaBlock(Blocks.quartz_block);
		
		IBlockFactory checks = new BlockFactoryCheckers(one, two);
		
		MetaStair stair = new MetaStair(StairType.QUARTZ);
		
		this.primary = new BlockSet(checks, stair, checks);
		
		this.secondary = primary;

	}
}
