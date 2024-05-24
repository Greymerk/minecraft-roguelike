package com.greymerk.roguelike.filter;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.FlowerPot;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.theme.ITheme;

public class MudFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
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
		
		
		if(rand.nextInt(6) != 0) return;
		
		BlockJumble plants = new BlockJumble();
		plants.addBlock(new MetaBlock(FlowerPot.getFlower(FlowerPot.BROWNMUSHROOM)));
		plants.addBlock(new MetaBlock(FlowerPot.getFlower(FlowerPot.REDMUSHROOM)));
		
		Coord cursor = pos.copy();
		cursor.add(Cardinal.UP);
		plants.set(editor, rand, cursor);
	}
	
	private boolean validLocation(IWorldEditor editor, Random rand, Coord pos){
		
		if(!editor.isSolid(pos)) return false;
		
		Coord cursor = pos.copy();
		cursor.add(Cardinal.UP);
		if(!editor.isAir(cursor)) return false;
		
		cursor.add(Cardinal.DOWN, 2);
		if(editor.isAir(cursor)) return false;
		
		cursor.add(Cardinal.UP);
		
		for(Cardinal dir : Cardinal.values()){
			cursor.add(dir);
			if(!editor.isSolid(pos)) return false;
			cursor.add(Cardinal.reverse(dir));
		}
		
		return true;
	}
}
