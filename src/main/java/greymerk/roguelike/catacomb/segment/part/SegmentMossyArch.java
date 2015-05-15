package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentMossyArch extends SegmentBase {

	private boolean spawnHoleSet = false;
	
	@Override
	protected void genWall(World world, Random rand, CatacombLevel level, Cardinal wallDirection, ITheme theme, int x, int y, int z) {
		
		MetaBlock stair = theme.getSecondaryStair(); 
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(wallDirection), true);
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		level.getSettings().getSecrets().genRoom(world, rand, level.getSettings(), wallDirection, new Coord(x, y, z));
		
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
		WorldGenPrimitive.setBlock(world, cursor, Blocks.flowing_water);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		cursor.add(wallDirection, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, new MetaBlock(Blocks.vine), true, true);
		
		if(!spawnHoleSet){
			WorldGenPrimitive.fillRectSolid(world, rand, x, y + 2, z, x, y + 5, z, new MetaBlock(Blocks.air));
			WorldGenPrimitive.randomVines(world, rand, x, y + 3, z, x, y + 5, z);
			
			if(!world.isAirBlock(new Coord(x, y + 6, z))) WorldGenPrimitive.setBlock(world, x, y + 7, z, Blocks.flowing_water);
			spawnHoleSet = true;
		}
	}

}
