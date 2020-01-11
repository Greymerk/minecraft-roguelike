package greymerk.roguelike.config;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;


/**
 * Provides configuration information.
 */
public abstract class ConfigurationProvider implements Iterable<Configuration> {


  private static final String true_regex = "^\\s*(?:t(?:rue)?|y(?:es)?)\\s*$";
  private static final String false_regex = "^\\s*(?:f(?:alse)?|no?)\\s*$";
  private static final String true_string = "true";
  private static final String false_string = "false";
  protected Hashtable<String, String> kvp;
  private Pattern true_pattern;
  private Pattern false_pattern;


  protected ConfigurationProvider() {

    kvp = new Hashtable<>();
    true_pattern = Pattern.compile(true_regex);
    false_pattern = Pattern.compile(false_regex);

  }


  /**
   * Checks to see whether a configuration setting exists.
   *
   * \param [in] key The key to check.
   *
   * \return \em true if \em key has associated data in the configuration store, \em false
   * otherwise.
   */
  public boolean ContainsKey(String key) {

    //	Null keys never exist
    if (key == null) {
      return false;
    }

    return kvp.get(key) != null;

  }


  /**
   * Gets a configuration setting, if it exists.
   *
   * \param [in] key The key whose associated configuration information is to be retrieved.
   *
   * \return The data associated with \em key, if it exists, \em null otherwise.
   */
  public String Get(String key) {

    //	Guard against null keys
    if (key == null) {
      return null;
    }

    //	Fetch
    return kvp.get(key);

  }


  /**
   * Gets a configuration setting, returning some default value if no default is specified.
   *
   * \param [in] key The key whose associated configuration information is to be retrieved. \param
   * [in] fallback A value which shall be retrieved if no configuration is associated with \em key.
   *
   * \return The data associated with \em key, if it exists, otherwise \em fallback.
   */
  public String Get(String key, String fallback) {

    String value = Get(key);

    return (value == null) ? fallback : value;

  }

  /**
   * Gets a configuration setting as a double.
   *
   * \param [in] key The key whose associated configuration information is to be retrieved. \param
   * [in] fallback The value which will be retrieved if \em key is not present, or if \em key could
   * not be parsed to a double.
   *
   * \return Either the configuration associated with \em key, or \em fallback.
   */
  public double GetDouble(String key, double fallback) {

    String value = Get(key);

    if (value == null) {
      return fallback;
    }

    try {

      return Double.parseDouble(value);

    } catch (NumberFormatException ignored) {
    }

    return fallback;

  }

  /**
   * Gets a configuration setting as an integer.
   *
   * \param [in] key The key whose associated configuration information is to be retrieved. \param
   * [in] fallback The value which will be retrieved if \em key is not present, or if \em key could
   * not be parsed to an integer.
   *
   * \return Either the configuration associated with \em key, or \em fallback.
   */
  public int GetInteger(String key, int fallback) {

    String value = Get(key);

    if (value == null) {
      return fallback;
    }

    try {

      return Integer.parseInt(value);

    } catch (NumberFormatException ignored) {
    }

    return fallback;

  }

  /**
   * Gets a configuration setting as a boolean.
   *
   * \param [in] key The key whose associated configuration information is to be retrieved. \param
   * [in] fallback The value which will be retrieved if \em key is not present, or if \em key could
   * not be parsed to an integer.
   *
   * \return Either the configuration associated with \em key, or \em fallback.
   */
  public boolean GetBoolean(String key, boolean fallback) {

    String value = Get(key);

    if (value == null) {
      return fallback;
    }

    if (true_pattern.matcher(value).find()) {
      return true;
    }

    if (false_pattern.matcher(value).find()) {
      return false;
    }

    return fallback;

  }

  public List<Integer> GetListInteger(String key, List<Integer> fallback) {

    String value = Get(key);

    if (value == null) {
      return fallback;
    }

    String[] values = value.split(",");

    List<Integer> ints = new ArrayList<>();

    for (String s : values) {
      try {
        ints.add(Integer.parseInt(s));
      } catch (NumberFormatException ignored) {
      }
    }

    return ints;
  }


  /**
   * Sets a configuration, creating it if it does not exist, updating it otherwise.
   *
   * \param [in] key The key associated with the configuration to update or create. \param [in]
   * value The value to associate with \em key.
   */
  public void Set(String key, String value) {

    //	If the key is null, pass, that's
    //	meaningless
    if (key == null) {
      return;
    }

    //	If the value is null, that's actually
    //	an attempt to remove a configuration
    if (value == null) {

      kvp.remove(key);

      return;

    }

    //	Otherwise we update/insert the
    //	configuration
    kvp.put(key, value);

  }

  /**
   * Sets a configuration, creating it if it does not exist, updating it otherwise.
   *
   * \param [in] key The key associated with the configuration to update or create. \param [in]
   * value The value to associate with \em key.
   */
  public void Set(String key, double value) {

    Set(
        key,
        Double.toString(value)
    );

  }

  /**
   * Sets a configuration, creating it if it does not exist, updating it otherwise.
   *
   * \param [in] key The key associated with the configuration to update or create. \param [in]
   * value The value to associate with \em key.
   */
  public void Set(String key, int value) {

    Set(
        key,
        Integer.toString(value)
    );

  }

  /**
   * Sets a configuration, creating it if it does not exist, updating it otherwise.
   *
   * \param [in] key The key associated with the configuration to update or create. \param [in]
   * value The value to associate with \em key.
   */
  public void Set(String key, boolean value) {

    Set(
        key,
        value ? true_string : false_string
    );

  }


  public void Set(String key, List<Integer> intList) {

    Set(key, StringUtils.join(intList, ","));

  }

  /**
   * Unsets a configuration, removing it.
   *
   * If the requested configuration didn't exist, nothing happens.
   *
   * \param [in] key The key associated with the configuration to remote.
   */
  public void Unset(String key) {

    //	If the key is null, pass, that's
    //	meaningless
    if (key == null) {
      return;
    }

    //	Remove
    kvp.remove(key);

  }


  /**
   * Fetches an iterator which may be used to traverse the configurations in this provider in
   * alphabetical order.
   *
   * \return An iterator.
   */
  public Iterator<Configuration> iterator() {
    Set<Map.Entry<String, String>> entries = new TreeSet<>(new EntryAlphabetical());
    entries.addAll(kvp.entrySet());
    return new ConfigurationProviderIterator(entries.iterator());

  }

  private class EntryAlphabetical implements Comparator<Map.Entry<String, String>> {

    @Override
    public int compare(Entry<String, String> thing, Entry<String, String> other) {
      return thing.getKey().compareTo(other.getKey());
    }
  }
}