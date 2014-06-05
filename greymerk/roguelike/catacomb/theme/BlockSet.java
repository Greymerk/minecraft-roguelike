package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public class BlockSet implements IBlockSet {

	private IBlockFactory fill;
	private MetaBlock stair;
	private IBlockFactory pillar;
	
	public BlockSet(IBlockFactory fill, MetaBlock stair, IBlockFactory pillar){
		this.fill = fill;		
		this.stair = stair;
		this.pillar = pillar;
	}
	
	@Override
	public IBlockFactory getFill() {
		return fill;
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
