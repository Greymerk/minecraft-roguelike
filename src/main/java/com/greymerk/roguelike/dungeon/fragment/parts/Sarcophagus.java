package com.greymerk.roguelike.dungeon.fragment.parts;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.slab.ISlab;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.util.math.random.Random;

public class Sarcophagus implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		ISlab slab = theme.getPrimary().getSlab();
		
		Coord pos = origin.copy();
		walls.set(editor, rand, pos);
		pos.add(Cardinal.UP, 2);
		slab.upsideDown(true).set(editor, pos);
		
		for(Cardinal o : Cardinal.orthogonal(Cardinal.left(dir))) {
			pos = origin.copy().add(o);
			stair.setOrientation(o, false).set(editor, rand, pos);
			pos.add(Cardinal.UP);
			stair.setOrientation(o, true).set(editor, rand, pos);
			pos.add(Cardinal.UP);
			stair.setOrientation(o, false).set(editor, rand, pos);
		}
		
		for(Cardinal orth : Cardinal.orthogonal(dir)) {
			pos = origin.copy().add(orth);
			walls.set(editor, rand, pos);
			pos.add(orth);
			stair.setOrientation(orth, false).set(editor, rand, pos);
			pos.add(Cardinal.UP);
			stair.setOrientation(orth, true).set(editor, rand, pos);
			pos.add(Cardinal.UP);
			stair.setOrientation(orth, false).set(editor, rand, pos);
			pos.add(Cardinal.reverse(orth));
			slab.upsideDown(true).set(editor, pos);
			
			for(Cardinal o : Cardinal.orthogonal(orth)) {
				pos = origin.copy().add(orth).add(o);
				stair.setOrientation(o, false).set(editor, rand, pos);
				pos.add(orth);
				stair.setOrientation(o, false).set(editor, rand, pos);
				pos.add(Cardinal.UP);
				stair.setOrientation(o, true).set(editor, rand, pos);
				pos.add(Cardinal.reverse(orth));
				stair.setOrientation(o, true).set(editor, rand, pos);
				pos.add(Cardinal.UP);
				stair.setOrientation(o, false).set(editor, rand, pos);
				pos.add(orth);
				stair.setOrientation(o, false).set(editor, rand, pos);
			}
		}
		
		Spawner type = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
		Spawner.generate(editor, rand, origin.copy().add(Cardinal.UP), type);
		
		List<Cardinal> dirs = new ArrayList<Cardinal>(Cardinal.orthogonal(dir));
		RandHelper.shuffle(dirs, rand);
		pos = origin.copy().add(Cardinal.UP).add(dirs.get(0));
		Treasure.generate(editor, rand, pos, dirs.get(0), Treasure.ARMOUR);
		pos = origin.copy().add(Cardinal.UP).add(dirs.get(1));
		Treasure.generate(editor, rand, pos, dirs.get(1), Treasure.WEAPONS);
	}
}
