package com.greymerk.roguelike.dungeon.tower;

import com.greymerk.roguelike.dungeon.fragment.parts.SpiralStairCase;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.IronBars;
import com.greymerk.roguelike.editor.blocks.Torch;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;


public class RogueTower implements ITower{

	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon){
		
		
		int x = dungeon.getX();
		int y = dungeon.getY();
		int z = dungeon.getZ();
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		IBlockFactory blocks = theme.getPrimary().getWall();
		
		IStair stair = theme.getPrimary().getStair();
		
		Coord towerBase = Tower.getBaseCoord(editor, dungeon);
		int ground = towerBase.getY() - 1;
		int main = towerBase.getY() + 4;
		int roof = towerBase.getY() + 9;
		
		RectSolid.fill(editor, rand, new Coord(x - 3, ground, z - 3), new Coord(x + 3, towerBase.getY() + 12, z + 3), air);
		
		RectSolid.fill(editor, rand, new Coord(x - 2, y + 10, z - 2), new Coord(x + 2, towerBase.getY() - 1, z + 2), blocks, false, true);

		Coord start;
		Coord end;
		Coord cursor;
		
		RectSolid.fill(editor, rand, new Coord(x - 3, main, z - 3), new Coord(x + 3, main, z + 3), theme.getSecondary().getWall(), true, true);
		RectSolid.fill(editor, rand, new Coord(x - 3, roof, z - 3), new Coord(x + 3, roof, z + 3), blocks);
		
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.orthogonal(dir)){
				// ground floor
				start = towerBase.copy();
				start.add(Cardinal.DOWN, 1);
				start.add(dir, 2);
				end = start.copy();
				end.add(dir, 3);
				end.add(orth, 1);
				RectSolid.fill(editor, rand, start, end, blocks, true, true);
				start.add(orth, 2);
				end.add(Cardinal.reverse(dir), 2);
				end.add(orth, 2);
				RectSolid.fill(editor, rand, start, end, blocks, true, true);
				
				cursor = towerBase.copy();
				cursor.add(dir, 5);
				cursor.add(orth, 1);
				start = cursor.copy();
				end = cursor.copy();
				end.add(Cardinal.reverse(dir), 1);
				end.add(Cardinal.UP, 2);
				RectSolid.fill(editor, rand, start, end, blocks);
				start = end.copy();
				start.add(dir, 1);
				start.add(Cardinal.reverse(orth), 1);
				RectSolid.fill(editor, rand, start, end, blocks);
				cursor.add(Cardinal.UP, 2);
				stair.setOrientation(orth, false);
				stair.set(editor, cursor);
				
				start = towerBase.copy();
				start.add(dir, 4);
				end = start.copy();
				end.add(Cardinal.UP, 9);
				end.add(orth, 2);
				RectSolid.fill(editor, rand, start, end, blocks);
				
				start = towerBase.copy();
				start.add(dir, 3);
				start.add(orth, 3);
				end = start.copy();
				end.add(Cardinal.UP, 9);
				RectSolid.fill(editor, rand, start, end, blocks);
				
				start = towerBase.copy();
				start.add(dir, 4);
				end = start.copy();
				end.add(dir, 1);
				end.add(Cardinal.UP, 1);
				RectSolid.fill(editor, rand, start, end, air);
				
				cursor = towerBase.copy();
				cursor.add(dir, 3);
				cursor.add(orth, 2);
				cursor.add(Cardinal.UP, 3);
				stair.setOrientation(Cardinal.reverse(orth), true);
				stair.set(editor, cursor);
				cursor.add(Cardinal.UP, 5);
				stair.setOrientation(Cardinal.reverse(orth), true);
				stair.set(editor, cursor);
				
				start = towerBase.copy();
				start.add(dir, 4);
				start.add(orth, 3);
				start.add(Cardinal.UP, 4);
				stair.setOrientation(orth, true);
				stair.set(editor, start);
				
				start.add(Cardinal.UP, 1);
				end = start.copy();
				end.add(Cardinal.UP, 4);
				RectSolid.fill(editor, rand, start, end, blocks, true, true);
				
				start = towerBase.copy();
				start.add(dir, 5);
				start.add(Cardinal.UP, 4);
				stair.setOrientation(dir, true);
				stair.set(editor, start);
				
				cursor = start.copy();
				cursor.add(orth, 1);
				stair.setOrientation(orth, true);
				stair.set(editor, cursor);
				
				start.add(Cardinal.UP, 3);
				stair.setOrientation(dir, true);
				stair.set(editor, start);
				
				cursor = start.copy();
				cursor.add(orth, 1);
				stair.setOrientation(orth, true);
				stair.set(editor, cursor);
				
				start.add(Cardinal.UP, 1);
				end = start.copy();
				end.add(orth, 1);
				end.add(Cardinal.UP, 1);
				RectSolid.fill(editor, rand, start, end, blocks, true, true);
				
				cursor = end.copy();
				cursor.add(orth, 1);
				cursor.add(Cardinal.DOWN, 1);
				stair.setOrientation(orth, true);
				stair.set(editor, cursor);
				cursor.add(Cardinal.UP, 1);
				cursor.add(orth, 1);
				stair.set(editor, cursor);
				
				cursor.add(Cardinal.reverse(orth), 1);
				blocks.set(editor, rand, cursor);
				cursor.add(Cardinal.UP, 1);
				blocks.set(editor, rand, cursor);
				cursor.add(orth, 1);
				blocks.set(editor, rand, cursor);
				cursor.add(Cardinal.UP, 1);
				this.addCrenellation(editor, rand, cursor, blocks);
				
				cursor.add(Cardinal.DOWN, 2);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				blocks.set(editor, rand, cursor);
				cursor.add(Cardinal.DOWN, 1);
				blocks.set(editor, rand, cursor);
				
				cursor = towerBase.copy();
				cursor.add(dir, 6);
				cursor.add(Cardinal.UP, 9);
				
				stair.setOrientation(dir, true);
				stair.set(editor, cursor);
				
				cursor.add(orth, 1);
				stair.setOrientation(orth, true);
				stair.set(editor, cursor);
				
				cursor.add(Cardinal.reverse(orth), 1);
				cursor.add(Cardinal.UP, 1);
				blocks.set(editor, rand, cursor);
				cursor.add(orth, 1);
				blocks.set(editor, rand, cursor);
				cursor.add(Cardinal.UP, 1);
				this.addCrenellation(editor, rand, cursor, blocks);
				
				cursor = towerBase.copy();
				cursor.add(dir, 4);
				cursor.add(Cardinal.UP, 5);
				air.set(editor, cursor);
				cursor.add(Cardinal.UP, 1);
				air.set(editor, cursor);
				cursor.add(orth, 2);
				IronBars.set(editor, cursor, dir);
			}
		}
		
		// wall down to entrance room
		int entranceY = (ground - ground % 10) - 10;
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.orthogonal(dir)){
				start = new Coord(x, ground, z);
				start.add(dir, 4);
				end = new Coord(x, entranceY, z);
				end.add(dir, 4);
				start.add(Cardinal.reverse(orth), 2);
				end.add(orth, 2);
				RectSolid.fill(editor, rand, start, end, blocks, true, false);
				
				start = new Coord(x, ground, z);
				start.add(dir, 3);
				start.add(orth, 3);
				end = new Coord(x, entranceY, z);
				end.add(dir, 3);
				end.add(orth, 3);
				RectSolid.fill(editor, rand, start, end, blocks, true, false);
				
			}
		}
		
		start = towerBase.copy();
		start.add(Cardinal.UP, 4);
		end = dungeon.copy();
		SpiralStairCase stairCase = new SpiralStairCase(start, end);
		stairCase.generate(editor, rand, theme);
	}
	
	
	private void addCrenellation(IWorldEditor editor, Random rand, Coord cursor, IBlockFactory blocks){
		
		blocks.set(editor, rand, cursor);
		
		if(editor.isAir(cursor)) return;

		cursor.add(Cardinal.UP, 1);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
	}
}
