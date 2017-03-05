package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.block.BlockBed;
import net.minecraft.init.Blocks;

public class Bed {

	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		
		Coord cursor = new Coord(pos);

		
		if(RogueConfig.getBoolean(RogueConfig.FURNITURE)){
			MetaBlock head = new MetaBlock(Blocks.BED);
			head.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD);
			head.withProperty(BlockBed.FACING, Cardinal.facing(dir));
			head.set(editor, cursor);
		} else {
			ColorBlock.get(ColorBlock.WOOL, DyeColor.WHITE).set(editor, cursor);
		}
		
		cursor.add(dir);
		if(RogueConfig.getBoolean(RogueConfig.FURNITURE)){
			MetaBlock foot = new MetaBlock(Blocks.BED);
			foot.withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
			foot.withProperty(BlockBed.FACING, Cardinal.facing(dir));
			foot.set(editor, cursor);
		} else {
			ColorBlock.get(ColorBlock.WOOL, DyeColor.RED).set(editor, cursor);
		}
	}
}
