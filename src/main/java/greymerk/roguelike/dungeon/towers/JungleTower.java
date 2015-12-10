package greymerk.roguelike.dungeon.towers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Vine;
import greymerk.roguelike.worldgen.blocks.Wood;


public class JungleTower implements ITower{

	@Override
	public void generate(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {
		
		Coord origin = Tower.getBaseCoord(editor, x, y, z);
		origin.add(Cardinal.UP);
		IBlockFactory pillar = theme.getPrimaryPillar();
		IBlockFactory walls = theme.getPrimaryWall();
		IStair stair = theme.getPrimaryStair();
		MetaBlock grass = BlockType.get(BlockType.GRASS);
		
		Coord start;
		Coord end;
		Coord cursor;
		
		// lower pillars
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 3);
			cursor.add(dir, 7);
			pillar(editor, rand, theme, cursor);
			
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o, 3);
				pillar(editor, rand, theme, c);
				c.add(dir);
				c.add(Cardinal.UP);
				walls.setBlock(editor, rand, c);
				c.add(Cardinal.UP);
				walls.setBlock(editor, rand, c);
				c.add(Cardinal.UP);
				stair.setOrientation(dir, false).setBlock(editor, c);
				c.add(Cardinal.reverse(dir));
				walls.setBlock(editor, rand, c);
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir, 8);
			walls.setBlock(editor, rand, cursor);
			cursor.add(Cardinal.UP);
			walls.setBlock(editor, rand, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(dir, false).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(dir));
			walls.setBlock(editor, rand, cursor);
			
			start = new Coord(origin);
			start.add(dir, 2);
			start.add(orth[0], 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
			cursor = new Coord(end);
			for(Cardinal d : new Cardinal[]{Cardinal.reverse(dir), orth[1]}){
				Coord c = new Coord(cursor);
				c.add(d);
				stair.setOrientation(d, true).setBlock(editor, c);
			}
			
			// corner pillar
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 6);
			cursor.add(dir, 6);
			cursor.add(orth[0], 6);
			editor.fillDown(rand, new Coord(cursor), pillar);
			for(Cardinal d : new Cardinal[]{dir, orth[0]}){
				start = new Coord(cursor);
				start.add(d);
				stair.setOrientation(d, false).setBlock(editor, start);
				start.add(Cardinal.DOWN);
				end = new Coord(start);
				end.add(Cardinal.DOWN, 2);
				walls.fillRectSolid(editor, rand, start, end, true, true);
				end.add(Cardinal.DOWN);
				stair.setOrientation(d, true).setBlock(editor, end);
			}
		}
		
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir, 7);
			start = new Coord(cursor);
			end = new Coord(cursor);
			start.add(orth[0], 5);
			end.add(orth[1], 5);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			grass.fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.reverse(dir));
			end.add(Cardinal.reverse(dir));
			walls.fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.reverse(dir));
			end.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir, 6);
			pillar(editor, rand, theme, cursor);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o, 3);
				pillar(editor, rand, theme, c);
			}
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 5);
			start.add(dir, 2);
			end = new Coord(start);
			end.add(dir, 3);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			end.add(orth[0], 3);
			start = new Coord(end);
			start.add(Cardinal.reverse(dir), 10);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 6);
			start.add(dir, 3);
			start.add(orth[0], 2);
			end = new Coord(start);
			end.add(orth[1], 8);
			end.add(dir, 3);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 4);
			start.add(dir, 2);
			end = new Coord(start);
			start.add(orth[0], 2);
			end.add(orth[1]);
			end.add(Cardinal.UP, 2);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 4);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 2);
			stair.setOrientation(dir, true).fillRectSolid(editor, rand, start, end, true, true);
		}
		
		// level 2 grass patches
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			for(Cardinal o : orth){
				start = new Coord(origin);
				start.add(Cardinal.UP, 6);
				start.add(dir, 5);
				start.add(o);
				end = new Coord(start);
				end.add(o);
				end.add(dir);
				grass.fillRectSolid(editor, rand, start, end, true, true);
				start.add(o, 3);
				end.add(o, 3);
				grass.fillRectSolid(editor, rand, start, end, true, true);
			}
		}

		// second floor pillars
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 9);
			cursor.add(dir, 5);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o, 2);
				pillar(editor, rand, theme, c);
				c.add(dir);
				c.add(Cardinal.UP);
				walls.setBlock(editor, rand, c);
				c.add(Cardinal.UP);
				stair.setOrientation(dir, false).setBlock(editor, c);
				c.add(Cardinal.reverse(dir));
				walls.setBlock(editor, rand, c);
				c.add(Cardinal.UP);
				stair.setOrientation(dir, false).setBlock(editor, c);
			}
			cursor.add(orth[0], 5);
			pillar(editor, rand, theme, cursor);
			for(Cardinal d : new Cardinal[]{orth[0], dir}){
				Coord c = new Coord(cursor);
				c.add(d);
				c.add(Cardinal.UP);
				walls.setBlock(editor, rand, c);
				c.add(Cardinal.UP);
				stair.setOrientation(d, false).setBlock(editor, c);
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 10);
			cursor.add(dir, 2);
			cursor.add(orth[0], 2);
			start = new Coord(cursor);
			end = new Coord(start);
			end.add(Cardinal.DOWN, 3);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
			for(Cardinal d : new Cardinal[]{orth[1], Cardinal.reverse(dir)}){
				Coord c = new Coord(cursor);
				c.add(d);
				stair.setOrientation(d, true).setBlock(editor, c);
			}
			
			cursor.add(Cardinal.DOWN);
			for(Cardinal d : new Cardinal[]{orth[0], dir}){
				Coord c = new Coord(cursor);
				c.add(d);
				stair.setOrientation(d, true).setBlock(editor, c);
				c.add(Cardinal.UP);
				walls.setBlock(editor, rand, c);
				c.add(d);
				walls.setBlock(editor, rand, c);
			}
			
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(Cardinal.UP, 10);
			start.add(dir, 5);
			end = new Coord(start);
			start.add(orth[0], 5);
			end.add(orth[1], 4);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 11);
			start.add(dir, 2);
			end = new Coord(start);
			start.add(orth[0]);
			end.add(orth[1], 4);
			end.add(dir, 2);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 11);
			cursor.add(dir, 5);
			start = new Coord(cursor);
			end = new Coord(start);
			start.add(orth[0], 4);
			end.add(orth[1], 4);
			grass.fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 12);
			cursor.add(dir, 3);
			start = new Coord(cursor);
			end = new Coord(start);
			end.add(dir);
			start.add(orth[0]);
			end.add(orth[1], 4);
			grass.fillRectSolid(editor, rand, start, end, true, true);
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 12);
			cursor.add(dir, 2);
			start = new Coord(cursor);
			end = new Coord(cursor);
			start.add(orth[0], 4);
			end.add(orth[1], 4);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 11);
			cursor.add(dir, 5);
			cursor.add(orth[0], 5);
			walls.setBlock(editor, rand, cursor);
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(orth[0], 2);
			cursor.add(Cardinal.UP, 15);
			pillar(editor, rand, theme, cursor);
			for(Cardinal d : new Cardinal[]{dir, orth[0]}){
				Coord c = new Coord(cursor);
				c.add(d);
				c.add(Cardinal.UP);
				walls.setBlock(editor, rand, c);
				c.add(Cardinal.UP);
				stair.setOrientation(d, false).setBlock(editor, c);
			}
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 16);
			start.add(dir, 2);
			end = new Coord(start);
			start.add(orth[0]);
			end.add(orth[1], 2);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 17);
			cursor.add(dir, 2);
			start = new Coord(cursor);
			end = new Coord(cursor);
			start.add(orth[0]);
			end.add(orth[1]);
			grass.fillRectSolid(editor, rand, start, end, true, true);
			cursor.add(orth[0], 2);
			walls.setBlock(editor, rand, cursor);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 17);
			end = new Coord(start);
			start.add(dir);
			start.add(orth[0]);
			end.add(Cardinal.reverse(dir));
			end.add(orth[1]);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			grass.fillRectSolid(editor, rand, start, end, true, true);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.EAST, 2);
		end = new Coord(origin.getX(), y + 10, origin.getZ());
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.WEST, 2);
		walls.fillRectSolid(editor, rand, start, end, false, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 12);
		start = new Coord(cursor.getX(), y, cursor.getZ());
		end = new Coord(cursor);
		for(Coord c : WorldEditor.getRectSolid(start, end)){
			editor.spiralStairStep(rand, c, stair, pillar);
		}
		
		decorate(editor, rand, theme, origin);
	}
	
	private void decorate(WorldEditor editor, Random rand, ITheme theme, Coord origin){
		List<Coord> spots = new ArrayList<Coord>();
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			Coord cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 6);
			cursor.add(dir, 7);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				spots.add(new Coord(c));
				c.add(o);
				spots.add(new Coord(c));
				c.add(o, 2);
				spots.add(new Coord(c));
				c.add(o);
				spots.add(new Coord(c));
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 12);
			cursor.add(dir, 5);
			spots.add(new Coord(cursor));
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				spots.add(new Coord(c));
				c.add(o, 2);
				spots.add(new Coord(c));
				c.add(o);
				spots.add(new Coord(c));
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 13);
			cursor.add(dir, 4);
			spots.add(new Coord(cursor));
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				spots.add(new Coord(c));
				c.add(o, 2);
				spots.add(new Coord(c));
				c.add(o);
				spots.add(new Coord(c));
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 18);
			cursor.add(dir, 2);
			spots.add(new Coord(cursor));
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				spots.add(new Coord(c));
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 19);
			Coord start = new Coord(cursor);
			Coord end = new Coord(cursor);
			start.add(dir);
			end.add(Cardinal.reverse(dir));
			start.add(orth[0]);
			end.add(orth[1]);
			spots.addAll(WorldEditor.getRectSolid(start, end));
		}
		
		for(Coord c : spots){
			if(rand.nextBoolean()){
				tree(editor, rand, theme, c);
			}
		}
		
		Coord start = new Coord(origin);
		Coord end = new Coord(origin);
		end.add(Cardinal.UP, 20);
		start.add(Cardinal.NORTH, 8);
		start.add(Cardinal.EAST, 8);
		end.add(Cardinal.SOUTH, 8);
		end.add(Cardinal.WEST, 8);
		Vine.fill(editor, rand, start, end);
	}
	
	private void tree(WorldEditor editor, Random rand, ITheme theme, Coord origin){
		
		MetaBlock leaves = Leaves.get(Leaves.JUNGLE, false);
		
		Coord cursor = new Coord(origin);
		Log.getLog(Wood.JUNGLE).setBlock(editor, cursor);
		for(Cardinal dir : Cardinal.directions){
			Coord c = new Coord(cursor);
			c.add(dir);
			leafSpill(editor, rand, theme, c, rand.nextInt(6));
		}
		if(rand.nextBoolean()){
			cursor.add(Cardinal.UP);
			Log.getLog(Wood.JUNGLE).setBlock(editor, cursor);
			for(Cardinal dir : Cardinal.directions){
				Coord c = new Coord(cursor);
				c.add(dir);
				leaves.setBlock(editor, rand, c, true, false);
			}
		}
		if(rand.nextInt(3) == 0){
			cursor.add(Cardinal.UP);
			Log.getLog(Wood.JUNGLE).setBlock(editor, cursor);
			for(Cardinal dir : Cardinal.directions){
				Coord c = new Coord(cursor);
				c.add(dir);
				leaves.setBlock(editor, rand, c, true, false);
			}
		}
		cursor.add(Cardinal.UP);
		leaves.setBlock(editor, cursor);
	}
	
	public void leafSpill(WorldEditor editor, Random rand, ITheme theme, Coord origin, int count){
		if(count < 0) return;
		MetaBlock leaves = Leaves.get(Leaves.JUNGLE, false);
		leaves.setBlock(editor, origin);
		Coord cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		if(!editor.getBlock(cursor).getBlock().getMaterial().isOpaque()){
			leaves.setBlock(editor, origin);
			if(rand.nextBoolean()) leafSpill(editor, rand, theme, cursor, count - 1);
			return;
		}
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			if(editor.getBlock(cursor).getBlock().getMaterial().isOpaque()) continue;
			leaves.setBlock(editor, origin);
			cursor.add(Cardinal.DOWN);
			if(editor.getBlock(cursor).getBlock().getMaterial().isOpaque()) continue;
			leafSpill(editor, rand, theme, cursor, count - 1);
		}
	}
	
	private void pillar(WorldEditor editor, Random rand, ITheme theme, Coord origin){
		
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		Coord cursor;
		
		editor.fillDown(rand, new Coord(origin), pillar);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
		}
	}
	
}
