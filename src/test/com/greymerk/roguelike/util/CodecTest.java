package com.greymerk.roguelike.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.editor.Cardinal;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.ExtraCodecs;


class CodecTest {

	private static final Codec<Map<String, Integer>> MAP_CODEC = Codec.unboundedMap(ExtraCodecs.NON_EMPTY_STRING, ExtraCodecs.POSITIVE_INT);
	private static final Codec<List<String>> LIST_CODEC = Codec.list(ExtraCodecs.NON_EMPTY_STRING);
	
	@Test
	void testMapCodec() {
		
		Map<String, Integer> m = new HashMap<String, Integer>();
		m.put("one", 1);
		m.put("two", 2);
		
		final DataResult<Tag> enc = MAP_CODEC.encodeStart(NbtOps.INSTANCE, m);

		Tag nbt = enc.getOrThrow();
		
		Map<String, Integer> m2 = MAP_CODEC.decode(NbtOps.INSTANCE, nbt).getOrThrow().getFirst();
		
		assert(m.equals(m2));
		
	}
	
	@Test
	void testListCodec() {
		List<String> strings = List.of("hello", "world");
		
		final DataResult<Tag> enc = LIST_CODEC.encodeStart(NbtOps.INSTANCE, strings);
		
		Tag nbt = enc.getOrThrow();
		
		List<String> strings2 = LIST_CODEC.decode(NbtOps.INSTANCE, nbt).getOrThrow().getFirst();
		
		assert(strings.equals(strings2));
	}
	
	@Test
	void testSetCardinal() {
		Set<Cardinal> dirs = new HashSet<Cardinal>();
		dirs.add(Cardinal.NORTH);
		dirs.add(Cardinal.EAST);
		
		List<String> dirList = dirs.stream().map(dir -> dir.name()).collect(Collectors.toList());
		final DataResult<Tag> enc = LIST_CODEC.encodeStart(NbtOps.INSTANCE, dirList);
		
		Tag nbt = enc.getOrThrow();
		
		List<String> dirList2 = LIST_CODEC.decode(NbtOps.INSTANCE, nbt).getOrThrow().getFirst();
		
		Set<Cardinal> dirs2 = dirList2.stream().map(dir -> Cardinal.of(dir)).collect(Collectors.toSet());		
		assert(dirs.equals(dirs2));
	}

}
