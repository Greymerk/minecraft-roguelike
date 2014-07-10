package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Orientation;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentWheat extends SegmentBase {


	@Override
	protected void genWall(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		Coord cursor;
		Coord start;
		Coord end;
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.DOWN);
		cursor.add(dir, 3);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.water);
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		start.add(Cardinal.UP, 2);
		end.add(dir);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), true, true);
		
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 1);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.air), true, true);
		start.add(Cardinal.DOWN, 1);
		end.add(Cardinal.DOWN, 2);

		WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.farmland), true, true);
		start.add(Cardinal.UP, 1);
		end.add(Cardinal.UP, 1);
		BlockJumble crops = new BlockJumble();
		crops.addBlock(new MetaBlock(Blocks.wheat));
		crops.addBlock(new MetaBlock(Blocks.carrots));
		crops.addBlock(new MetaBlock(Blocks.potatoes));
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, crops, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		cursor.add(Cardinal.UP, 1);
		MetaBlock pumpkin = new MetaBlock(Blocks.lit_pumpkin);
		Orientation.set(pumpkin, Cardinal.reverse(dir));
		pumpkin.setBlock(world, cursor);
		
		
		for(Cardinal d : orth){
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			cursor.add(Cardinal.UP, 1);
			MetaBlock stair = theme.getSecondaryStair();
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(d), true));
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		}
	}
}
