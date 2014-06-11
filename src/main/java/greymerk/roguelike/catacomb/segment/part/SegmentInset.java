package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.segment.alcove.SilverfishNest;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import net.minecraft.init.Blocks;

public class SegmentInset extends SegmentBase {

	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getSecondaryStair();
		
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(wallDirection);		
		
		start = new Coord(x, y, z);
		start.add(wallDirection, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		start.add(wallDirection, 1);
		end.add(wallDirection, 1);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
		
		for(Cardinal d : orth){
			cursor = new Coord(x, y, z);
			cursor.add(Cardinal.UP, 2);
			cursor.add(wallDirection, 2);
			cursor.add(d, 1);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(wallDirection), true));
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
			
			cursor = new Coord(x, y, z);
			cursor.add(wallDirection, 2);
			cursor.add(d, 1);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(d), false));
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
			
			
		}
	
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 1);
		cursor.add(wallDirection, 3);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(wallDirection), true));
		WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		
		bonus(x, y, z, wallDirection);
	}
	
	private void bonus(int x, int y, int z, Cardinal dir){
		
		Coord shelf = new Coord(x, y, z);
		shelf.add(dir, 3);
		shelf.add(Cardinal.UP, 1);

		if(world.isAirBlock(shelf.getX(), shelf.getY() - 1, shelf.getZ())) return;		
		
		if(rand.nextBoolean() && Catacomb.getLevel(y) == 0){
			WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY(), shelf.getZ(), Blocks.flower_pot, rand.nextInt(11) + 1, 2, true, true);
			return;
		}
		
		if(rand.nextInt(5) != 0) return;
		
		if(Catacomb.getLevel(y) == 3 && rand.nextBoolean()){
			IAlcove nest = new SilverfishNest();
			if(nest.isValidLocation(world, x, y, z, dir)){
				nest.generate(world, rand, theme, x, y, z, dir);
				return;
			}
		}
		

		
		if(rand.nextBoolean()){
			
			if(Catacomb.getLevel(y) == 1){
				WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY(), shelf.getZ(), Blocks.bookshelf);
				return;	
			}
			
			if(Catacomb.getLevel(y) == 2 || Catacomb.getLevel(y) == 3){
				WorldGenPrimitive.skull(world, rand, shelf.getX(), shelf.getY(), shelf.getZ(), Cardinal.reverse(dir));
				return;
			}
		}
		
		if(rand.nextBoolean()){
			boolean trapped = Catacomb.getLevel(y) == 3 && rand.nextInt(3) == 0;
			TreasureChest.generate(world, rand, shelf.getX(), shelf.getY(), shelf.getZ(), Catacomb.getLevel(y), trapped);
			if(trapped){
				WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY() - 2, shelf.getZ(), Blocks.tnt);
				if(rand.nextBoolean()) WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY() - 3, shelf.getZ(), Blocks.tnt);
			}
		}
		
		if(Catacomb.getLevel(y) > 0){
			Spawner.generate(world, rand, shelf.getX(), shelf.getY(), shelf.getZ());
			return;
		}
	}
}
