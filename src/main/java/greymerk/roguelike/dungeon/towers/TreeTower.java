package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.Line;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class TreeTower implements ITower{

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord pos) {
		
		Coord ground = Tower.getBaseCoord(editor, pos);
		
		Coord start = new Coord(ground);
		start.add(new Coord(-4, -1, -4));
		Coord end = new Coord(ground);
		end.add(new Coord(4, 10, 4));
		
		//Log.getLog(Wood.OAK).fill(editor, rand, new RectSolid(start, end));
		
		start = new Coord(ground);
		start.add(new Coord(-2, 0, -2));
		end = new Coord(ground);
		end.add(new Coord(2, 5, 2));
		BlockType.get(BlockType.AIR).fill(editor, rand, new RectSolid(start, end));
		
		for(Coord p : new RectSolid(ground, pos)){
			editor.spiralStairStep(rand, p, theme.getPrimary().getStair(), Log.getLog(Wood.OAK));
		}
		
		start = new Coord(ground);
		start.add(Cardinal.UP, 40);
		end = new Coord(start);
		start.add(new Coord(-30, 0, -30));
		end.add(new Coord(30, 0, 30));
		
		for(Coord c : new RectSolid(start, end)){
			if(c.getX() % 5 != 0 || c.getZ() % 5 != 0) continue;	
			IBlockFactory glass = ColorBlock.get(ColorBlock.GLASS, DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
			new Line(ground, c).fill(editor, rand, glass);	
		}
		
	}
}
