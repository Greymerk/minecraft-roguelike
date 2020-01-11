package greymerk.roguelike.util;

import java.util.Random;

public interface IWeighted<T> {

  int getWeight();

  T get(Random rand);

}
