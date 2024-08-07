package com.greymerk.roguelike.dungeon.layout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.util.StructureLocator;

public class ExclusionZones {

	private Set<BoundingBox> zones;
	private static int SIZE_OF_ZONES = 100;
	
	public ExclusionZones() {
		this.zones = new HashSet<BoundingBox>();
	}
	
	public void add(Coord pos) {
		this.zones.add(BoundingBox.of(pos)
				.grow(Cardinal.directions, SIZE_OF_ZONES)
				.grow(List.of(Cardinal.UP, Cardinal.DOWN), 64));
	}
	
	public boolean collides(IBounded room) {
		for(IBounded zone : zones) {
			if(room.collide(zone)) return true;
		}
		return false;
	}
	
	public void scan(IWorldEditor editor, Coord pos, int range) {
		Set<Coord> structureLocations = StructureLocator.scan(editor, pos, StructureLocator.UNDERGROUND, range);
		structureLocations.forEach(location -> {
			this.add(location);
		});
	}
	
	@Override
	public String toString() {
		return "Zones: " + this.zones.size() + " : " + List.of(zones).toString();
	}
}
