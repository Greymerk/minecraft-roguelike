package greymerk.roguelike.catacomb.dungeon.room;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.loot.Firework;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.redstone.Comparator;
import greymerk.roguelike.worldgen.redstone.Dispenser;
import greymerk.roguelike.worldgen.redstone.Dropper;
import greymerk.roguelike.worldgen.redstone.Repeater;
import greymerk.roguelike.worldgen.redstone.Torch;

public class DungeonFirework implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, ITheme theme, Cardinal[] entrances, int x, int y, int z){
		
		MetaBlock breadboard = new MetaBlock(Blocks.planks);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal dir = entrances[0];
		start = new Coord(x, y, z);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 9);
		end.add(dir, 9);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(orth[0], 4);
		end.add(orth[1], 4);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 3);
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, new MetaBlock(Blocks.cobblestone), false, true);
		
		start = new Coord(x, y, z);
		start.add(orth[0], 2);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 3);
		end.add(dir, 7);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, breadboard, true, true);
		
		start.add(orth[1], 2);
		end.add(orth[1], 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, breadboard, true, true);
		
		start.add(orth[1], 2);
		end.add(orth[1], 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, breadboard, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(orth[0], 2);
		
		launcher(world, rand, dir, cursor);
		cursor.add(orth[1], 2);
		launcher(world, rand, dir, cursor);
		cursor.add(orth[1], 2);
		launcher(world, rand, dir, cursor);
		cursor.add(dir, 6);
		launcher(world, rand, dir, cursor);
		cursor.add(orth[0], 2);
		launcher(world, rand, dir, cursor);
		cursor.add(orth[0], 2);
		launcher(world, rand, dir, cursor);
		
		start = new Coord(x, y, z);
		start.add(dir, 4);
		end = new Coord(start);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		end.add(dir, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.air), true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		Repeater.generate(world, rand, dir, 0, cursor);
		cursor.add(orth[0], 2);
		Repeater.generate(world, rand, dir, 0, cursor);
		cursor.add(orth[1], 4);
		Repeater.generate(world, rand, dir, 0, cursor);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(orth[0]);
		Repeater.generate(world, rand, orth[0], 0, cursor);
		cursor.add(orth[1], 2);
		Repeater.generate(world, rand, orth[1], 0, cursor);
		
		MetaBlock wire = new MetaBlock(Blocks.redstone_wire);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.DOWN, 2);
		start.add(orth[1]);
		start.add(Cardinal.reverse(dir), 2);
		end = new Coord(start);
		end.add(orth[0], 5);
		end.add(Cardinal.reverse(dir), 5);
		end.add(Cardinal.DOWN, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.cobblestone), true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(Cardinal.DOWN);
		Torch.generate(world, Torch.REDSTONE, Cardinal.UP, cursor);
		cursor.add(Cardinal.DOWN);
		breadboard.setBlock(world, cursor);
		cursor.add(orth[0]);
		Torch.generate(world, Torch.REDSTONE, orth[0], cursor);
		cursor.add(orth[0]);
		wire.setBlock(world, cursor);
		cursor.add(Cardinal.reverse(dir));
		wire.setBlock(world, cursor);
		cursor.add(Cardinal.reverse(dir));
		wire.setBlock(world, cursor);
		cursor.add(orth[1]);
		wire.setBlock(world, cursor);
		cursor.add(orth[1]);
		wire.setBlock(world, cursor);
		cursor.add(dir);
		Repeater.generate(world, rand, Cardinal.reverse(dir), 4, cursor);
		cursor.add(Cardinal.UP);
		cursor.add(Cardinal.reverse(dir));
		breadboard.setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		MetaBlock lever = new MetaBlock(Blocks.lever, 5 + 8);
		lever.setBlock(world, cursor);
		
		return false;
	}


	private void launcher(World world, Random rand, Cardinal dir, Coord pos){
		Coord cursor = new Coord(pos);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.redstone_wire);
		cursor.add(Cardinal.reverse(dir));
		WorldGenPrimitive.setBlock(world, cursor, Blocks.redstone_wire);
		cursor.add(Cardinal.reverse(dir));
		Repeater.generate(world, rand, dir, 0, cursor);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP);
		
		Dropper dropper = new Dropper();
		dropper.generate(world, Cardinal.UP, cursor);
		for(int i = 0;i < 8; ++i){
			dropper.add(world, cursor, i, new ItemStack(Items.dye, 1, i));
		}
		dropper.add(world, cursor, 8, new ItemStack(Items.wooden_hoe));
		
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.hopper);
		cursor.add(dir);
		Comparator.generate(world, rand, dir, false, cursor);
		cursor.add(dir);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.redstone_wire);
		cursor.add(dir);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.redstone_wire);
		cursor.add(dir);
		
		Coord top = new Coord(pos.getX(), 80, pos.getZ());
		while(top.getY() > pos.getY()){
			top.add(Cardinal.DOWN);
			if(world.getBlock(top.getX(), top.getY(), top.getZ()).getMaterial().isSolid()) break;
		}
		
		if(top.getY() >= 100) return;
		
		Coord start = new Coord(cursor);
		start.add(Cardinal.UP);
		

		start.add(dir);
		Coord end = new Coord(start);
		
		MetaBlock breadboard = new MetaBlock(Blocks.planks);
		MetaBlock red = new MetaBlock(Blocks.redstone_torch, 5);
		
		boolean torch = false;
		while(end.getY() < top.getY()){
			if(torch){
				red.setBlock(world, cursor);
			} else {
				breadboard.setBlock(world, cursor);
			}
			torch = !torch;
			cursor.add(Cardinal.UP);
			end.add(Cardinal.UP);
		}
		
		if(torch){
			cursor.add(Cardinal.DOWN);
		}
		
		Dispenser d = new Dispenser();
		d.generate(world, Cardinal.UP, cursor);
		for(int i = 0; i < 9; i++){
			d.add(world, cursor, i, Firework.get(rand, 16 + rand.nextInt(16)));
		}
		
		cursor.add(Cardinal.UP);
		MetaBlock cob = new MetaBlock(Blocks.cobblestone);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, cob, true, true);
		start.add(Cardinal.reverse(dir), 2);
		end.add(Cardinal.reverse(dir), 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, cob, true, true);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(dir);
		end.add(dir);
		Coord above = new Coord(end);
		above.add(Cardinal.UP, 10);
		List<Coord> tubeEnd = WorldGenPrimitive.getRectSolid(cursor, above);
		MetaBlock air = new MetaBlock(Blocks.air);
		for(Coord c : tubeEnd){
			if(world.getBlock(c.getX(), c.getY(), c.getZ()).getMaterial().isSolid()){
				air.setBlock(world, c);
			}
		}
		start.add(orth[0]);
		end.add(orth[0]);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, cob, true, true);
		start.add(orth[1], 2);
		end.add(orth[1], 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, cob, true, true);
	}
	
	
	@Override
	public int getSize() {
		return 10;
	}

	@Override
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z) {
		Coord start;
		Coord end;
		
		start = new Coord(x, y, z);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 9);
		end.add(dir, 9);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(orth[0], 5);
		end.add(orth[1], 5);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 3);
		
		for(Coord c : WorldGenPrimitive.getRectHollow(start, end)){
			if(world.isAirBlock(c.getX(), c.getY(), c.getZ())) return false;
		}
		
		return true;
	}

}
