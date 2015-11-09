package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class JungleTower implements ITower{

	@Override
	public void generate(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {
		
		Coord origin = Tower.getBaseCoord(editor, x, y, z);
		Coord cursor;
		
		base(editor, rand, theme, origin);
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		top(editor, rand, theme, cursor);
	}
	
	private void base(WorldEditor editor, Random rand, ITheme theme, Coord origin){
		
		Coord start;
		Coord end;
		MetaBlock air = new MetaBlock(Blocks.air);

		
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.NORTH, 10);
		start.add(Cardinal.WEST, 10);
		end.add(Cardinal.SOUTH, 10);
		end.add(Cardinal.EAST, 10);
		end.add(Cardinal.UP, 3);
		
		start = new Coord(start.getX(), 60, start.getZ());
		
		editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
		
		IStair stair = theme.getPrimaryStair();
		
		BlockStripes nsStairs = new BlockStripes();
		nsStairs.addBlock(new MetaBlock(stair.setOrientation(Cardinal.NORTH, false)));
		nsStairs.addBlock(new MetaBlock(stair.setOrientation(Cardinal.SOUTH, false)));

		
		BlockStripes ewStairs = new BlockStripes();
		ewStairs.addBlock(new MetaBlock(stair.setOrientation(Cardinal.EAST, false)));
		ewStairs.addBlock(new MetaBlock(stair.setOrientation(Cardinal.WEST, false)));
		
		for (Cardinal dir : Cardinal.directions){
			
			IBlockFactory stairs;
			
			if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
				stairs = ewStairs;
			} else {
				stairs = nsStairs;
			}
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			for (Cardinal o : orth){
				start = new Coord(origin);
				start.add(dir, 8);
				start.add(o, 5);
				end = new Coord(start);
				end.add(dir, 2);
				end.add(o, 3);
				end.add(Cardinal.UP, 2);
				editor.fillRectSolid(rand, start, end, air, true, true);
				start.add(dir);
				end.add(Cardinal.reverse(dir));
				editor.fillRectSolid(rand, start, end, stairs, true, true);

			}
		}
	}
	
	private void top(WorldEditor editor, Random rand, ITheme theme, Coord origin){
		Coord start;
		Coord end;
		Coord cursor;
		
		IBlockFactory blocks = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.NORTH, 6);
		start.add(Cardinal.WEST, 6);
		start.add(Cardinal.DOWN);
		
		end.add(Cardinal.SOUTH, 6);
		end.add(Cardinal.EAST, 6);
		end.add(Cardinal.UP, 3);
		
		editor.fillRectHollow(rand, start, end, theme.getPrimaryWall(), true, true);
		
		for(int i = origin.getY() - 1; i >= 50; --i){
			editor.spiralStairStep(rand, new Coord(origin.getX(), i, origin.getZ()), theme.getPrimaryStair(), theme.getPrimaryPillar());
		}
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(dir, 6);
			end = new Coord(start);
			start.add(orth[0]);
			end.add(orth[1]);
			end.add(Cardinal.UP, 2);
			editor.fillRectSolid(rand, start, end, air, true, true);
			
			for(Cardinal o : orth){
				cursor = new Coord(origin);
				cursor.add(dir, 5);
				cursor.add(o, 2);
				start = new Coord(cursor);
				end = new Coord(cursor);
				end.add(Cardinal.UP, 2);
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				start.add(o, 2);
				end.add(o, 2);
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				
				Coord temp = new Coord(cursor);
				
				cursor.add(o);
				stair.setOrientation(o, false).setBlock(editor, cursor);
				cursor.add(o);
				stair.setOrientation(Cardinal.reverse(o), false).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(o));
				stair.setOrientation(o, true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				blocks.setBlock(editor, rand, cursor);
				cursor.add(o);
				blocks.setBlock(editor, rand, cursor);
				
				cursor = new Coord(temp);
				cursor.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
				cursor.add(o);
				stair.setOrientation(o, false).setBlock(editor, cursor);
				cursor.add(o);
				stair.setOrientation(Cardinal.reverse(o), false).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				pillar.setBlock(editor, rand, cursor);
				cursor.add(Cardinal.reverse(o));
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(o));
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(o));
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
				cursor.add(dir);
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				cursor.add(dir);
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				
				start = new Coord(origin);
				start.add(dir, 6);
				start.add(o, 3);
				end = new Coord(start);
				end.add(o);
				end.add(Cardinal.UP, 2);
				editor.fillRectSolid(rand, start, end, air, true, true);
				
				
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.DOWN);
			cursor.add(dir, 11);
			step(editor, rand, theme, dir, cursor);
		}
	}
	
	private void step(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin){
		
		if(editor.getBlock(origin).getBlock().isOpaqueCube()) return;
		if(origin.getY() <= 60) return;
		
		Coord start;
		Coord end;
		
		IStair stair = theme.getPrimaryStair();
		IBlockFactory blocks = theme.getPrimaryWall();
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		end = new Coord(end.getX(), 60, end.getZ());
		editor.fillRectSolid(rand, start, end, blocks, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0]);
		end.add(orth[1]);
		stair.setOrientation(dir, false);
		editor.fillRectSolid(rand, start, end, stair, true, true);
		
		origin.add(Cardinal.DOWN);
		origin.add(dir);
		step(editor, rand, theme, dir, origin);
	}
}
