package greymerk.roguelike.catacomb.settings;


public interface ICatacombSettings {

	public boolean isValid();
	
	public CatacombLevelSettings getLevelSettings(int level);
	
}
