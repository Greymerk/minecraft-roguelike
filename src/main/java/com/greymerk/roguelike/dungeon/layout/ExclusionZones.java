package com.greymerk.roguelike.dungeon.layout;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.util.StructureLocator;

public class ExclusionZones {

	private Set<Zone> zones;
	private static int SIZE_OF_ZONES = 50;
	
	public ExclusionZones() {
		this.zones = new HashSet<Zone>();
	}
	
	public boolean collides(IBounded box) {
		for(Zone zone : zones) {
			if(zone.collide(box)) return true;
		}
		return false;
	}
	
	public boolean collides(Coord pos, int range) {
		return this.collides(BoundingBox.of(Coord.of(pos.getX(), 0, pos.getZ()))
				.grow(Cardinal.directions, range)
				.grow(List.of(Cardinal.UP, Cardinal.DOWN), 64));
	}
	
	public void scan(IWorldEditor editor, Coord pos, int range) {
		Set<Coord> structureLocations = StructureLocator.scan(editor, pos, StructureLocator.UNDERGROUND, range);
		structureLocations.forEach(location -> {
			this.zones.add(Zone.of(location));
		});
	}
	
	public Optional<Coord> nearest(Coord pos) {
		if(this.zones.isEmpty()) return Optional.empty();
		
		return Optional.of(zones.stream().min((a, b) -> {
			return (int)(a.origin.distance(pos) - b.origin.distance(pos));
		}).get().origin);
	}
	
	@Override
	public String toString() {
		return "Zones: " + this.zones.size() + " : " + List.of(zones).toString();
	}
	
	private static class Zone {

		private Coord origin;
		private BoundingBox bb;
		
		public static Zone of(Coord origin) {
			return new Zone(origin);
		}
		
		private Zone(Coord origin) {
			this.origin = origin;
			this.bb = BoundingBox.of(origin)
					.grow(Cardinal.directions, SIZE_OF_ZONES)
					.grow(List.of(Cardinal.UP, Cardinal.DOWN), 64);
		}
		
		public boolean collide(IBounded box) {
			return box.collide(this.bb);
		}
		
		@Override
		public String toString() {
			return origin.toString();
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(bb, origin);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Zone other = (Zone) obj;
			return Objects.equals(bb, other.bb) && Objects.equals(origin, other.origin);
		}
	}
}
