package greymerk.roguelike.worldgen.filter;

public enum Filter {
	VINE, ENCASE, WIREFRAME, COBWEB;
	
	public static IFilter get(Filter type){
		switch(type){
		case VINE: return new VineFilter();
		case ENCASE: return new EncaseFilter();
		case WIREFRAME: return new WireframeFilter();
		case COBWEB: return new CobwebFilter();
		default: return new VineFilter();
		}
	}
}
