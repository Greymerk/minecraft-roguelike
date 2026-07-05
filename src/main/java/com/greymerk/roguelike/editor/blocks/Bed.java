package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BedPart;

public class Bed {

	public static void generate(IWorldEditor editor, RandomSource rand, Cardinal dir, Coord pos) {
		generate(editor, dir, pos, Color.get(rand));
	}
	
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, dir, pos, Color.RED);
	}
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord origin, Color color){
		Block b = Blocks.BED.pick(Color.get(color));
		
		MetaBlock.of(b)
			.with(BedBlock.PART, BedPart.HEAD)
			.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir))
			.set(editor, origin);
		
		MetaBlock.of(b)
			.with(BedBlock.PART, BedPart.FOOT)
			.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir))
			.set(editor, origin.copy().add(dir));
	}
}
