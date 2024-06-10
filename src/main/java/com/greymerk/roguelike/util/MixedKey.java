package com.greymerk.roguelike.util;

import java.util.Objects;

public class MixedKey<T> {

	public final Class<T> cls;
    public final String key;

    public static MixedKey<Boolean> ofBoolean(String key){
    	return new MixedKey<Boolean>(Boolean.class, key);
    }
    
    public static MixedKey<Double> ofDouble(String key){
    	return new MixedKey<Double>(Double.class, key);
    }
    
    public static MixedKey<String> ofString(String key){
    	return new MixedKey<String>(String.class, key);
    }
    
    public static MixedKey<Integer> ofInteger(String key){
    	return new MixedKey<Integer>(Integer.class, key);
    }
    
    public MixedKey(Class<T> cls, String key) {
    	this.cls = cls;
    	this.key = key;
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(cls, key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MixedKey<?> other = (MixedKey<?>) obj;
		return Objects.equals(cls, other.cls) && Objects.equals(key, other.key);
	}
}
