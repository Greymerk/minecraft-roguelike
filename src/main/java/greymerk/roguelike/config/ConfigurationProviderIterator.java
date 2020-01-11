package greymerk.roguelike.config;


import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * An iterator which traverses the configurations contained in a ConfigurationProvider in an
 * unspecified order.
 */
public class ConfigurationProviderIterator implements Iterator<Configuration> {


  private Iterator<Map.Entry<String, String>> inner;


  /**
   * \cond
   */


  public ConfigurationProviderIterator(Iterator<Map.Entry<String, String>> inner) {

    this.inner = inner;

  }


  /**
   * \endcond
   */


  public boolean hasNext() {

    return inner.hasNext();

  }


  public Configuration next() throws NoSuchElementException {

    Map.Entry<String, String> next = inner.next();

    return new Configuration(
        next.getKey(),
        next.getValue()
    );

  }


  /**
   * This method is not supported.
   */
  public void remove() throws UnsupportedOperationException {

    //	Operation not supported,
    //	unconditional throw
    throw new UnsupportedOperationException();

  }


}