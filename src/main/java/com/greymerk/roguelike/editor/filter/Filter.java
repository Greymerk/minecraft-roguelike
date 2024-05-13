package com.greymerk.roguelike.editor.filter;

public enum Filter {
	VINE, ENCASE, WIREFRAME, COBWEB, MUD, POTS;
	
	public static IFilter get(Filter type){
		switch(type){
		case VINE: return new VineFilter();
		case ENCASE: return new EncaseFilter();
		case WIREFRAME: return new WireframeFilter();
		case COBWEB: return new CobwebFilter();
		case MUD: return new MudFilter();
		case POTS: return new DecoratedPotFilter();
		default: return new VineFilter();
		}
	}
}
