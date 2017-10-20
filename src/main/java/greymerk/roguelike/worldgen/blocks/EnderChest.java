package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.init.Blocks;

public class EnderChest {
	public static void set(IWorldEditor editor, Cardinal dir, Coord pos){
		MetaBlock chest = new MetaBlock(Blocks.ENDER_CHEST);
		chest.withProperty(BlockEnderChest.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		chest.set(editor, pos);
	}
}
