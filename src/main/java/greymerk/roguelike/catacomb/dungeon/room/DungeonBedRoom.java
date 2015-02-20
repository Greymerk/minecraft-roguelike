package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.Bed;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.redstone.Torch;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DungeonBedRoom implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		ITheme theme = settings.getTheme();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal dir = entrances[0];
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(x, y, z);
		end = new Coord(x, y, z);
		
		start.add(orth[0], 4);
		end.add(orth[1], 4);
		start.add(Cardinal.reverse(dir), 4);
		end.add(dir, 4);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 4);
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, theme.getPrimaryWall(), false, true);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		start.add(Cardinal.reverse(dir), 2);
		end.add(dir, 2);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), true, true);
		
		for(Cardinal o : orth){
			MetaBlock stair = theme.getSecondaryStair();
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true);
			
			start = new Coord(x, y, z);
			start.add(o, 3);
			end = new Coord(start);
			start.add(Cardinal.getOrthogonal(o)[0], 2);
			end.add(Cardinal.getOrthogonal(o)[1], 2);
			
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, true);
			start.add(Cardinal.UP, 2);
			end.add(Cardinal.UP, 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
			start.add(Cardinal.reverse(o));
			end.add(Cardinal.reverse(o));
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, true);
		}
		
		for(Cardinal o : orth){
			cursor = new Coord(x, y, z);
			cursor.add(o, 3);
			pillar(world, rand, o, theme, cursor);
			for(Cardinal p : Cardinal.getOrthogonal(o)){
				Coord c = new Coord(cursor);
				c.add(p, 3);
				pillar(world, rand, o, theme, c);
			}
		}
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		cursor.add(Cardinal.reverse(dir), 3);
		
		for(int i = 0; i < 3; ++i){
			start = new Coord(cursor);
			end = new Coord(cursor);
			start.add(orth[0], 2);
			end.add(orth[1], 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), true, true);
			cursor.add(dir, 3);
		}
		
		Cardinal side = orth[rand.nextInt(orth.length)];
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		cursor.add(side, 1);
		Bed.generate(world, Cardinal.reverse(dir), cursor);
		cursor.add(side);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.bookshelf);
		cursor.add(Cardinal.UP);
		FlowerPot.generate(world, rand, cursor);
		cursor.add(Cardinal.reverse(side), 3);
		cursor.add(Cardinal.DOWN);
		MetaBlock stair = theme.getSecondaryStair();
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
		stair.setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		Torch.generate(world, Torch.WOODEN, Cardinal.UP, cursor);
		
		side = orth[rand.nextInt(orth.length)];
		cursor = new Coord(x, y, z);
		cursor.add(dir);
		cursor.add(side, 3);
		TreasureChest.generate(world, rand, settings, cursor, TreasureChest.STARTER);
		cursor.add(Cardinal.reverse(side), 6);
		if(rand.nextBoolean()){
			cursor.add(Cardinal.UP);
			Torch.generate(world, Torch.WOODEN, Cardinal.UP, cursor);
			cursor.add(Cardinal.DOWN);
			cursor.add(dir);
			WorldGenPrimitive.setBlock(world, cursor, Blocks.crafting_table);
		} else {
			WorldGenPrimitive.setBlock(world, cursor, Blocks.crafting_table);
			cursor.add(dir);
			cursor.add(Cardinal.UP);
			Torch.generate(world, Torch.WOODEN, Cardinal.UP, cursor);
			cursor.add(Cardinal.DOWN);
		}
		
		side = orth[rand.nextInt(orth.length)];
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(side, 3);
		if(rand.nextBoolean()) cursor.add(Cardinal.reverse(dir));
		Furnace.generate(world, new ItemStack(Items.coal, 2 + rand.nextInt(3)), true, Cardinal.reverse(side), cursor);
		
		
		return true;
	}
	
	public static void pillar(World world, Random rand, Cardinal dir, ITheme theme, final Coord base){
		Coord start = new Coord(base);
		Coord end = new Coord(base);
		
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryPillar(), true, true);
		MetaBlock stair = theme.getSecondaryStair();
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
		end.add(Cardinal.reverse(dir));
		stair.setBlock(world, end);
	}

	@Override
	public int getSize() {
		
		return 5;
	}

	@Override
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z) {
		Coord start;
		Coord end;
		
		start = new Coord(x, y, z);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 5);
		end.add(dir, 5);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(orth[0], 5);
		end.add(orth[1], 5);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 3);
		
		for(Coord c : WorldGenPrimitive.getRectHollow(start, end)){
			if(WorldGenPrimitive.isAirBlock(world, c)) return false;
		}
		
		return true;
	}
}
