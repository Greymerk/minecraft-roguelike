package com.greymerk.roguelike.dungeon.fragment.parts;

import java.util.List;
import java.util.Optional;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.Line;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class SpiderNest implements IFragment {

	public static void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin) {
		new SpiderNest().generate(editor, rand, settings, origin, Cardinal.NORTH);
	}
	
	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		if(!this.validLocation(editor, origin)) return;
		
		Spawner.generate(editor, rand, settings.getDifficulty(), origin, Spawner.CAVESPIDER);
		List<Coord> ends = RandHelper.pickCountFrom(findSolids(editor, origin, 8), rand, 10);;
		
		ends.forEach(end -> {
			getStrand(editor, origin, end).ifPresent(strand -> {
				strand.forEach(c -> {
					strand.fill(editor, rand, MetaBlock.of(Blocks.COBWEB), Fill.AIR);
				});
			});
		});
		
	}
		
	private Optional<Line> getStrand(IWorldEditor editor, Coord start, Coord end) {
		List<Coord> segments = Line.of(start, end).get().stream()
				.sorted((a, b) -> {
					return (int) (Math.round(start.distance(b)) - Math.round(start.distance(a)));
				}).toList();
		
		for(Coord c : segments) {
			if(c.equals(start)) continue;
			if(editor.isSupported(c)) return Optional.of(Line.of(start, c));
		}
		
		return Optional.empty();
	}
	
	private List<Coord> findSolids(IWorldEditor editor, Coord origin, int range){
		return BoundingBox.of(origin).grow(Cardinal.all, range)
				.get().stream().filter(c -> {
					return editor.isSolid(c) && editor.isSupported(c);
				}).toList();
	}
	
	private boolean validLocation(IWorldEditor editor, Coord origin) {
		for(Coord c : BoundingBox.of(origin).grow(Cardinal.all)) {
			if(!editor.isAir(c)) return false;
		}
		
		return true;
	}
}
