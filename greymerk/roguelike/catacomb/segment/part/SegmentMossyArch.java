package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.segment.alcove.PrisonCell;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentMossyArch extends SegmentBase {

	private int stairType;
	private static final int SPRUCE = 1;
	
	private boolean spawnHoleSet = false;
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		MetaBlock stair = theme.getSecondaryStair(); 
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(wallDirection), true));
		
		MetaBlock air = new MetaBlock(0);
		
		Coord cursor = new Coord(x, y, z);
		cursor.add(wallDirection, 2);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(wallDirection)){
			cursor = new Coord(x, y, z);
			cursor.add(orth, 1);
			cursor.add(wallDirection, 2);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
			cursor.add(Cardinal.reverse(wallDirection), 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);			
		}
		
		cursor = new Coord(x, y, z);
		cursor.add(wallDirection, 2);
		cursor.add(Cardinal.DOWN, 1);
		WorldGenPrimitive.setBlock(world, cursor, Block.waterMoving.blockID);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		cursor.add(wallDirection, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, new MetaBlock(Block.vine.blockID, rand.nextInt(15)), true, true);
		
		if(!spawnHoleSet){
			spawnHole();
			spawnHoleSet = true;
		}
	}
	
	private void spawnHole(){
		WorldGenPrimitive.fillRectSolid(world, rand, x, y + 2, z, x, y + 5, z, 0);
		WorldGenPrimitive.randomVines(world, rand, x, y + 3, z, x, y + 5, z);
		
		if(!world.isAirBlock(x, y + 6, z)) WorldGenPrimitive.setBlock(world, x, y + 7, z, Block.waterMoving.blockID);
	}
}
