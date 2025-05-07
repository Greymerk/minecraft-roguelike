package com.greymerk.roguelike.util.math;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

import net.minecraft.util.math.random.Random;

public class RandHelper {

	private static final int SHUFFLE_THRESHOLD = 5;
	
	public static <T> T pickFrom(List<T> list, Random rand) {
		if(list.size() == 0) return null;
		return list.get(rand.nextInt(list.size()));
	}
	
	public static <T> List<T> pickCountFrom(List<T> list, Random rand, int count){
		List<T> copy = new ArrayList<T>(list);
		shuffle(copy, rand);
		if(copy.size() < count) return copy;
		return copy.subList(0, count);
	}
	
	@SuppressWarnings("unchecked")
	public static void shuffle(List<?> list, Random rnd) {
        int size = list.size();
        if (size < SHUFFLE_THRESHOLD || list instanceof RandomAccess) {
            for (int i=size; i>1; i--)
                swap(list, i-1, rnd.nextInt(i));
        } else {
            Object arr[] = list.toArray();

            // Shuffle array
            for (int i=size; i>1; i--)
                swap(arr, i-1, rnd.nextInt(i));

            // Dump array back into list
            @SuppressWarnings("rawtypes")
			ListIterator it = list.listIterator();
            for (int i=0; i<arr.length; i++) {
                it.next();
                it.set(arr[i]);
            }
        }
    }
	
    @SuppressWarnings("unchecked")
	public static void swap(List<?> list, int i, int j) {
        @SuppressWarnings("rawtypes")
		final List l = list;
        l.set(i, l.set(j, l.get(i)));
    }
	
	public static void swap(Object[] x, int a, int b) {
	    Object t = x[a];
	    x[a] = x[b];
	    x[b] = t;
	}
	
	public static <T> Comparator<T> randomizer(Random rand){
		return new RandomComparator<>(rand);
	}
	
	private static final class RandomComparator<T> implements Comparator<T> {

	    private final Map<T, Integer> map = new IdentityHashMap<>();
	    private final Random random;

	    public RandomComparator(Random rand) {
	        this.random = rand;
	    }

	    @Override
	    public int compare(T t1, T t2) {
	        return Integer.compare(valueFor(t1), valueFor(t2));
	    }

	    private int valueFor(T t) {
	        synchronized (map) {
	            return map.computeIfAbsent(t, ignore -> random.nextInt());
	        }
	    }

	}
	
}
