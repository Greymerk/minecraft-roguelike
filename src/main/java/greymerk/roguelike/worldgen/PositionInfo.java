package greymerk.roguelike.worldgen;

import net.minecraft.world.biome.Biome;

public class PositionInfo implements IPositionInfo {

	private IWorldEditor editor;
	private Coord pos;
	
	public PositionInfo(IWorldEditor editor, Coord pos){
		this.editor = editor;
		this.pos = pos;
	}
	
	@Override
	public int getDimension() {
		return this.editor.getDimension();
	}

	@Override
	public Biome getBiome() {
		return editor.getBiome(pos);
	}

	@Override
	public boolean validGroundBlock() {
		return editor.validGroundBlock(pos);
	}

	@Override
	public MetaBlock getBlock() {
		return editor.getBlock(pos);
	}
}
