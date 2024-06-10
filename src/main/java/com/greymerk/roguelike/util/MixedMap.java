package com.greymerk.roguelike.util;

import java.util.HashMap;

public class MixedMap extends HashMap<MixedKey<?>,Object> {
    /**
	 * https://stackoverflow.com/a/10082360
	 */
	private static final long serialVersionUID = 1L;

	public <T> T putMixed(MixedKey<T> key, T value) {
        return key.cls.cast(put(key, value));
    }

    public <T> T getMixed(MixedKey<T> key) {
        return key.cls.cast(get(key));
    }
}
