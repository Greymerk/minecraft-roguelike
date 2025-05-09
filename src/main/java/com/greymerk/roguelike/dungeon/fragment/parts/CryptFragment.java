package com.greymerk.roguelike.dungeon.fragment.parts;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Quartz;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.util.math.random.Random;

public class CryptFragment implements IFragment {

	boolean empty;
	
	public CryptFragment() {
		empty = false;
	}
	
	public CryptFragment(boolean empty) {
		this.empty = empty;
	}
	
	public void setEmpty(boolean e) {
		this.empty = e;
	}
	
	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		ITheme theme = settings.getTheme();
		if(empty && rand.nextBoolean()) {
			Air.get().set(editor, origin);
		} else {
			Quartz.get(Quartz.SMOOTH).set(editor, origin);	
		}
		Coord pos = origin.copy();
		pos.add(dir);
		if(empty) {
			Air.get().set(editor, pos);
		} else {
			Spawner type = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
			Spawner.generate(editor, rand, settings.getDifficulty(), pos, type);
		}
		
		pos.add(dir);
		if(empty) {
			Air.get().set(editor, pos);
		} else {
			Treasure type = rand.nextBoolean() ? Treasure.ARMOR : Treasure.WEAPON;
			Treasure.generate(editor, rand, settings.getDifficulty(), pos, Cardinal.reverse(dir), type);
		}
		pos.add(Cardinal.UP);
		IStair stair = theme.getPrimary().getStair();
		stair.setOrientation(Cardinal.reverse(dir), true);
		stair.set(editor, rand, pos);
		pos.add(Cardinal.reverse(dir));
		stair.setOrientation(dir, true);
		stair.set(editor, rand, pos);
	}
}
