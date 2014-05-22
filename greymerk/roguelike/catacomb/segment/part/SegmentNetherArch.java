package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.segment.alcove.PrisonCell;
import greymerk.roguelike.worldgen.BlockFactoryProvider;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentNetherArch extends SegmentBase {

	@Override
	protected void genWall(Cardinal dir) {
		
		MetaBlock step = new MetaBlock(Block.stairsNetherBrick.blockID);
		step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), true));
		
		Coord start;
		Coord end;
		Coord cursor;
		
		boolean hasLava = rand.nextInt(5) == 0;
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(orth, 1);
			cursor.add(Cardinal.UP, 2);
			WorldGenPrimitive.setBlock(world, cursor, step, true, true);			
		}
			
		MetaBlock fence = new MetaBlock(Block.netherFence.blockID);
		MetaBlock lava = new MetaBlock(Block.lavaMoving.blockID);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);		
		WorldGenPrimitive.setBlock(world, cursor, fence, true, true);
		cursor.add(Cardinal.UP, 1);		
		WorldGenPrimitive.setBlock(world, cursor, fence, true, true);
		
		if(hasLava){
			cursor.add(dir, 1);
			WorldGenPrimitive.setBlock(world, cursor, lava, true, true);
			cursor.add(Cardinal.DOWN, 1);		
			WorldGenPrimitive.setBlock(world, cursor, lava, true, true);
		}
	}
}
