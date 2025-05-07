package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.dungeon.fragment.parts.Fungus;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.FlowerPot;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class MudFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box) {
		for(Coord pos : box.getShape(Shape.RECTSOLID)){
			if(rand.nextInt(40) != 0) continue;
			if(!validLocation(editor, rand, pos)) continue;
			generate(editor, rand, pos, rand.nextInt(3) + 2);
		}
	}
	
	private void generate(IWorldEditor editor, Random rand, Coord pos, int counter){
		if(counter <= 0) return;
		
		for(Cardinal dir : Cardinal.directions){
			if(rand.nextBoolean()) continue;
			Coord next = pos.copy();
			next.add(dir);
			generate(editor, rand, next, counter - 1);
		}
		
		if(!validLocation(editor, rand, pos)) return;
		
		BlockJumble wet = new BlockJumble();
		wet.add(MetaBlock.of(Blocks.CLAY));
		wet.add(MetaBlock.of(Blocks.SOUL_SAND));
		wet.add(MetaBlock.of(Blocks.MYCELIUM));
		
		BlockJumble dry = new BlockJumble();
		dry.add(MetaBlock.of(Blocks.PODZOL));
		dry.add(MetaBlock.of(Blocks.DIRT));
		dry.add(MetaBlock.of(Blocks.COARSE_DIRT));
		
		switch(counter){
		case 5:
		case 4:
			MetaBlock.of(Blocks.DIRT).set(editor, pos);
		case 3:
			if(rand.nextBoolean()){
				MetaBlock.of(Blocks.COARSE_DIRT).set(editor, pos);
				break;
			}
		case 2:
			wet.set(editor, rand, pos);
			break;
		case 1:
			if(rand.nextBoolean()){
				wet.set(editor, rand, pos);
				break;
			}
		default:
			MetaBlock.of(Blocks.WATER).set(editor, pos);
			return;
		}
		
		
		if(rand.nextInt(3) != 0) return;
		
		BlockJumble shrooms = new BlockJumble();
		shrooms.add(MetaBlock.of(FlowerPot.getFlower(FlowerPot.BROWNMUSHROOM)));
		shrooms.add(MetaBlock.of(FlowerPot.getFlower(FlowerPot.REDMUSHROOM)));
		
		if(rand.nextInt(3) == 0) {
			Fungus.generate(editor, rand, pos.copy().add(Cardinal.UP));
		} else {
			shrooms.set(editor, rand, pos.copy().add(Cardinal.UP));	
		}
	}
	
	private boolean validLocation(IWorldEditor editor, Random rand, Coord origin){
		
		if(!editor.isSolid(origin)) return false;
		
		if(!editor.isAir(origin.copy().add(Cardinal.UP))) return false;
		if(editor.isAir(origin.copy().add(Cardinal.DOWN))) return false;
		
		for(Cardinal dir : Cardinal.directions){
			if(!editor.isSolid(origin.copy().add(dir))) return false;
		}
		
		return true;
	}
}
