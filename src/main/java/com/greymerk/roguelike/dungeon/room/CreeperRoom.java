package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.chest.ChestType;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.util.math.random.Random;

public class CreeperRoom extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(worldPos.copy());
		Coord origin = worldPos.copy();
		IBlockFactory wall = theme.getPrimary().getWall();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.directions, 4).grow(Cardinal.UP, 6);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.UP, 3).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, wall);
		
		for(Cardinal dir : Cardinal.directions) {
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 3).add(Cardinal.left(dir), 3).grow(Cardinal.UP, 6);
			RectSolid.fill(editor, rand, bb, pillar);
			bb.add(dir).add(Cardinal.left(dir));
			RectSolid.fill(editor, rand, bb, pillar);
			
			bb = new BoundingBox(origin.copy());
			bb.add(Cardinal.UP, 3).add(dir, 2).grow(Cardinal.orthogonal(dir), 4);
			RectSolid.fill(editor, rand, bb, wall);
			bb.add(dir, 2);
			RectSolid.fill(editor, rand, bb, wall);
			bb.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, wall);
			bb.add(Cardinal.reverse(dir), 2);
			RectSolid.fill(editor, rand, bb, wall);
			
			bb = new BoundingBox(origin.copy());
			bb.add(Cardinal.UP, 3).add(dir).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, wall);
			bb.add(dir).add(Cardinal.left(dir), 2);
			RectSolid.fill(editor, rand, bb, wall);
			
			Coord pos = origin.copy();
			pos.add(Cardinal.UP, 3).add(dir, 3);
			wall.set(editor, rand, pos);
			pos.add(Cardinal.UP, 3);
			wall.set(editor, rand, pos);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 4).add(o, 2).grow(Cardinal.UP, 6).grow(o);
				RectSolid.fill(editor, rand, bb, pillar);
				
				pos = origin.copy();
				pos.add(dir, 4).add(o).add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
				pos.add(Cardinal.reverse(dir)).add(o);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
				
			}
		}
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN, 3);
		bb.grow(Cardinal.directions, 4);
		RectSolid.fill(editor, rand, bb, wall);
		
		BlockJumble gravelNonsense = new BlockJumble();
		gravelNonsense.addBlock(wall);
		gravelNonsense.addBlock(BlockType.get(BlockType.GRAVEL));
		
		BlockJumble tntNonsense = new BlockJumble();
		tntNonsense.addBlock(gravelNonsense);
		tntNonsense.addBlock(BlockType.get(BlockType.TNT));
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions, 3);
		RectSolid.fill(editor, rand, bb, gravelNonsense);
		bb.add(Cardinal.DOWN);
		RectSolid.fill(editor, rand, bb, tntNonsense);
		
		bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.directions, 2);
		List<Coord> chestSpots = bb.getShape(Shape.RECTSOLID).get();
		RandHelper.shuffle(chestSpots, rand);
		Coord chestPos = chestSpots.get(0);
		Treasure.generate(editor, rand, chestPos, Treasure.ORE, ChestType.TRAPPED_CHEST);
		chestPos.add(Cardinal.DOWN, 2);
		BlockType.get(BlockType.TNT).set(editor, chestPos);
		
		Coord pos = origin.copy().add(Cardinal.UP, 4);
		Spawner.generate(editor, rand, pos, Spawner.CREEPER);
	}
	
	@Override
	public String getName() {
		return Room.CREEPER.name();
	}

}
