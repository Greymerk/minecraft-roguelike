package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.dungeon.fragment.parts.Fungus;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.FlowerPot;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.settings.ILevelSettings;

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
		wet.addBlock(BlockType.get(BlockType.CLAY));
		wet.addBlock(BlockType.get(BlockType.SOUL_SAND));
		wet.addBlock(BlockType.get(BlockType.MYCELIUM));
		
		BlockJumble dry = new BlockJumble();
		dry.addBlock(BlockType.get(BlockType.DIRT_PODZOL));
		dry.addBlock(BlockType.get(BlockType.DIRT));
		dry.addBlock(BlockType.get(BlockType.DIRT_COARSE));
		
		switch(counter){
		case 5:
		case 4:
			BlockType.get(BlockType.DIRT).set(editor, pos);
		case 3:
			if(rand.nextBoolean()){
				BlockType.get(BlockType.DIRT_COARSE).set(editor, pos);
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
			BlockType.get(BlockType.WATER_FLOWING).set(editor, pos);
			return;
		}
		
		
		if(rand.nextInt(3) != 0) return;
		
		BlockJumble shrooms = new BlockJumble();
		shrooms.addBlock(MetaBlock.of(FlowerPot.getFlower(FlowerPot.BROWNMUSHROOM)));
		shrooms.addBlock(MetaBlock.of(FlowerPot.getFlower(FlowerPot.REDMUSHROOM)));
		
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
