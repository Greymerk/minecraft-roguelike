
package com.greymerk.roguelike.dungeon.room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greymerk.roguelike.dungeon.layout.Exit;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.dynamic.Codecs;

public enum Room {

	CORRIDOR, ENTRANCE, STAIRWAY, CROSS, BEDROOM, CRYPT,
	RESERVOIR, CISTERN, OSSUARY, KITCHEN, CREEPER, ENDER,
	ABYSS, PRISON, MUSIC, BREWING, PANOPTICON, BANQUET, SCULK,
	BTEAM, LIBRARY, SMITH, PIT, BALCONY;
	
	public static IRoom fromType(Room type) {
		switch(type) {
		case CORRIDOR: return new Corridor();
		case ENTRANCE: return new EntranceRoom();
		case STAIRWAY: return new Stairway();
		case CROSS: return new CrossRoom();
		case BEDROOM: return new BedRoom();
		case CRYPT: return new CryptRoom();
		case RESERVOIR: return new ReservoirRoom();
		case CISTERN: return new CisternRoom();
		case OSSUARY: return new OssuaryRoom();
		case KITCHEN: return new KitchenRoom();
		case CREEPER: return new CreeperRoom();
		case ENDER: return new EnderRoom();
		case ABYSS: return new AbyssRoom();
		case PRISON: return new PrisonRoom();
		case MUSIC: return new MusicRoom();
		case BREWING: return new BrewingRoom();
		case PANOPTICON: return new PanopticonRoom();
		case BANQUET: return new BanquetRoom();
		case SCULK: return new SculkRoom();
		case BTEAM: return new BTeamRoom();
		case LIBRARY: return new LibraryRoom();
		case SMITH: return new SmithRoom();
		case PIT: return new PitRoom();
		case BALCONY: return new BalconyRoom();
		default: return new Corridor();
		}
	}
	
	public static final Codec<List<Exit>> EXITS_CODEC = Codec.list(Exit.CODEC);
	
	public static final Codec<IRoom> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				Codecs.NON_EMPTY_STRING.fieldOf("name").forGetter(room -> room.getName()),
				Codecs.NON_EMPTY_STRING.fieldOf("settings").forGetter(room -> room.getLevelSettings().getName()),
				Coord.CODEC.fieldOf("pos").forGetter(room -> room.getWorldPos()),
				Codecs.NON_EMPTY_STRING.fieldOf("dir").forGetter(room -> room.getDirection().name()),
				//ENTRANCES_CODEC.fieldOf("entrances").forGetter(room -> Room.convertEntrances(room.getEntrances())),
				EXITS_CODEC.fieldOf("exits").forGetter(room -> room.getExits()),
				Codec.BOOL.fieldOf("generated").forGetter(room -> room.isGenerated())
			).apply(instance, (name, settings, pos, dir, exits, generated) -> Room.getInstance(name, settings, pos, dir, exits, generated))
	);
	
	
	public static Room get(String name) {
		for(Room type : Room.values()) {
			if(type.toString().equals(name)) return type;
		}
		return CORRIDOR;
	}
	
	public static boolean contains(String name) {
		for(Room type : Room.values()) {
			if(type.toString().equals(name)) return true;
		}
		return false;
	}
	
	public static IRoom getInstance(Room type, ILevelSettings settings) {
		IRoom room = fromType(type);
		room.setLevelSettings(settings);
		return room;
	}
	
	public static IRoom getInstance(Room type, ILevelSettings settings, Coord floorPos, Coord worldPos) {
		IRoom room = fromType(type);
		room.setLevelSettings(settings);
		room.setFloorPos(floorPos);
		room.setWorldPos(worldPos);
		return room;
	}
	
	public static IRoom getInstance(Room type, ILevelSettings settings, Coord floorPos, Coord worldPos, Cardinal dir) {
		IRoom room = getInstance(type, settings, floorPos, worldPos);
		room.setDirection(dir);
		return room;
	}
	
	public static IRoom getInstance(Room type, ILevelSettings settings, Coord pos, Cardinal dir, List<Exit> exits, boolean generated) {
		IRoom room = fromType(type);
		room.setLevelSettings(settings);
		room.setWorldPos(pos);
		room.setDirection(dir);
		exits.forEach(e -> {
			room.addExit(e);
		});
		room.setGenerated(generated);
		return room;
	}
	
	public static IRoom getInstance(String name, String settings, Coord pos, String dir, List<Exit> exits, boolean generated) {
		return Room.getInstance(
			Room.get(name),
			LevelSettings.get(settings),
			pos,
			Cardinal.of(dir),
			exits,
			generated);
	}
	
	public static Map<String, String> convertEntrances(Map<Cardinal, ExitType> entrances){
		Map<String, String> ents = new HashMap<String, String>();
		entrances.forEach((d, e) -> {
			ents.put(d.name(), e.name());
		});
		
		return ents;
	}
	
	public static Map<Cardinal, ExitType> convertEntrancesBack(Map<String, String> entrances){
		Map<Cardinal, ExitType> ents = new HashMap<Cardinal, ExitType>();
		entrances.forEach((d, e) -> {
			Cardinal dir = Cardinal.of(d);
			ExitType ent = ExitType.valueOf(e);
			ents.put(dir, ent);
		});
		return ents;
	}
}
