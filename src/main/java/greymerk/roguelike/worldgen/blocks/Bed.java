package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.block.BlockBed;
import net.minecraft.init.Blocks;

public class Bed {

	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		
		Coord cursor = new Coord(pos);

		MetaBlock head = new MetaBlock(Blocks.BED);
		head.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD);
		head.withProperty(BlockBed.FACING, Cardinal.facing(dir));
		head.set(editor, cursor);
		
		MetaBlock foot = new MetaBlock(Blocks.BED);
		foot.withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
		foot.withProperty(BlockBed.FACING, Cardinal.facing(dir));
		cursor.add(dir);
		foot.set(editor, cursor);
	}
}
