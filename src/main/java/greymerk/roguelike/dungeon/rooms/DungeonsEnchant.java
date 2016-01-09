package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;

public class DungeonsEnchant extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		Cardinal dir = entrances[0];
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock panel = ColorBlock.get(ColorBlock.CLAY, DyeColor.PURPLE);
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getPrimaryStair();
		
		List<Coord> chests = new ArrayList<Coord>();
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		start.add(dir, 5);
		end = new Coord(start);
		start.add(-2, 0, -2);
		end.add(2, 3, 2);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.reverse(dir), 2);
		start.add(orth[0], 4);
		end.add(dir, 2);
		end.add(orth[1], 4);
		end.add(Cardinal.UP, 3);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.reverse(dir), 2);
		end = new Coord(start);
		end.add(Cardinal.reverse(dir));
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		end.add(Cardinal.UP, 3);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		end.add(dir, 3);
		start.add(orth[0], 3);
		end.add(orth[1], 3);
		wall.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0], 4);
		end.add(orth[1], 4);
		start.add(Cardinal.reverse(dir), 2);
		end.add(dir, 2);
		theme.getPrimaryFloor().fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.reverse(dir), 4);
		end = new Coord(start);
		end.add(Cardinal.UP, 3);
		start.add(orth[0]);
		end.add(orth[1]);
		wall.fillRectSolid(editor, rand, start, end, false, true);
		
		cursor = new Coord(origin);
		cursor.add(dir, 5);
		for(Cardinal d : Cardinal.directions){
			Cardinal[] side = Cardinal.getOrthogonal(d);
			start = new Coord(cursor);
			start.add(d, 2);
			start.add(side[0], 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
			
			if(d == Cardinal.reverse(dir)) continue;
			
			start = new Coord(cursor);
			start.add(d, 3);
			end = new Coord(start);
			start.add(side[0]);
			end.add(side[1]);
			end.add(Cardinal.UP, 2);
			panel.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(cursor);
			start.add(d, 2);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(side[0]);
			end.add(side[1]);
			stair.setOrientation(Cardinal.reverse(d), true).fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.reverse(d));
			start.add(Cardinal.UP);
			end.add(Cardinal.reverse(d));
			end.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(d), true).fillRectSolid(editor, rand, start, end, true, true);
		}
		
		cursor = new Coord(origin);
		cursor.add(dir, 5);
		cursor.add(Cardinal.UP, 4);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		BlockType.get(BlockType.GLOWSTONE).setBlock(editor, cursor);
		cursor.add(Cardinal.DOWN);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(dir, true).setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		wall.setBlock(editor, rand, cursor);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 5);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		wall.setBlock(editor, rand, cursor);
		
		for(Cardinal d : Cardinal.directions){
			Cardinal[] side = Cardinal.getOrthogonal(d);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 4);
			end = new Coord(start);
			if(d == dir){
				end.add(d);
			} else {
				end.add(d, 2);	
			}
			
			air.fillRectSolid(editor, rand, start, end, true, true);
			
			for(Cardinal o : side){
				Coord s = new Coord(start);
				s.add(d);
				Coord e = new Coord(end);
				s.add(o);
				e.add(o);
				stair.setOrientation(Cardinal.reverse(o), true).fillRectSolid(editor, rand, s, e, true, true);
			}
			
			Coord s = new Coord(start);
			s.add(d);
			Coord e = new Coord(end);
			s.add(Cardinal.UP);
			e.add(Cardinal.UP);
			wall.fillRectSolid(editor, rand, s, e, true, true);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 5);
			cursor.add(d);
			stair.setOrientation(Cardinal.reverse(d), true).setBlock(editor, cursor);
			cursor.add(side[0]);
			wall.setBlock(editor, rand, cursor);
			
		}
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(dir, 3);
		end.add(dir, 7);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		panel.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal o : orth){
			start = new Coord(origin);
			start.add(Cardinal.reverse(dir), 3);
			start.add(o, 3);
			end = new Coord(start);
			end.add(Cardinal.reverse(dir));
			end.add(Cardinal.UP, 3);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(o, 3);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			wall.fillRectSolid(editor, rand, start, end, true, true);
			cursor = new Coord(end);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(o));
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(o));
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
			cursor.add(dir);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
			
			Cardinal[] side = Cardinal.getOrthogonal(o);
		
			start = new Coord(origin);
			start.add(o, 4);
			end = new Coord(start);
			start.add(side[0]);
			end.add(side[1]);
			stair.setOrientation(Cardinal.reverse(o), true).fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.UP, 3);
			end.add(Cardinal.UP, 3);
			stair.setOrientation(Cardinal.reverse(o), true).fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.reverse(o));
			start.add(Cardinal.UP);
			end.add(Cardinal.reverse(o));
			end.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), true).fillRectSolid(editor, rand, start, end, true, true);
			
			for(Cardinal r : side){
				start = new Coord(origin);
				start.add(o, 4);
				start.add(r, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				pillar.fillRectSolid(editor, rand, start, end, true, true);
			}
			
			start = new Coord(origin);
			start.add(o, 5);
			end = new Coord(start);
			start.add(side[0]);
			end.add(side[1]);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP, 2);
			panel.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(Cardinal.reverse(dir), 3);
			start.add(o, 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
			cursor = new Coord(end);
			cursor.add(Cardinal.reverse(o));
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
			cursor.add(dir);
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
			cursor.add(o);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
			cursor.add(o);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.reverse(dir), 2);
			cursor.add(o);
			cursor.add(Cardinal.UP, 4);
			wall.setBlock(editor, rand, cursor);
			cursor.add(dir);
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
			
			start = new Coord(origin);
			start.add(Cardinal.UP);
			start.add(o, 4);
			end = new Coord(start);
			start.add(side[0]);
			end.add(side[1]);
			chests.addAll(editor.getRectSolid(start, end));
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 2);
		cursor.add(Cardinal.UP, 4);
		stair.setOrientation(dir, true).setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		wall.setBlock(editor, rand, cursor);
		
		cursor = new Coord(origin);
		cursor.add(dir, 5);
		BlockType.get(BlockType.ENCHANTING_TABLE).setBlock(editor, cursor);
		
		Treasure.generate(editor, rand, chests, Treasure.ENCHANTING, settings.getDifficulty(origin));
		
		return true;
	}	
	
	@Override
	public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos) {
		Coord start;
		Coord end;
		
		start = new Coord(pos);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 4);
		end.add(dir, 8);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(orth[0], 5);
		end.add(orth[1], 5);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 2);
		
		for(Coord c : editor.getRectHollow(start, end)){
			if(editor.isAirBlock(c)) return false;
		}
		
		return true;
	}
	
	public int getSize(){
		return 6;
	}
}
