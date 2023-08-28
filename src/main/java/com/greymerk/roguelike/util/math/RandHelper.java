package com.greymerk.roguelike.util.math;

import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import net.minecraft.util.math.random.Random;

public class RandHelper {

	private static final int SHUFFLE_THRESHOLD = 5;
	
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
	
}
