package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Carpet;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class MusicRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = worldPos.copy().add(direction, Cell.SIZE);
		Random rand = editor.getRandom(origin);
		BoundingBox bb = BoundingBox.of(origin);
		bb.grow(Cardinal.directions, 5).grow(Cardinal.UP, 5);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin);
		bb.add(Cardinal.DOWN).grow(Cardinal.directions, 6);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		CellSupport.generate(editor, rand, theme, origin);
		
		bb = BoundingBox.of(origin);
		bb.add(Cardinal.UP, 6).grow(Cardinal.directions, 4);
		RectSolid.fill(editor, rand, bb, theme.getSecondary().getWall());
		
		for(Cardinal dir : Cardinal.directions) {
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 5).add(Cardinal.left(dir), 5).grow(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			IStair stair = theme.getPrimary().getStair();
			IStair stair2 = theme.getSecondary().getStair();
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 5).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 4);
			RectSolid.fill(editor, rand, bb, theme.getSecondary().getWall());
			bb.add(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			bb = BoundingBox.of(origin);
			bb.add(Cardinal.UP, 5).add(dir, 2).grow(Cardinal.orthogonal(dir)).grow(Cardinal.left(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getSlab().upsideDown(true).get());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				bb = BoundingBox.of(origin);
				bb.add(dir, 6).add(o, 3).grow(o, 3).grow(Cardinal.UP, 5);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
				
				bb = BoundingBox.of(origin);
				bb.add(dir, 5).add(o, 2).grow(Cardinal.UP, 3);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
				
				Coord pos = origin.copy().add(dir, 5).add(o).add(Cardinal.UP, 3);
				stair2.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
				pos.add(o, 2);
				stair2.setOrientation(o, true).set(editor, rand, pos);
				pos.add(o);
				stair2.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
				
				pos = origin.copy().add(dir, 4).add(o, 2).add(Cardinal.UP, 3);
				stair2.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
				pos.add(Cardinal.UP);
				theme.getSecondary().getWall().set(editor, rand, pos);
				pos.add(Cardinal.UP);
				theme.getPrimary().getWall().set(editor, rand, pos);
				pos.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
				
				pos = origin.copy().add(dir, 5).add(o, 3);
				stair2.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
				pos.add(o);
				stair2.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
			}
			
			if(this.getExitType(dir).equals(ExitType.DOOR)) {
				bb = BoundingBox.of(origin);
				bb.add(dir, 6).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 3).grow(dir, 2);
				RectSolid.fill(editor, rand, bb, Air.get());
				
				bb = BoundingBox.of(origin);
				bb.add(dir, 6).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 2).grow(dir, 3);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
				
				bb = BoundingBox.of(origin);
				bb.add(dir, 6).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 2).grow(dir, 2);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
				
				bb = BoundingBox.of(origin);
				bb.add(dir, 8).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir));
				RectSolid.fill(editor, rand, bb, theme.getSecondary().getWall());
				
				for(Cardinal o : Cardinal.orthogonal(dir)) {
					bb = BoundingBox.of(origin);
					bb.add(dir, 7).add(o, 3).grow(Cardinal.UP, 4).grow(dir, 3);
					RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
					
					bb = BoundingBox.of(origin);
					bb.add(dir, 8).add(o, 2).grow(Cardinal.UP, 3);
					RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
					
					bb = BoundingBox.of(origin);
					bb.add(dir, 6).add(o, 2).add(Cardinal.UP).grow(dir).grow(Cardinal.UP);
					RectSolid.fill(editor, rand, bb, Air.get());
					
					Coord pos = origin.copy().add(dir, 6).add(o, 2);
					stair2.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
					pos.add(dir);
					stair2.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
					pos.add(Cardinal.UP, 3);
					stair2.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
					pos.add(Cardinal.reverse(dir));
					stair2.setOrientation(dir, true).set(editor, rand, pos);
					
					pos = origin.copy().add(dir, 8).add(o).add(Cardinal.UP, 2);
					stair2.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
				}
			} else {
				bb = BoundingBox.of(origin);
				bb.add(dir, 6).grow(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 2);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), Fill.SOLID);
			}
		}
		
		Carpet.generate(editor, rand, origin, 4);		
		Lantern.set(editor, origin.copy().add(Cardinal.UP, 5));
		
		MetaBlock.of(Blocks.JUKEBOX).set(editor, origin);
		
		Cardinal d = Cardinal.randDirs(rand).get(0);
		Coord pos = origin.copy().add(d, 5).add(Cardinal.orthogonal(d).get(rand.nextInt(2)), rand.nextInt(2) + 3).add(Cardinal.UP);
		Treasure.generate(editor, rand, settings.getDifficulty(), pos, Cardinal.reverse(d), Treasure.MUSIC);
		
		doors(editor, rand, origin);
	}

	private void doors(IWorldEditor editor, Random rand, Coord origin) {
		this.exits.forEach(exit -> {
			if(exit.type() == ExitType.DOOR) {
				Fragment.generate(Fragment.ARCH, editor, rand, settings, exit.origin(), exit.dir());	
			}
		});
	}

	@Override
	public String getName() {
		return Room.MUSIC.name();
	}

}
