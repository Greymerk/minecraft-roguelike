package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.init.Blocks;

public class Piston {

	public static void generate(IWorldEditor editor, Coord origin, Cardinal dir, boolean sticky){
		
		MetaBlock piston = new MetaBlock(sticky ? Blocks.sticky_piston : Blocks.piston);
		piston.withProperty(BlockPistonBase.FACING, Cardinal.getFacing(Cardinal.reverse(dir)));
		piston.setBlock(editor, origin);
	}
	
}
