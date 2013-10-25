package greymerk.roguelike;

public class MetaBlock {

	private int blockID;
	private int meta;
	private int flag;
	
	public MetaBlock(int blockID){
		this(blockID, 0, 2);		
	}
	
	public MetaBlock(int blockID, int meta){
		this(blockID, meta, 2);
	}
	
	public MetaBlock(int blockID, int meta, int flag){
		this.blockID = blockID;
		this.meta = meta;
		this.flag = flag;
	}
	
	public int getBlockID(){
		return blockID;
	}
	
	public int getMeta(){
		return meta;
	}
	
	public int getFlag(){
		return flag;
	}
	
	public void setBlockID(int in){
		blockID = in;
	}
	
	public void setMeta(int in){
		meta = in;
	}
	
	public void setFlag(int in){
		flag = in;
	}
}
