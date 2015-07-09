package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeChecker extends ThemeBase{

	public ThemeChecker(){
	
		MetaBlock one = new MetaBlock(Blocks.obsidian);
		MetaBlock two = new MetaBlock(Blocks.quartz_block);
		
		IBlockFactory checks = new BlockFactoryCheckers(one, two);
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		
		this.primary = new BlockSet(checks, stair, checks);
		
		this.secondary = primary;

	}
}
