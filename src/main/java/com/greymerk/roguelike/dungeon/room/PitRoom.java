package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Piston;
import com.greymerk.roguelike.editor.blocks.RedstoneTorch;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class PitRoom extends AbstractMediumRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.worldPos.copy().add(direction, Cell.SIZE).freeze();
		Random rand = editor.getRandom(origin);
		this.centerRoom(editor, rand, origin);
		this.drillDown(editor, rand, origin.add(Cardinal.DOWN).freeze());
		Cardinal.directions.forEach(dir -> {
			if(this.getEntrance(dir) == Entrance.DOOR) {
				this.entryRoom(editor, rand, origin, dir);	
			} else {
				this.pistonTrap(editor, rand, origin, dir);
			}
		});
		
		CellSupport.generate(editor, rand, theme, origin);
	}
	
	private void centerRoom(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 2).grow(Cardinal.UP, 3).fill(editor, rand, Air.get());
		BoundingBox.of(origin).grow(Cardinal.directions, 3).add(Cardinal.DOWN).fill(editor, rand, this.theme.getPrimary().getWall());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 3).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.UP, 4).grow(Cardinal.DOWN).fill(editor, rand, this.theme.getPrimary().getWall());
		});
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.directions, 3).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
	}
	
	private void entryRoom(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Corridor room = new Corridor();
		room.setWorldPos(origin.add(dir, Cell.SIZE));
		room.setDirection(dir);
		room.setLevelSettings(settings);
		Cardinal.parallel(dir).forEach(d -> {
			room.addEntrance(d, Entrance.DOOR);	
		});
		Cardinal.orthogonal(dir).forEach(d -> {
			room.addEntrance(d, Entrance.WALL);
		});
		room.generate(editor);
	}
	
	private void drillDown(IWorldEditor editor, Random rand, Coord origin) {
		if(origin.getY() <= -60) return;
		
		BoundingBox.of(origin).grow(Cardinal.directions).fill(editor, rand, origin.getY() > -55 ? Air.get() : theme.getPrimary().getLiquid());	
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir), 2).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		});
		
		if(!editor.isSolid(origin.add(Cardinal.DOWN))) return;
		this.drillDown(editor, rand, origin.add(Cardinal.DOWN).freeze());
	}

	private void pistonTrap(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).add(dir, 3).grow(Cardinal.UP, 2).grow(Cardinal.DOWN, 2).grow(Cardinal.orthogonal(dir), 2).grow(dir, 3).fill(editor, rand, this.theme.getPrimary().getWall());
		MetaBlock.of(Blocks.STONE_PRESSURE_PLATE).set(editor, origin.add(dir, 2));
		RedstoneTorch.generate(editor, origin.add(dir, 3).add(Cardinal.DOWN), dir, true);
		RedstoneTorch.generate(editor, origin.add(dir, 5), false);
		MetaBlock.of(Blocks.REDSTONE_WIRE).set(editor, origin.add(dir, 4).add(Cardinal.DOWN));
		Piston.generate(editor, origin.add(dir, 4).add(Cardinal.UP), Cardinal.reverse(dir), true);
	}
	
	@Override
	public String getName() {
		return Room.PIT.name();
	}

}
