package com.greymerk.roguelike.filter;

public enum Filter {
	VINE, ENCASE, WIREFRAME, COBWEB, MUD, POTS, SCULK, EXPLOSIVE, LEAFLITTER, CHAINS;
	
	public static IFilter get(Filter type){
		switch(type){
		case VINE: return new VineFilter();
		case ENCASE: return new EncaseFilter();
		case WIREFRAME: return new WireframeFilter();
		case COBWEB: return new CobwebFilter();
		case MUD: return new MudFilter();
		case POTS: return new DecoratedPotFilter();
		case SCULK: return new SculkFilter();
		case EXPLOSIVE: return new ExplosiveTrapFilter();
		case LEAFLITTER: return new LeafLitterFilter();
		case CHAINS: return new ChainFilter();
		default: return new VineFilter();
		}
	}
}
