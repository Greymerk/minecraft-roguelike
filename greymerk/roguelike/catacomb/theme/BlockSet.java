package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public class BlockSet implements IBlockSet {

	private IBlockFactory fill;
	private IBlockFactory bridge;
	private MetaBlock stair;
	private IBlockFactory pillar;
	
	public BlockSet(IBlockFactory fill, IBlockFactory bridge, MetaBlock stair, IBlockFactory pillar){
		this.fill = fill;
		this.bridge = bridge;
		this.stair = stair;
		this.pillar = pillar;
	}
	
	@Override
	public IBlockFactory getFill() {
		return fill;
	}

	@Override
	public IBlockFactory getBridge() {
		return bridge;
	}

	@Override
	public MetaBlock getStair() {
		return new MetaBlock(stair);
	}

	@Override
	public IBlockFactory getPillar() {
		return pillar;
	}

}
