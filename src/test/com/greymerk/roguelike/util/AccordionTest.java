package com.greymerk.roguelike.util;

import java.util.Map;

import org.junit.jupiter.api.Test;

class AccordionTest {

	@Test
	void testAdd() {
		Accordion<String> strings = new Accordion<String>();
		strings.addExactly("foo", 1);
		strings.add("bar", 2, 2);
		strings.add("biz", 3, 3);
		strings.addExactly("baz", 1);
	}
	
	
	@Test
	void testMap() {
		Accordion<String> strings = new Accordion<String>()
			.addExactly("foo", 1)
			.addAny("bar")
			.add("biz", 3, 3)
			.addExactly("baz", 1);
		
		
		Map<String, Integer> stringMap = strings.get(10);
		
		assert(stringMap.get("foo") == 1);
		assert(stringMap.get("bar") == 5);
		assert(stringMap.get("biz") == 3);
		assert(stringMap.get("baz") == 1);
		
		//System.out.println(stringMap);
	}
	
	@Test
	void testList() {
		Accordion<String> strings = new Accordion<String>();
		strings.addExactly("foo", 1);
		strings.addAtLeast("bar", 1);
		strings.add("biz", 3, 3);
		strings.addExactly("baz", 1);
		
		//System.out.println(strings.list(3));
	}
}
