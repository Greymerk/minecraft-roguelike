package com.greymerk.roguelike.dungeon.layout;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.dynamic.Codecs;

public class Exit {
	
	private ExitType type;
	private Coord origin;
	private Cardinal dir;
	
	public static Codec<Exit> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codecs.NON_EMPTY_STRING.fieldOf("type").forGetter(exit -> exit.type.name()),
					Coord.CODEC.fieldOf("origin").forGetter(exit -> exit.origin),
					Codecs.NON_EMPTY_STRING.fieldOf("dir").forGetter(exit -> exit.dir.name())
			).apply(instance, (type, origin, dir) -> Exit.of(ExitType.valueOf(type), origin, Cardinal.of(dir)))
	);
	
	public static Exit of(ExitType type, Coord origin, Cardinal dir) {
		return new Exit(type, origin, dir);
	}
	
	private Exit(ExitType type, Coord origin, Cardinal dir) {
		this.type = type;
		this.origin = origin.freeze();
		this.dir = dir;
	}
	
	public ExitType type() {
		return this.type;
	}
	
	public Coord origin() {
		return this.origin;
	}
	
	public Cardinal dir() {
		return dir;
	}
	
	@Override
	public String toString() {
		return this.type.name() + " " + this.origin.toString() + " " + dir.name();
	}
	
};

