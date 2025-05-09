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

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.dynamic.Codecs;

class CodecTest {

	private static final Codec<Map<String, Integer>> MAP_CODEC = Codec.unboundedMap(Codecs.NON_EMPTY_STRING, Codecs.POSITIVE_INT);
	private static final Codec<List<String>> LIST_CODEC = Codec.list(Codecs.NON_EMPTY_STRING);
	
	@Test
	void testMapCodec() {
		
		Map<String, Integer> m = new HashMap<String, Integer>();
		m.put("one", 1);
		m.put("two", 2);
		
		final DataResult<NbtElement> enc = MAP_CODEC.encodeStart(NbtOps.INSTANCE, m);

		NbtElement nbt = enc.getOrThrow();
		
		Map<String, Integer> m2 = MAP_CODEC.decode(NbtOps.INSTANCE, nbt).getOrThrow().getFirst();
		
		assert(m.equals(m2));
		
	}
	
	@Test
	void testListCodec() {
		List<String> strings = List.of("hello", "world");
		
		final DataResult<NbtElement> enc = LIST_CODEC.encodeStart(NbtOps.INSTANCE, strings);
		
		NbtElement nbt = enc.getOrThrow();
		
		List<String> strings2 = LIST_CODEC.decode(NbtOps.INSTANCE, nbt).getOrThrow().getFirst();
		
		assert(strings.equals(strings2));
	}
	
	@Test
	void testSetCardinal() {
		Set<Cardinal> dirs = new HashSet<Cardinal>();
		dirs.add(Cardinal.NORTH);
		dirs.add(Cardinal.EAST);
		
		List<String> dirList = dirs.stream().map(dir -> dir.name()).collect(Collectors.toList());
		final DataResult<NbtElement> enc = LIST_CODEC.encodeStart(NbtOps.INSTANCE, dirList);
		
		NbtElement nbt = enc.getOrThrow();
		
		List<String> dirList2 = LIST_CODEC.decode(NbtOps.INSTANCE, nbt).getOrThrow().getFirst();
		
		Set<Cardinal> dirs2 = dirList2.stream().map(dir -> Cardinal.of(dir)).collect(Collectors.toSet());		
		assert(dirs.equals(dirs2));
	}

}
