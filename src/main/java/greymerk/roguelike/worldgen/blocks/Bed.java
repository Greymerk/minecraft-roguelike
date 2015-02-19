package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockBed;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Bed {

	public static void generate(World world, Cardinal dir, Coord pos){
		
		Coord cursor = new Coord(pos);
		
		MetaBlock head = new MetaBlock(Blocks.bed.getDefaultState()
				.withProperty(BlockBed.PART_PROP, BlockBed.EnumPartType.HEAD)
				.withProperty(BlockBed.FACING, Cardinal.getFacing(Cardinal.reverse(dir))));
		
		head.setBlock(world, cursor);
		
		MetaBlock foot = new MetaBlock(Blocks.bed.getDefaultState()
				.withProperty(BlockBed.PART_PROP, BlockBed.EnumPartType.FOOT));
		cursor.add(dir);
		foot.setBlock(world, cursor);
	}
}
